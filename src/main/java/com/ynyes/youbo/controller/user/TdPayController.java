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
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.ynyes.youbo.entity.TdRechargeLog;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdRechargeLogService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping(value = "/user/pay")
public class TdPayController {

	@Autowired
	private TdRechargeLogService tdRechargetLogService;

	@Autowired
	private TdUserService tdUserService;

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

		String notify_url = "http://www.youbo100.cn/user/pay/alipay/recharge/return";

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
		log.setRechargeDate(new Date());
		log.setStatusId(new Long(-1));
		tdRechargetLogService.save(log);

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
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
				tdRechargetLogService.save(log);
				TdUser user = tdUserService.findOne(log.getUserId());
				user.setBalance(user.getBalance() + log.getMoney());
				tdUserService.save(user);
			}

		} else {
			// 该页面可做页面美工编辑
		}
		return "/user/pay_success";
	}
}
