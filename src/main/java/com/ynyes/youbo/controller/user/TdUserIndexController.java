package com.ynyes.youbo.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping("/user")
public class TdUserIndexController {
	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdAdTypeService tdAdTypeService;

	@Autowired
	private TdAdService tdAdService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdUserService tdUserService;

	@RequestMapping
	public String index(HttpServletRequest req, Device device, ModelMap map, Boolean reload,
			@CookieValue(value = "cookie_username", required = false) String cookie_username,
			@CookieValue(value = "cookie_password", required = false) String cookie_password) {

		tdCommonService.setHeader(map, req);

		try {
			if (null != cookie_username && null != cookie_password) {
				cookie_username = URLDecoder.decode(cookie_username, "utf-8");
				cookie_password = URLDecoder.decode(cookie_password, "utf-8");
				TdUser user = tdUserService.findByUsername(cookie_username);
				if (null != user && null != user.getPassword() && user.getPassword().equals(cookie_password)) {
					req.getSession().setAttribute("username", cookie_username);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 停车场首页广告
		TdAdType adType = tdAdTypeService.findByTitle("用户端顶部轮播");

		if (null != adType) {
			map.addAttribute("depot_ad_list", tdAdService.findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
		}

		String username = (String) req.getSession().getAttribute("username");

		if (null != username) {
			List<TdOrder> list = tdOrderService.findByUsername(username);
			TdOrder currentOrder = null;
			if (null != list && list.size() > 0) {
				currentOrder = list.get(0);
				TdDiySite site = tdDiySiteService.findOne(currentOrder.getDiyId());
				map.addAttribute("x", site.getLatitude());
				map.addAttribute("y", site.getLongitude());
			}
			req.getSession().setAttribute("currentOrder", currentOrder);
			map.addAttribute("currentOrder", currentOrder);
			TdSetting setting = tdSettingService.findOne(1L);
			Double firstPay = setting.getFirstPay();
			map.addAttribute("firstPay", firstPay);
		}

		if (null == reload) {
			reload = true;
		}
		map.addAttribute("reload", reload);

		return "/user/index";
	}
	
	@RequestMapping(value = "/test")
	public String test(){
		return "/user/test";
	}
}
