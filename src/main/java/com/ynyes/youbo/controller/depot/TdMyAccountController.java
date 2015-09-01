package com.ynyes.youbo.controller.depot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/depot/myaccount")
public class TdMyAccountController {
	
	/**
	 *  我的账户首页
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/my_account";
	}
	
	/**
	 *  提现记录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/cashrecord")
    public String cashrecord(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/withdraw_cash_record";
	}
	
	@RequestMapping("/bankcard")
    public String bankcard(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/bankcard";
	}
}
