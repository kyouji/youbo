package com.ynyes.youbo.controller.user;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.unionpay.DemoBase;
import com.unionpay.acp.sdk.SDKConfig;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdRechargeLog;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdRechargeLogService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping(value = "/user/pay")
public class TdPayController {

	@Autowired
	private TdRechargeLogService tdRechargetLogService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@RequestMapping(value = "/alipay/recharge")
	public String aliRecharge(HttpServletRequest req, HttpServletResponse resp, Double money, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		TdUser user = tdUserService.findByUsername(username);

		// -----请求参数-----

		// 支付类型
		String payment_type = "1";

		// 页面跳转同步通知页面路径
		String return_url = "http://www.youbo100.cn/user/pay/alipay/recharge/return";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		// 以下代码用于生成充值单编号
		Date date = new Date();
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = "CZ" + sDate + suiji;
		String out_trade_no = orderNum;
		String subject = "优泊天下账户充值";
		String show_url = "http://www.youbo100.cn/user";
		String total_fee = money + "";

		TdRechargeLog log = new TdRechargeLog();
		log.setNum(orderNum);
		log.setMoney(money);
		log.setUserId(user.getId());
		log.setUsername(username);
		log.setRechargeDate(new Date());
		log.setStatusId(new Long(-1));
		tdRechargetLogService.save(log);

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		map.put("code", sHtmlText);
		return "/user/waiting_pay";
	}

	@RequestMapping(value = "/alipay/recharge/return")
	public String alipayReturn(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		String out_trade_no = null;
		String trade_no = null;
		String trade_status = null;
		try {
			// 商户订单号
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		verify_result = true;
		if (verify_result) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				TdRechargeLog log = tdRechargetLogService.findByNum(out_trade_no);
				log.setStatusId(new Long(0));
				log = tdRechargetLogService.save(log);
				if (null != log && null != log.getStatusId() && 0 == log.getStatusId()) {
					TdUser user = tdUserService.findOne(log.getUserId());
					user.setBalance(user.getBalance() + log.getMoney());
					tdUserService.save(user);
				}
			}

		} else {
			// 该页面可做页面美工编辑
		}
		return "/user/pay_success";
	}

	@RequestMapping(value = "/unionpay/recharge")
	public String unionPay(HttpServletRequest req, HttpServletResponse resp, Double money, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", "http://www.youbo100.cn/user");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", "898510175231000");

		// 以下代码用于生成充值单编号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		int zs = (int) (money * 100);
		String orderNum = "CZ" + sDate + suiji;

		// 商户订单号，8-40位数字字母
		data.put("orderId", orderNum);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", zs + "");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		Map<String, String> submitFromData = DemoBase.signData(data);

		// 交易请求url 从配置文件读取
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		String html = DemoBase.createHtml(requestFrontUrl, submitFromData);
		map.addAttribute("code", html);
		return "/user/waiting_pay";
	}

	@RequestMapping(value = "/unionpay/online")
	public String unionPayOnline(HttpServletRequest req, HttpServletResponse resp, Double money, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 01-消费
		data.put("txnType", "01");
		// 交易子类型 01:自助消费 02:订购 03:分期付款
		data.put("txnSubType", "01");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", "http://www.youbo100.cn/user");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", "898510175231000");

		// 以下代码用于生成充值单编号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		int zs = (int) (money * 100);
		String orderNum = "CZ" + sDate + suiji;

		// 商户订单号，8-40位数字字母
		data.put("orderId", orderNum);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额，单位分
		data.put("txnAmt", zs + "");
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		// data.put("reqReserved", "透传信息");
		// 订单描述，可不上送，上送时控件中会显示该信息
		// data.put("orderDesc", "订单描述");

		Map<String, String> submitFromData = DemoBase.signData(data);

		// 交易请求url 从配置文件读取
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		String html = DemoBase.createHtml(requestFrontUrl, submitFromData);
		map.addAttribute("code", html);
		return "/user/waiting_pay";
	}

	@RequestMapping(value = "/alipay/online")
	public String aliOnline(HttpServletRequest req, HttpServletResponse resp, Double money, ModelMap map, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		TdUser user = tdUserService.findByUsername(username);

		// -----请求参数-----

		// 支付类型
		String payment_type = "1";

		// 页面跳转同步通知页面路径
		String return_url = "http://www.youbo100.cn:8020/user/pay/alipay/online/return";

		TdOrder order = tdOrderService.findOne(id);
		String subject = "优泊天下线上支付";
		String show_url = "http://www.youbo100.cn/user";
		String total_fee = money + "";

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", order.getOrderNumber());
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_id", AlipayConfig.seller_id);
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		map.put("code", sHtmlText);
		return "/user/waiting_pay";
	}

	@RequestMapping(value = "/alipay/online/return")
	public String alipayOnlineReturn(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		String out_trade_no = null;
		String trade_no = null;
		String trade_status = null;
		try {
			// 商户订单号
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		verify_result = true;
		if (verify_result) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);
				TdDiySite site = tdDiySiteService.findOne(order.getDiyId());
				TdUser user = tdUserService.findByUsername(order.getUsername());
				TdSetting setting = tdSettingService.findOne(1L);
				if ( 1L == order.getStatusId()) {
					// 判断停车场是否还有剩余车位
					if (site.getParkingNowNumber() > 0) {
						// 如果还有剩余的车位，就开始判断停车场是否有摄像头
						if (null != site.getIsCamera() && site.getIsCamera()) {// 有摄像头就自动预约成功
							if (!(site.getParkingNowNumber() > 0)) {
								order.setFirstPay(0.00);
								user.setBalance(user.getBalance() + setting.getFirstPay());
								order.setStatusId(9L);
								order.setCancelReason("指定停车场无剩余车位");
								tdOrderService.save(order);
								tdUserService.save(user);
							}
							order.setStatusId(3L);
							order.setReserveTime(new Date());
							tdOrderService.save(order);
							tdDiySiteService.save(site);
						} else {// 如果没有摄像头就需要等待泊车员手动确认预约
							order.setStatusId(2L);
							tdOrderService.save(order);
						}
					} else {// 剩余车位不足即预定失败，订单结束
						order.setFirstPay(0.00);
						// 返还定金
						user.setBalance(user.getBalance() + setting.getFirstPay());
						// 设置订单状态为交易结束
						order.setStatusId(9L);
						// 设置订单取消的原因
						order.setCancelReason("指定停车场无剩余车位");

						tdOrderService.save(order);
						// 设置消息提示
					}
				} else if (4L == order.getStatusId()) {
					order.setThePayType(0L);
					order.setStatusId(6L);
					order.setFinishTime(new Date());
					order.setRemarkInfo("收取了" + setting.getPoundage() * 100 + "%的手续费");
					site.setAllMoney(site.getAllMoney() + order.getTotalPrice());
					tdOrderService.save(order);
					tdDiySiteService.save(site);
				}
			}
		} else {
			// 该页面可做页面美工编辑
		}
		return "/user/pay_success";
	}

	@ModelAttribute
	public void init() {
		/**
		 * 参数初始化 在java main 方式运行时必须每次都执行加载 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
	}
}
