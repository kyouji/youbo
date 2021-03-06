package com.ynyes.youbo.controller.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.entity.TdDeposit;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdInformation;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.entity.TdUserComment;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdCouponService;
import com.ynyes.youbo.service.TdDepositService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdGoodsService;
import com.ynyes.youbo.service.TdInformationService;
import com.ynyes.youbo.service.TdOrderGoodsService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdShippingAddressService;
import com.ynyes.youbo.service.TdUserCashRewardService;
import com.ynyes.youbo.service.TdUserCollectService;
import com.ynyes.youbo.service.TdUserCommentService;
import com.ynyes.youbo.service.TdUserConsultService;
import com.ynyes.youbo.service.TdUserPointService;
import com.ynyes.youbo.service.TdUserRecentVisitService;
import com.ynyes.youbo.service.TdUserReturnService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.SiteMagConstant;

/**
 * 用户中心
 * 
 * @author Sharon
 *
 */
@Controller
@RequestMapping(value = "/user/center")
public class TdUserCenterController {

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdGoodsService tdGoodsService;

	@Autowired
	private TdUserReturnService tdUserReturnService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdCouponService tdCoupanService;

	@Autowired
	private TdUserPointService tdUserPointService;

	@Autowired
	private TdUserCollectService tdUserCollectService;

	@Autowired
	private TdUserConsultService tdUserConsultService;

	@Autowired
	private TdUserCommentService tdUserCommentService;

	@Autowired
	private TdUserRecentVisitService tdUserRecentVisitService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdOrderGoodsService tdOrderGoodsService;

	@Autowired
	private TdUserCashRewardService tdUserCashRewardService;

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdBankcardService tdBankcardService;

	@Autowired
	private TdInformationService tdInfoService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDepositService tdDepositService;

	/**
	 * 用户个人中心
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping
	public String user(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		if (null != username) {
			List<TdOrder> list = tdOrderService.findByUsername(username);
			TdOrder currentOrder = null;
			if (null != list && list.size() > 0) {
				currentOrder = list.get(0);
			}
			map.addAttribute("currentOrder", currentOrder);
		}

		TdSetting setting = tdSettingService.findOne(1L);
		tdCommonService.setHeader(map, req);
		map.addAttribute("setting", setting);
		map.addAttribute("user", tdUserService.findByUsername(username));
		map.addAttribute("server_ip", req.getLocalName());
		map.addAttribute("server_port", req.getLocalPort());

		return "/user/user_center";
	}

	/**
	 * 设置
	 * 
	 * @return
	 */
	@RequestMapping(value = "setting")
	public String setting(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		map.addAttribute("user", user);
		return "/user/setting";
	}
	
	@RequestMapping(value = "/deposit/detail/{id}")
	public String depositDetail(@PathVariable Long id, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdDeposit deposit = tdDepositService.findOne(id);
		map.addAttribute("deposit", deposit);
		return "/user/deposit_detail";
	}
	
	
	/**
	 * 设置城市
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setting/changecity")
	public String settingcity() {
		return "/user/setting_city";
	}

	/**
	 * 设置离线地图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setting/map")
	public String settingmap() {
		return "/user/setting_map";
	}

	/**
	 * 关于我们
	 * 
	 * @return
	 */
	@RequestMapping(value = "/about")
	public String about() {
		return "/user/about";
	}

	/**
	 * 银行卡
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bankcard")
	public String bankcard(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		List<TdBankcard> bankcardList = user.getBankcardList();
		map.addAttribute("bankcard_list", bankcardList);
		return "/user/bankcard";
	}

	/**
	 * 银行卡添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bankcard/add")
	public String bankcardAdd(TdBankcard bankcard, HttpServletRequest req, ModelMap map) {
		map.addAttribute("paytape_list", tdPayTypeService.findAll());
		return "/user/bankcard_add";
	}
	
	@RequestMapping(value = "/cashrecord")
	public String cashrecord(HttpServletRequest req,ModelMap map){
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == username) {
			return "redirect:/user/center/login";
		}
		List<TdDeposit> deposit_list = tdDepositService.findByUserIdOrderByDepositDateDesc(user.getId());
		map.addAttribute("deposit_list", deposit_list);
		return "/user/withdraw_record";
	}

	/**
	 * 保存银行卡
	 * 
	 * @param bankcard
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/bankcard/add", method = RequestMethod.POST)
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
	 * 用户反馈页面
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/comment")
	public String comment(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		return "/user/feedback";
	}

	/**
	 * 提交意见的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/comment/save")
	public String commentSave(HttpServletRequest req, String title, String content) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		TdInformation info = new TdInformation();
		info.setUserId(user.getId());
		info.setTitle(title);
		info.setStatusId(1L);
		info.setContent(content);
		info.setReleaseTime(new Date());
		tdInfoService.save(info);
		return "redirect:/user/center/message";
	}

	/**
	 * 用户反馈
	 * 
	 * @author mdj
	 * @param req
	 * @param tdComment
	 * @param code
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> commentAdd(HttpServletRequest req, TdUserComment tdComment, ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("username");

		if (null == tdComment.getContent() || tdComment.getContent().equals("")) {
			res.put("message", "内容不能为空！");
			return res;
		}

		tdComment.setCommentTime(new Date());
		tdComment.setIsReplied(false);
		tdComment.setNegativeNumber(0L);
		tdComment.setPositiveNumber(0L);
		tdComment.setUsername(username);

		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

		if (null != user) {
			tdComment.setUserHeadUri(user.getHeadImageUri());
		}

		tdComment.setStatusId(0L);

		tdUserCommentService.save(tdComment);
		res.put("code", 0);

		return res;
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
		// 获取到当前登陆的用户
		String username = (String) req.getSession().getAttribute("username");
		if (null == username || username.isEmpty()) {
			return "redirect:/user/center/login";
		}
		// 通过用户名得到当前登陆用户的一系列信息
		TdUser tdUser = tdUserService.findByUsername(username);
		List<TdInformation> infos = tdInfoService.findByUserIdAndParentIdIsNullOrderByReleaseTimeDesc(tdUser.getId());
		map.addAttribute("infos", infos);
		return "/user/message_center";
	}

	/**
	 * @author dengxiao 消息详情
	 */
	@RequestMapping("/message/content/{id}")
	public String messageContent(@PathVariable Long id, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		if (null == id) {
			return "/user/error404";
		}

		TdInformation theInfo = tdInfoService.findOne(id);
		if (null != theInfo) {
			TdInformation subInfo = tdInfoService.findByParentId(theInfo.getId());
			if (null != subInfo && 0L == subInfo.getStatusId()) {
				subInfo.setStatusId(1L);
				theInfo.setIsSubRead(true);
				tdInfoService.save(subInfo);
				tdInfoService.save(theInfo);
				map.addAttribute("subInfo", subInfo);
			}
		}
		map.addAttribute("info", theInfo);
		return "/user/message_content";
	}

	/**
	 * 个人中心
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/info")
	public String info(HttpServletRequest req, Device device, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		map.addAttribute("user", user);
		return "/user/user_center_info";
	}

	/**
	 * 个人中心
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/info/edit")
	public String infoEdit(HttpServletRequest req, String editType, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		switch (editType) {
		case "username":// 用户名
			return "/user/user_info_username";
		case "mobile": // 手机号
			return "/user/user_info_mobile";
		case "plate": // 车牌号
			return "/user/user_info_plate";
		case "password":// 登录密码
			return "/user/user_info_password";
		case "paykey": // 支付密码
			return "/user/user_info_paykey";

		default:
			break;
		}
		return "/user/center";
	}

	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest req, Device device, ModelMap map, String username, String password) {
		String curentuser = (String) req.getSession().getAttribute("username");
		if (curentuser != null) {
			return "redirect:/user/center";
		}
		return "/user/login";
	}

	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest req, HttpServletResponse resp, String username, String password,
			ModelMap map) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("code", 1);

		// String user = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (user == null) {
			res.put("msg", "用户不存在!");
			return res;
		}
		if (!user.getPassword().equals(password)) {
			res.put("msg", "密码错误！");
			return res;
		}
		res.put("code", 0);

		req.getSession().setMaxInactiveInterval(60 * 60 * 24);
		req.getSession().setAttribute("username", username);

		try {
			String encode_username = URLEncoder.encode(username, "utf-8");
			String encode_password = URLEncoder.encode(password, "utf-8");
			Cookie cookie_username = new Cookie("cookie_username", encode_username);
			cookie_username.setPath("/");
			cookie_username.setMaxAge(60 * 60 * 24 * 30);
			Cookie cookie_password = new Cookie("cookie_password", encode_password);
			cookie_password.setPath("/");
			cookie_password.setMaxAge(60 * 60 * 24 * 30);
			resp.addCookie(cookie_username);
			resp.addCookie(cookie_password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * 用注册
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/register")
	public String register(HttpServletRequest req, Device device, ModelMap map) {
		return "/user/register";
	}

	/**
	 * 退出登录
	 * 
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpServletRequest req, HttpServletResponse resp, Device device, ModelMap map,
			@CookieValue(value = "cookie_username", required = false) String username,
			@CookieValue(value = "cookie_password", required = false) String password) {
		req.getSession().invalidate();
		try {
			String encode_username = URLEncoder.encode(username, "utf-8");
			String encode_password = URLEncoder.encode(password, "utf-8");
			Cookie cookie_username = new Cookie("cookie_username", encode_username);
			cookie_username.setPath("/");
			cookie_username.setMaxAge(0);
			Cookie cookie_password = new Cookie("cookie_password", encode_password);
			cookie_password.setPath("/");
			cookie_password.setMaxAge(0);
			resp.addCookie(cookie_username);
			resp.addCookie(cookie_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user";
	}

	/**
	 * @author dengxiao 修改密码时，验证原密码是否输入正确的方法
	 */
	@RequestMapping("/password/check")
	@ResponseBody
	public Map<String, Object> checkOldPassword(String param, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "n");
		res.put("info", "原密码输入错误!");
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null != user) {
			if (user.getPassword().equals(param)) {
				res.put("status", "y");
				res.put("info", "");
			}
		}
		return res;
	}

	@RequestMapping(value = "/deposit")
	public String deposit(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		List<TdPayType> all_pay_type = tdPayTypeService.findAll();
		map.addAttribute("all_pay_type", all_pay_type);
		map.addAttribute("user", user);
		return "/user/withdraw";
	}

	@RequestMapping(value = "/password/save")
	public String savePassword(String password, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		user.setPassword(password);
		tdUserService.save(user);
		return "redirect:/user/center/info";
	}

	@RequestMapping(value = "/info/pay")
	public String pay(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		if (null != user.getPayPassword()) {
			String payPassword = user.getPayPassword();
			map.addAttribute("payPassword", payPassword);
		}
		return "/user/user_info_pay_password";

	}

	/**
	 * @author dengxiao 修改支付密码时，验证原密码是否输入正确的方法
	 */
	@RequestMapping("/pay/check")
	@ResponseBody
	public Map<String, Object> checkPayPassword(String param, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", "n");
		res.put("info", "原支付密码输入错误!");
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null != user) {
			if (user.getPayPassword().equals(param)) {
				res.put("status", "y");
				res.put("info", "");
			}
		}
		return res;
	}

	@RequestMapping(value = "/deposit/check")
	@ResponseBody
	public Map<String, Object> depositCheck(Double money, String pwd, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("stauts", -1);

		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null != user) {
			if (null != user.getPayPassword() && pwd.equals(user.getPayPassword())) {
				// 以下代码用于生成订单编号
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String sDate = sdf.format(date);
				Random random = new Random();
				Integer suiji = random.nextInt(900) + 100;
				String orderNum = "TX" + sDate + suiji;
				// 生成订单编号结束
				TdDeposit deposit = new TdDeposit();
				deposit.setNum(orderNum);
				deposit.setMoney(money);
				deposit.setDepositDate(new Date());
				deposit.setUserId(user.getId());
				deposit.setUsername(user.getUsername());
				tdDepositService.save(deposit);
				res.put("message", "提现申请提交成功，请耐心等待！");
			} else {
				res.put("message", "密码错误！");
				return res;
			}
		}else{
			res.put("message", "未找到指定用户！");
			return res;
		}

		res.put("status", 0);
		return res;
	}

	@RequestMapping(value = "/pay/save")
	public String savePayPassword(String password, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		user.setPayPassword(password);
		tdUserService.save(user);
		return "redirect:/user/center/info";
	}

	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		// 获取当前年份
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		map.addAttribute("year", year);
		return "/user/detail";
	}

	@RequestMapping(value = "/detail/find")
	public String findDetail(HttpServletRequest request, Integer year, Integer month, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		if (null == year || null == month) {
			return "redict:/user/center/detail";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 起始时间
		String sBeginDate = year + "-" + month + "-1";
		// 结束时间
		String sFinishDate = null;
		if (12 == month) {
			sFinishDate = (year + 1) + "-1-1";
		} else {
			sFinishDate = year + "-" + (month + 1) + "-1";
		}

		Date beginDate = null;
		Date finishDate = null;
		try {
			beginDate = sdf.parse(sBeginDate);
			finishDate = sdf.parse(sFinishDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<TdOrder> orders = tdOrderService.findByUsernameAndFinishTimeBetween(username, beginDate, finishDate);
		List<TdOrder> the_orders = new ArrayList<>();
		Double totalPrice = new Double(0);
		for (TdOrder tdOrder : orders) {
			if (null != tdOrder.getTotalPrice() && 4L != tdOrder.getStatusId() && tdOrder.getTotalPrice() > 0) {
				totalPrice += tdOrder.getTotalPrice();
				the_orders.add(tdOrder);
			} else if (null != tdOrder.getFirstPay() && tdOrder.getFirstPay() > 0) {
				totalPrice += tdOrder.getFirstPay();
				the_orders.add(tdOrder);
			}
		}

		map.addAttribute("orders", the_orders);
		map.addAttribute("year", year);
		map.addAttribute("month", month);
		map.addAttribute("totalPrice", totalPrice);

		return "/user/detail_month";
	}

	@RequestMapping(value = "/info/nickname")
	public String nickname(HttpServletRequest request, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		map.addAttribute("nickname", user.getNickname());
		return "/user/user_info_nickname";
	}

	@RequestMapping(value = "/nickname/edit")
	public String nicknameEdit(HttpServletRequest request, String nickname) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		user.setNickname(nickname);
		tdUserService.save(user);
		return "redirect:/user/center/info";
	}

	@RequestMapping(value = "/share")
	public String share(HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		return "/user/share";
	}

	@RequestMapping(value = "/info/phone")
	public String carCode(HttpServletRequest request, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		map.addAttribute("phone", user.getMobile());
		return "/user/user_info_phone";
	}

	@RequestMapping(value = "/phone/edit")
	public String carcodeEdit(HttpServletRequest request, String phone) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}
		user.setMobile(phone);
		tdUserService.save(user);
		return "redirect:/user/center/info";
	}

	@RequestMapping(value = "/headImg", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/user/center/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		if (!".jpg".equals(ext) && !".png".equals(ext)) {
			return "redirect:/user/center/info";
		}

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
			user.setHeadImageUri("/images/" + fileName);
			tdUserService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/center/info";
	}

	@RequestMapping(value = "/recharge")
	public String reCharge(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		return "/user/recharge";
	}

	@RequestMapping(value = "/frost")
	public String frost(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		TdUser user = tdUserService.findByUsername(username);
		if (null != user.getIsFrost() && user.getIsFrost()) {
			user.setIsFrost(false);
		} else {
			user.setIsFrost(true);
		}
		tdUserService.save(user);
		return "redirect:/user/center";
	}
}