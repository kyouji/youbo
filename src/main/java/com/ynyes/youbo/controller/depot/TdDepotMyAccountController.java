package com.ynyes.youbo.controller.depot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.entity.TdDeposit;
import com.ynyes.youbo.entity.TdDiyLog;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.entity.TdVip;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDepositService;
import com.ynyes.youbo.service.TdDiyLogService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdDiyUserService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.service.TdVipService;
import com.ynyes.youbo.util.SiteMagConstant;

@Controller
@RequestMapping("/depot/myaccount")
public class TdDepotMyAccountController {

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdBankcardService tdBankcardService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdDepositService tdDepositService;

	@Autowired
	private TdDiyLogService tdDiyLogService;

	@Autowired
	private TdDiyUserService tdDiyUserService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdVipService tdVipService;

	/**
	 * 我的账户首页
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping
	public String index(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		tdCommonService.setHeader(map, req);
		map.addAttribute("depot", site);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf_temp = new SimpleDateFormat("yyyy-MM-dd");

		Double income = 0.00;

		// 获取当前可提现金额
		Calendar cal = Calendar.getInstance();

		String sBeginDate = null;
		String sFinishDate = null;
		Date beginDate = null;
		Date finishDate = null;

		// 获取提现结束时间
		cal.add(Calendar.DATE, -1);
		sFinishDate = sdf_temp.format(cal.getTime()) + " 23:59:59";
		try {
			finishDate = sdf.parse(sFinishDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		List<TdOrder> about_income = null;

		// 获取上一次提现的时间
		List<TdDeposit> deposits = tdDepositService.findByDiyIdAndIsRemitTrueOrderByDepositDateDesc(diyUser.getDiyId());
		if (null != deposits && deposits.size() > 0 && null != deposits.get(0)
				&& null != deposits.get(0).getDepositDate()) {
			sBeginDate = sdf_temp.format(deposits.get(0).getDepositDate()) + " 00:00:00";
			try {
				beginDate = sdf.parse(sBeginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			about_income = tdOrderService.findByDiyIdAndFinishTimeBetween(diyUser.getDiyId(), beginDate, finishDate);
		} else {
			about_income = tdOrderService.findByDiyIdAndFinishTimeBefore(diyUser.getDiyId(), finishDate);
		}

		// 计算当前可提现金额
		for (TdOrder order : about_income) {
			if (null != order && null != order.getThePayType() && 0 == order.getThePayType()) {
				income += order.getTotalPrice();
			}
		}

		map.addAttribute("site", site);
		map.addAttribute("diyUser", diyUser);
		map.addAttribute("income", income);
		return "/depot/my_account";
	}

	/**
	 * 银行卡
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/bankcard")
	public String bankcard(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		List<TdBankcard> bankcardList = tdBankcardService.findByDiyId(site.getId());
		map.addAttribute("bankcardList", bankcardList);
		return "/depot/bankcard";
	}

	@RequestMapping(value = "/vip/carcode")
	public String vipCarCode(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		Long now = new Date().getTime();
		List<TdVip> list = tdVipService.findByDiyId(diyUser.getDiyId());
		for (int i = 0; i < list.size(); i++) {
			TdVip vip = list.get(i);
			Long begin = vip.getBeginDate().getTime();
			Long finish = vip.getFinishDate().getTime();
			if (!(begin < now && finish > now)) {
				list.remove(i);
			}
		}
		map.addAttribute("vip_list", list);
		return "/depot/vip_carcode";
	}

	/**
	 * 银行卡添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addbankcard")
	public String bankcardAdd(TdBankcard bankcard, HttpServletRequest req, ModelMap map) {
		map.addAttribute("paytape_list", tdPayTypeService.findAll());
		return "/depot/bankcard_add";
	}

	/**
	 * 保存银行卡
	 * 
	 * @param bankcard
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "bankcard/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> bankcardSave(TdBankcard bankcard, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);
		res.put("message", "用户不存在");
		if (username == null) {

		}
		TdUser user = tdUserService.findByUsername(username);
		if (user == null) {
			res.put("message", "用户不存在");
			return res;
		}

		List<TdBankcard> list = user.getBankcardList();

		if (null == user.getBankcardList()) {
			List<TdBankcard> bankCards = new ArrayList<>();
			bankCards.add(bankcard);
			user.setBankcardList(bankCards);
		} else {
			user.getBankcardList().add(bankcard);
		}

		tdBankcardService.save(bankcard);
		tdUserService.save(user);
		res.put("code", 0);
		return res;
	}

	/**
	 * 退款申请
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/refund")
	public String refund(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}

		List<TdOrder> all_refund = tdOrderService.findByIdAndRefund(site.getId());
		List<TdOrder> checking_refund = tdOrderService.findChecking(site.getId());
		List<TdOrder> true_refund = tdOrderService.findCheckedTrue(site.getId());
		List<TdOrder> false_refund = tdOrderService.findCheckedFalse(site.getId());

		map.addAttribute("all_refund", all_refund);
		map.addAttribute("checking_refund", checking_refund);
		map.addAttribute("true_refund", true_refund);
		map.addAttribute("false_refund", false_refund);

		return "/depot/refund";
	}

	/**
	 * 消息中心
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/message")
	public String message(HttpServletRequest req, Device device, ModelMap map) {
		return "/depot/message_center";
	}

	/**
	 * 提现
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/withdrawal")
	public String withdrawal(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());

		if (null == site.getAllMoney()) {
			site.setAllMoney(new Double(0));
		}
		map.addAttribute("allMoney", site.getAllMoney());
		List<TdPayType> all_pay_type = tdPayTypeService.findAll();
		map.addAttribute("all_pay_type", all_pay_type);

		// 获取当前可提现金额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf_temp = new SimpleDateFormat("yyyy-MM-dd");

		Double income = 0.00;

		Calendar cal = Calendar.getInstance();

		String sBeginDate = null;
		String sFinishDate = null;
		Date beginDate = null;
		Date finishDate = null;

		// 获取提现结束时间
		cal.add(Calendar.DATE, -1);
		sFinishDate = sdf_temp.format(cal.getTime()) + " 23:59:59";
		try {
			finishDate = sdf.parse(sFinishDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		List<TdOrder> about_income = null;

		// 获取上一次提现的时间
		List<TdDeposit> deposits = tdDepositService.findByDiyIdAndIsRemitTrueOrderByDepositDateDesc(diyUser.getDiyId());
		if (null != deposits && deposits.size() > 0 && null != deposits.get(0)
				&& null != deposits.get(0).getDepositDate()) {
			sBeginDate = sdf_temp.format(deposits.get(0).getDepositDate()) + " 00:00:00";
			try {
				beginDate = sdf.parse(sBeginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			about_income = tdOrderService.findByDiyIdAndFinishTimeBetween(diyUser.getDiyId(), beginDate, finishDate);
		} else {
			about_income = tdOrderService.findByDiyIdAndFinishTimeBefore(diyUser.getDiyId(), finishDate);
		}

		// 计算当前可提现金额
		for (TdOrder order : about_income) {
			if (null != order && null != order.getThePayType() && 0 == order.getThePayType()) {
				income += order.getTotalPrice();
			}
		}
		map.addAttribute("income", income);

		return "/depot/withdraw";
	}

	@RequestMapping(value = "/recharge/check")
	@ResponseBody
	public Map<String, Object> rechargeChcek(Double money, String pwd, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");

		if (null != diyUser) {
			TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
			if (!pwd.equals(site.getDepositPassword())) {
				res.put("message", "密码错误！");
				return res;
			} else {
				// 以下代码用于生成订单编号
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String sDate = sdf.format(date);
				Random random = new Random();
				Integer suiji = random.nextInt(900) + 100;
				String orderNum = "TX" + sDate + suiji;
				// 生成订单编号结束
				TdDeposit deposit = new TdDeposit();
				String sBeginDate = null;
				String sFinishDate = null;
				Date beginDate = null;
				Date finishDate = null;
				SimpleDateFormat sdf_theone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf_temp = new SimpleDateFormat("yyyy-MM-dd");
				List<TdDeposit> deposits = tdDepositService
						.findByDiyIdAndIsRemitTrueOrderByDepositDateDesc(diyUser.getDiyId());
				if (null != deposits && deposits.size() > 0 && null != deposits.get(0)
						&& null != deposits.get(0).getDepositDate()) {
					sBeginDate = sdf_temp.format(deposits.get(0).getDepositDate()) + " 00:00:00";
					try {
						beginDate = sdf_theone.parse(sBeginDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				Calendar cal = Calendar.getInstance();
				// 获取提现结束时间
				cal.add(Calendar.DATE, -1);
				sFinishDate = sdf_temp.format(cal.getTime()) + " 23:59:59";
				try {
					finishDate = sdf.parse(sFinishDate);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				deposit.setBelongBegin(beginDate);
				deposit.setBelongFinish(finishDate);
				deposit.setNum(orderNum);
				deposit.setMoney(money);
				deposit.setDepositDate(new Date());
				deposit.setDiyId(diyUser.getDiyId());
				deposit.setDiyName(site.getTitle());
				tdDepositService.save(deposit);
				res.put("message", "提现申请提交成功，请耐心等待！");
			}
		} else {
			res.put("message", "未找到指定停车场！");
			return res;
		}
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/deposit/detail/{id}")
	public String depositDetail(@PathVariable Long id, HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDeposit deposit = tdDepositService.findOne(id);
		map.addAttribute("deposit", deposit);
		return "/depot/deposit_detail";
	}

	/**
	 * 提现记录
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/cashrecord")
	public String cashrecord(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		List<TdDeposit> deposit_list = tdDepositService.findByDiyIdOrderByDepositDateDesc(site.getId());
		map.addAttribute("site", site);
		map.addAttribute("deposit_list", deposit_list);
		return "/depot/withdraw_record";
	}

	@RequestMapping(value = "/reserve")
	public String reserve(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		List<TdOrder> list = tdOrderService.findByReserve(site.getId());
		map.addAttribute("reserved_list", list);
		return "/depot/reserved_list";
	}

	@RequestMapping(value = "/saveOrderId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOrderId(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		req.getSession().setAttribute("orderId", id);
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/headImg", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, HttpServletRequest req) {
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			return "redirect:/depot/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		try {
			byte[] bytes = Filedata.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = SiteMagConstant.imagePath + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
			Long orderId = (Long) req.getSession().getAttribute("orderId");
			TdOrder order = tdOrderService.findOne(orderId);
			if (null != order) {
				if (null == order.getCarCodePhoto()) {
					order.setCarCodePhoto("");
				}
				order.setCarCodePhoto(order.getCarCodePhoto() + ("/images/" + fileName + ","));
				tdOrderService.save(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/depot/myaccount/reserve";

	}

	@RequestMapping(value = "/detail")
	public String orderDetail(HttpServletRequest req, Long orderId, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != order.getUsername()) {
			TdUser user = tdUserService.findByUsername(order.getUsername());
			map.addAttribute("user", user);
		}
		map.addAttribute("order", order);
		return "/depot/order_details";
	}

	@RequestMapping(value = "/operate")
	public String operate(HttpServletRequest req, Long id, Long type) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		if (null == site || null == diyUser) {
			return "redirect:/depot/login";
		}

		TdOrder order = tdOrderService.findOne(id);

		if (9L != order.getStatusId()) {
			TdDiyLog log = new TdDiyLog();

			log.setCreateTime(new Date());
			log.setDiyId(site.getId());
			log.setUsername(diyUser.getUsername());
			if (0 == type) {
				log.setActionType("同意预约");
				log.setRemark(diyUser.getUsername() + "同意了" + order.getCarCode() + "的预约， 预约前车位剩余"
						+ site.getParkingNowNumber() + "个");
				order.setStatusId(3L);
				order.setReserveTime(new Date());
				site.setParkingNowNumber(site.getParkingNowNumber() - 1);
				tdDiySiteService.save(site);
			}

			if (1 == type) {
				log.setActionType("拒绝预约");
				log.setRemark(diyUser.getUsername() + "拒绝了" + order.getCarCode() + "的预约，预约前车位剩余"
						+ site.getParkingNowNumber() + "个");
				order.setStatusId(9L);
				order.setCancelReason("对不起，您的预约已被拒绝");
				TdUser user = tdUserService.findByUsername(order.getUsername());
				TdSetting setting = tdSettingService.findOne(1L);
				user.setBalance(user.getBalance() + setting.getFirstPay());
				tdUserService.save(user);
			}
			tdDiyLogService.save(log);
			tdOrderService.save(order);
		}
		return "redirect:/depot/myaccount/reserve";
	}

	@RequestMapping(value = "/chargeManage")
	public String chargeManage(HttpServletRequest req, ModelMap map, Integer year, Integer month, Integer day) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (null == year || month == year || null == day) {
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DATE);
		}
		String date = year + "-" + month + "-" + day;

		String sBeginDate = date + " 00:00:00";
		String sFinishDate = date + " 23:59:59";

		Date beginTime = null;
		Date finishTime = null;
		try {
			beginTime = sdf.parse(sBeginDate);
			finishTime = sdf.parse(sFinishDate);
			map.addAttribute("theDate", beginTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TdOrder> xs_list = tdOrderService.findByXszfTime(diyUser.getDiyId(), beginTime, finishTime);
		List<TdOrder> xj_list = tdOrderService.findByXjzfTime(diyUser.getDiyId(), beginTime, finishTime);
		List<TdOrder> md_list = tdOrderService.findByMdTime(diyUser.getDiyId(), beginTime, finishTime);
		List<TdOrder> yk_list = tdOrderService.findByYkTime(diyUser.getDiyId(), beginTime, finishTime);
		List<TdOrder> wy = tdOrderService.findByWyTime(diyUser.getDiyId(), beginTime, finishTime);
		List<TdOrder> wy_list = new ArrayList<>();
		for (TdOrder tdOrder : wy) {
			if (null != tdOrder.getTotalPrice() && tdOrder.getTotalPrice() > 0) {
				wy_list.add(tdOrder);
			}
		}

		Double xsAll = 0.00;
		Double xjAll = 0.00;
		Double wyAll = 0.00;

		// 获取所有的泊车员账号
		List<TdDiyUser> diyUser_list = tdDiyUserService.findByDiyIdAndRoleId(diyUser.getDiyId());

		// 计算当日线上金额
		for (TdOrder order : xs_list) {
			if (null != order && null != order.getTotalPrice()) {
				xsAll += order.getTotalPrice();
			}
		}

		// 计算当日现金金额
		for (TdOrder order : xj_list) {
			if (null != order && null != order.getTotalPrice()) {
				xjAll += order.getTotalPrice();
				// 计算泊车员现金收费量
				for (TdDiyUser subDiyUser : diyUser_list) {
					if (null != subDiyUser && null != subDiyUser.getRealName()
							&& subDiyUser.getRealName().equals(order.getOperator())) {
						if (null == subDiyUser.getAllash()) {
							subDiyUser.setAllash(0.00);
						}
						subDiyUser.setAllash(subDiyUser.getAllash() + order.getTotalPrice());
					}
				}
			}
		}

		// 计算当日泊车员免单数量
		for (TdOrder order : md_list) {
			for (TdDiyUser subDiyUser : diyUser_list) {
				if (null != subDiyUser && null != subDiyUser.getRealName()
						&& subDiyUser.getRealName().equals(order.getOperator())) {
					if (null == subDiyUser.getMdNum()) {
						subDiyUser.setMdNum(0L);
					}
					subDiyUser.setMdNum(subDiyUser.getMdNum() + 1);
				}
			}
		}

		// 计算当日违约金额
		for (TdOrder order : wy_list) {
			if (null != order && null != order.getTotalPrice()) {
				wyAll += order.getTotalPrice();
			}
		}

		map.addAttribute("xsAll", xsAll);
		map.addAttribute("xjAll", xjAll);
		map.addAttribute("wyAll", wyAll);
		map.addAttribute("diyUser_list", diyUser_list);
		map.addAttribute("xs_list", xs_list);
		map.addAttribute("xj_list", xj_list);
		map.addAttribute("md_list", md_list);
		map.addAttribute("yk_list", yk_list);
		map.addAttribute("wy_list", wy_list);
		return "/depot/charge_manage";
	}

	@RequestMapping("/subAccount")
	public String subAccount(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		List<TdDiyUser> subAccount_list = tdDiyUserService.findByDiyIdAndRoleId(diyUser.getDiyId());
		map.addAttribute("subAccount_list", subAccount_list);
		return "/depot/sub_account";
	}

	@RequestMapping("/editSubAccount")
	public String addSubAccount(HttpServletRequest req, Long id, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		if (null != id) {
			TdDiyUser theUser = tdDiyUserService.findOne(id);
			map.addAttribute("theUser", theUser);
		}
		return "/depot/subaccount_add";
	}

	@RequestMapping(value = "/editSubAccount", method = RequestMethod.POST)
	public String editSubAccount(HttpServletRequest req, String username, String password, String realname, Long id,
			Boolean isEnable) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiyUser theUser = new TdDiyUser();
		theUser.setId(id);
		theUser.setUsername(username);
		theUser.setPassword(password);
		theUser.setDiyId(diyUser.getDiyId());
		theUser.setIsEnable(isEnable);
		theUser.setRealName(realname);
		theUser.setRoleId(1L);
		tdDiyUserService.save(theUser);
		return "redirect:/depot/myaccount/subAccount";
	}

	@RequestMapping(value = "/checkSubAccount")
	@ResponseBody
	public Map<String, Object> checkSubAccount(String username) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (null == username) {
			res.put("message", "获取用户名失败！");
			return res;
		}
		TdDiyUser diyUser = tdDiyUserService.findByUsername(username);
		if (null != diyUser) {
			res.put("message", "该用户名已注册！");
			return res;
		}
		res.put("status", 0);
		return res;
	}

	@RequestMapping("/delSubAccount")
	public String deleteSubAccount(HttpServletRequest req, Long id) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		tdDiyUserService.delete(id);
		return "redirect:/depot/myaccount/subAccount";
	}

	@RequestMapping("/sureInput")
	public String sureInput(HttpServletRequest req, Long id, String keywords, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}

		TdOrder order = tdOrderService.findOne(id);
		order.setStatusId(4L);
		order.setInputTime(new Date());
		TdDiyLog log = new TdDiyLog();
		log.setActionType("确认入库");
		log.setCreateTime(new Date());
		log.setDiyId(diyUser.getDiyId());
		log.setUsername(diyUser.getRealName());
		log.setRemark("确认" + order.getCarCode() + "车辆进入车库");
		tdDiyLogService.save(log);
		tdOrderService.save(order);
		List<TdOrder> search_list = tdOrderService
				.findByDiyIdAndCarCodeContainingOrderByOrderTimeDesc(diyUser.getDiyId(), keywords);
		map.addAttribute("search_list", search_list);
		map.addAttribute("keywords", keywords);
		return "/depot/search";
	}

	@RequestMapping(value = "/noCarCode")
	public String noCarCode(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		List<TdOrder> no_carCode_list = tdOrderService
				.findByStatusIdAndCarCodeContainingAndDiyIdOrderByOrderTimeDesc(diyUser.getDiyId());
		map.addAttribute("no_carCode_list", no_carCode_list);
		return "/depot/no_carcode";
	}

	@RequestMapping(value = "/change/password")
	public String changePassword(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}

		map.addAttribute("diyUser", diyUser);
		return "/depot/change_password";
	}

	@RequestMapping(value = "/password/check")
	@ResponseBody
	public Map<String, Object> passwordCheck(String param, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "n");
		res.put("info", "原密码输入错误!");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null != diyUser) {
			if (diyUser.getPassword().equals(param)) {
				res.put("status", "y");
				res.put("info", "");
			}
		}
		return res;
	}

	@RequestMapping(value = "/change/password/save")
	public String saveNewPassword(String password, HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		diyUser.setPassword(password);
		tdDiyUserService.save(diyUser);
		return "redirect:/depot/site";
	}

	@RequestMapping(value = "/addNewCar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNewCar(String carCode, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		List<TdOrder> orders = tdOrderService.findByReservedOrder(diyUser.getDiyId(), carCode);
		if (null == orders || 0 == orders.size()) {
			TdOrder order = new TdOrder();
			// 以下代码用于生成订单编号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String sDate = sdf.format(date);
			Random random = new Random();
			Integer suiji = random.nextInt(900) + 100;
			String orderNum = sDate + suiji;
			// 生成订单编号结束
			order.setOrderNumber(orderNum);
			order.setOrderTime(new Date());
			order.setReserveTime(new Date());
			order.setInputTime(new Date());
			order.setCarCode(carCode);
			order.setUsername(carCode);
			order.setDiyId(diyUser.getDiyId());
			order.setDiyTitle(site.getTitle());
			order.setStatusId(4L);
			tdOrderService.save(order);
			TdDiyLog log = new TdDiyLog();
			log.setActionType("未预约车辆入库");
			log.setCreateTime(new Date());
			log.setDiyId(diyUser.getDiyId());
			log.setUsername(diyUser.getRealName());
			log.setRemark("确认" + carCode + "车辆进入车库");
			tdDiyLogService.save(log);
		} else {
			if (orders.size() != 1) {
				res.put("info", "该车牌号码存在有错误信息的订单，操作失败！");
				return res;
			} else {
				orders.get(0).setInputTime(new Date());
				TdDiyLog log = new TdDiyLog();
				log.setActionType("预约车辆入库");
				log.setCreateTime(new Date());
				log.setDiyId(diyUser.getDiyId());
				log.setUsername(diyUser.getRealName());
				log.setRemark("确认" + carCode + "车辆进入车库");
				tdDiyLogService.save(log);
				tdOrderService.save(orders.get(0));
			}
		}
		res.put("status", 0);
		res.put("info", "操作成功！");
		return res;
	}

	@RequestMapping(value = "/tradeDetail")
	public String detail(HttpServletRequest request, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) request.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		// 获取当前年份
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		map.addAttribute("year", year);
		return "/depot/detail";
	}

	@RequestMapping(value = "/OrderDetail")
	public String getOrderDeatail(Long year, Long month, HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		Long days = new Long(0);
		if (2 == month) {
			if ((year % 100 == 0 && year % 400 == 0) || year % 4 == 0) {
				days = new Long(29);
			}
		}

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			days = new Long(31);
		}

		if (month == 4 || month == 6 || month == 9 || month == 11) {
			days = new Long(30);
		}

		map.addAttribute("days", days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Double allXs = 0.00;
		Double allXj = 0.00;
		Integer mdNum = 0;
		Double allWy = 0.00;

		Double totalPrice = new Double(0.00);

		for (int i = 1; i <= days; i++) {
			String sBegin = year + "-" + month + "-" + i + " 00:00:00";
			String sFinish = year + "-" + month + "-" + i + " 23:59:59";
			Date beginDate = null;
			Date finishDate = null;
			Double income = new Double(0.00);
			try {
				beginDate = sdf.parse(sBegin);
				finishDate = sdf.parse(sFinish);
				List<TdOrder> list = tdOrderService.findByDiyIdAndFinishTimeBetween(diyUser.getDiyId(), beginDate,
						finishDate);
				if (null != list) {
					for (TdOrder order : list) {
						if (null != order.getTotalPrice() && order.getTotalPrice() > 0
								&& (order.getStatusId() == 6L || order.getStatusId() == 9L)
								&& (null != order.getThePayType() && 2L != order.getThePayType())) {
							// && (null != order.getThePayType() && 2L !=
							// order.getThePayType())
							if (null != order.getThePayType() && 0L == order.getThePayType()
									&& 9L != order.getStatusId()) {
								income += order.getTotalPrice();
								totalPrice += order.getTotalPrice();
								allXs += order.getTotalPrice();
							}
							if (null != order.getThePayType() && 1L == order.getThePayType()) {
								income += order.getTotalPrice();
								totalPrice += order.getTotalPrice();
								allXj += order.getTotalPrice();
							}
							if (9L == order.getStatusId()) {
								income += order.getTotalPrice();
								totalPrice += order.getTotalPrice();
								allWy += order.getTotalPrice();
							}
						} else if (null != order.getThePayType() && 2L == order.getThePayType()) {
							mdNum += 1;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.addAttribute("income" + i, income);
		}
		map.addAttribute("allXs", allXs);
		map.addAttribute("allXj", allXj);
		map.addAttribute("mdNum", mdNum);
		map.addAttribute("allWy", allWy);
		map.addAttribute("year", year);
		map.addAttribute("month", month);
		map.addAttribute("totalPrice", totalPrice);
		return "/depot/fee_detial";
	}

	@RequestMapping(value = "/detailDay")
	public String detailDay(Long year, Long month, Long day, HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}

		Date beginDate = null;
		Date finishDate = null;

		String sBegin = year + "-" + month + "-" + day + " 00:00:00";
		String sFinish = year + "-" + month + "-" + day + " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Double totalPrice = new Double(0.00);

		try {
			beginDate = sdf.parse(sBegin);
			finishDate = sdf.parse(sFinish);
			List<TdOrder> list = tdOrderService.findByDiyIdAndFinishTimeBetween(diyUser.getDiyId(), beginDate,
					finishDate);
			List<TdOrder> incomeOrder = new ArrayList<>();
			for (TdOrder order : list) {
				if (null != order.getTotalPrice() && order.getTotalPrice() > 0
						&& (order.getStatusId() == 6L || order.getStatusId() == 9L)
						&& (null == order.getThePayType() || order.getThePayType() == 0L)) {
					totalPrice += order.getTotalPrice();
					incomeOrder.add(order);
				}
			}
			map.addAttribute("orders", incomeOrder);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.addAttribute("totalPrice", totalPrice);
		return "/depot/detail_day";
	}
}
