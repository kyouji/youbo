package com.ynyes.youbo.controller.user;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.VerifServlet;

@Controller
@RequestMapping(value = "/user/reg")
public class TdUserRegController {

	@Autowired
	private TdUserService tdUserService;

	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public void verify(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		VerifServlet randomValidateCode = new VerifServlet();
		randomValidateCode.getRandcode(request, response);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String reg(String username, String mobile, String password, String email, String smsCode, String code,
			String carCode, Long shareId, HttpServletResponse resp, HttpServletRequest request) {
		String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
		if (!codeBack.equalsIgnoreCase(code)) {
			if (null == shareId) {
				return "redirect:/reg?errCode=1&name= " + username + "&carCode=" + carCode;
			} else {
				return "redirect:/reg?errCode=1&shareId=" + shareId + "&name= " + username + "&carCode=" + carCode;
			}
		}

		/**
		 * 因为是车牌号码注册，所以将username字段的值赋值给carCode
		 * 
		 * @author dengxiao
		 */
		carCode = username;

		TdUser user = tdUserService.addNewUser(username, password, mobile, email, carCode);
		user.setRoleId(1L);

		// 设置新的用户为C端用户
		user.setRoleId(1L);

		user.setBalance(0.00);

		user = tdUserService.save(user);

		request.getSession().setAttribute("username", username);

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

		String referer = (String) request.getAttribute("referer");

		if (null != request.getAttribute("referer")) {
			return "redirect:" + referer;
		}

		if (null == shareId) {
			return "redirect:/user";
		}

		return "redirect:/user";
	}

	@RequestMapping(value = "/check/{type}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(@PathVariable String type, String param, HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");
		if ("username".equalsIgnoreCase(type)) {
			TdUser user = tdUserService.findByUsername(param);
			if (null != user) {
				res.put("info", "该车牌号已注册！");
				return res;
			}
		}
		if ("yzm".equalsIgnoreCase(type)) {
			String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
			if (!param.equalsIgnoreCase(codeBack)) {
				res.put("info", "验证码错误！");
				return res;
			}
		}
		res.put("status", "y");
		return res;
	}

}