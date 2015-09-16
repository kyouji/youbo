package com.ynyes.youbo.controller.depot;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.entity.TdDeposit;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDepositService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdUserService;

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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		if (null == siteUsername) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		tdCommonService.setHeader(map, req);
		map.addAttribute("depot", tdDiySiteService.findByUsernameAndIsEnableTrue(siteUsername));
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
			if (null!=order.getTotalPrice()&&order.getTotalPrice() > 0 && order.getStatusId() != 5) {
					income += order.getTotalPrice();
			} else {
				if (null!=order.getFirstPay()&&order.getFirstPay() > 0 && !"审核通过".equalsIgnoreCase(order.getCheckStatus())) {
						income += order.getFirstPay();
				}
			}
		}
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
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if(null == site){
			return "/depot/login";
		}
		List<TdBankcard> bankcardList = site.getBankcardList();
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
		
		if(null == site){
			return "/depot/login";
		}
		
		if(null == site.getAllMoney()){
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
		if(null == site){
			return "/depot/login";
		}
		List<TdDeposit> deposit_list = tdDepositService.findByDiyIdOrderByDepositDateDesc(site.getId());
		map.addAttribute("deposit_list", deposit_list);
		return "/depot/withdraw_record";
	}
	
	@RequestMapping(value="/refund/edit")
	@ResponseBody
	public Map<String, Object> refundEdit(HttpServletRequest req,Long orderId,Integer type,String reason){
		Map<String , Object> res = new HashMap<>();
		res.put("status", -1);
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if(null == site){
			res.put("message", "未获取到已登录用户的信息！");
			return res;
		}
		if(null == orderId||null == type||null == reason){
			res.put("message", "参数获取失败！");
			return res;
		}
		
		TdOrder order = tdOrderService.findOne(orderId);
		if(0 == type){
			order.setStatusId(9L);
			order.setCheckStatus("审核通过");
			order.setFinishTime(new Date());
			//在此开始调用银行接口进行退款
		}
		if(-1 == type){
			order.setStatusId(8L);
			order.setCheckStatus("审核未通过");
		}
		order.setRemarkInfo(reason);
		tdOrderService.save(order);
		res.put("status", 0);
		return res;
	}
}
