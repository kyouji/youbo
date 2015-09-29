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
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDepositService;
import com.ynyes.youbo.service.TdDiyLogService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
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
		TdDiySite site =  (TdDiySite) req.getSession().getAttribute("site");
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
			if (null != order.getTotalPrice() && order.getTotalPrice() > 0 && order.getStatusId() != 5) {
				income += order.getTotalPrice();
			} else {
				if (null != order.getFirstPay() && order.getFirstPay() > 0
						&& !"审核通过".equalsIgnoreCase(order.getCheckStatus())) {
					income += order.getFirstPay();
				}
			}
		}
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
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
 		if (null == site) {
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
		System.err.println(list);

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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findByUsernameAndIsEnableTrue(siteUsername);
		if (null == site) {
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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);

		if (null == site) {
			return "/depot/login";
		}

		if (null == site.getAllMoney()) {
			site.setAllMoney(new Double(0));
		}
		map.addAttribute("allMoney", site.getAllMoney());
		map.addAttribute("cards", site.getBankcardList());

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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			return "/depot/login";
		}
		List<TdDeposit> deposit_list = tdDepositService.findByDiyIdOrderByDepositDateDesc(site.getId());
		map.addAttribute("deposit_list", deposit_list);
		return "/depot/withdraw_record";
	}

	@RequestMapping(value = "/refund/edit")
	@ResponseBody
	public Map<String, Object> refundEdit(HttpServletRequest req, Long orderId, Integer type, String reason) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			res.put("message", "未获取到已登录用户的信息！");
			return res;
		}
		if (null == orderId || null == type || null == reason) {
			res.put("message", "参数获取失败！");
			return res;
		}

		TdOrder order = tdOrderService.findOne(orderId);
		if (0 == type) {
			order.setStatusId(9L);
			order.setCheckStatus("审核通过");
			order.setFinishTime(new Date());
			// 在此开始调用银行接口进行退款
		}
		if (-1 == type) {
			order.setStatusId(8L);
			order.setCheckStatus("审核未通过");
		}
		order.setRemarkInfo(reason);
		tdOrderService.save(order);
		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/reserve")
	public String reserve(HttpServletRequest req, ModelMap map) {
		TdDiySite site =  (TdDiySite) req.getSession().getAttribute("site");
		if (null == site) {
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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			return "/depot/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if(null !=order&&null != order.getUsername()){
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
	public String operate(HttpServletRequest req,Long id,Long type){
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if(null == site || null == diyUser){
			return "/depot/login";
		}
		
		TdOrder order = tdOrderService.findOne(id);
		
		TdDiyLog log = new TdDiyLog();
		
		log.setCreateTime(new Date());
		log.setDiyId(site.getId());
		log.setUsername(diyUser.getUsername());
		
		if(0 == type){
			log.setActionType("同意预约");
			log.setRemark(diyUser.getUsername()+"同意了"+order.getCarCode()+"的预约，此时车位剩余"+site.getParkingNowNumber()+"个");
			order.setStatusId(3L);
		}
		
		if(1 == type){
			log.setActionType("拒绝预约");
			log.setRemark(diyUser.getUsername()+"拒绝了"+order.getCarCode()+"的预约，此时车位剩余"+site.getParkingNowNumber()+"个");
			order.setStatusId(9L);
			order.setCancelReason("对不起，您的预约已被拒绝");
		}
		tdOrderService.save(order);
		tdDiyLogService.save(log);
		return "redirect:/depot/myaccount/reserve";
		
	}
}
