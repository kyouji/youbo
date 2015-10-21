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
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == site) {
			return "redirect:/depot/login";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("depot", site);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String year = cal.get(Calendar.YEAR) + "";
		String month = (cal.get(Calendar.MONTH) + 1) + "";
		String day = cal.get(Calendar.DAY_OF_MONTH) + "";
		String sBeginDate = year + "-" + month + "-" + day + " 00:00:00";
		String sFinishDate = year + "-" + month + "-" + day + " 24:00:00";
		Date beginDate = null;
		Date finishDate = null;
		try {
			beginDate = sdf.parse(sBeginDate);
			finishDate = sdf.parse(sFinishDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<TdOrder> orders = tdOrderService.findByDiyIdAndOrderTimeBetween(site.getId(), beginDate, finishDate);
		Double income = new Double(0);
		for (TdOrder order : orders) {
			if (null != order.getTotalPrice() && order.getTotalPrice() > 0
					&& (order.getStatusId() == 6L || order.getStatusId() == 9L)
					&& (null == order.getThePayType() || order.getThePayType() == 0L)) {
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
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		if (null == diyUser) {
			return "/depot/login";
		}

		List<TdBankcard> bankcardList = tdBankcardService.findByDiyId(site.getId());
		map.addAttribute("bankcardList", bankcardList);
		return "/depot/bankcard";
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
			return "/depot/login";
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
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");

		if (null == diyUser) {
			return "/depot/login";
		}

		if (null == site.getAllMoney()) {
			site.setAllMoney(new Double(0));
		}
		map.addAttribute("allMoney", site.getAllMoney());
		List<TdBankcard> bankcardList = tdBankcardService.findByDiyId(site.getId());
		map.addAttribute("cards", bankcardList);

		return "/depot/withdraw";
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
			return "/depot/login";
		}
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		List<TdDeposit> deposit_list = tdDepositService.findByDiyIdOrderByDepositDateDesc(site.getId());
		map.addAttribute("deposit_list", deposit_list);
		return "/depot/withdraw_record";
	}

	@RequestMapping(value = "/reserve")
	public String reserve(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		if (null == diyUser) {
			return "/depot/login";
		}
		List<TdOrder> list = tdOrderService.findByDiyIdAndStatusIdOrderByOrderTime(site.getId());
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
			return "/depot/login";
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
			return "/depot/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != order.getUsername()) {
			TdUser user = tdUserService.findByUsername(order.getUsername());
			map.addAttribute("user", user);
		}
		if (null != order && null != order.getPayTypeId()) {
			TdPayType payType = tdPayTypeService.findOne(order.getPayTypeId());
			map.addAttribute("payType", payType);
		}
		map.addAttribute("order", order);
		return "/depot/order_details";
	}

	@RequestMapping(value = "/operate")
	public String operate(HttpServletRequest req, Long id, Long type) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		if (null == site || null == diyUser) {
			return "/depot/login";
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
				site.setParkingNowNumber(site.getParkingNowNumber() - 1);
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
	public String chargeManage(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
		}
		// 查找不同支付方式的订单
		List<TdOrder> xs_list = tdOrderService.findXszf(diyUser.getDiyId());
		List<TdOrder> xj_list = tdOrderService.findXjzf(diyUser.getDiyId());
		List<TdOrder> md_list = tdOrderService.findMd(diyUser.getDiyId());
		List<TdOrder> yk_list = tdOrderService.findYk(diyUser.getDiyId());

		// 查找违约订单
		List<TdOrder> wy = tdOrderService.findWy(diyUser.getDiyId());
		List<TdOrder> wy_list = new ArrayList<>();
		for (TdOrder tdOrder : wy) {
			if (null != tdOrder.getTotalPrice() && tdOrder.getTotalPrice() > 0) {
				wy_list.add(tdOrder);
			}
		}

		map.addAttribute("xs_list", xs_list);
		map.addAttribute("xj_list", xj_list);
		map.addAttribute("md_list", md_list);
		map.addAttribute("yk_list", yk_list);

		return "/depot/charge_manage";
	}

	@RequestMapping("/subAccount")
	public String subAccount(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
		}
		List<TdDiyUser> subAccount_list = tdDiyUserService.findByDiyIdAndRoleId(diyUser.getDiyId());
		map.addAttribute("subAccount_list", subAccount_list);
		return "/depot/sub_account";
	}

	@RequestMapping("/editSubAccount")
	public String addSubAccount(HttpServletRequest req, Long id, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
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
			return "/depot/login";
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
			return "/depot/login";
		}
		tdDiyUserService.delete(id);
		return "redirect:/depot/myaccount/subAccount";
	}

	@RequestMapping("/sureInput")
	public String sureInput(HttpServletRequest req, Long id, String keywords, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
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
}
