package com.ynyes.youbo.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdSettingService;

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

	@RequestMapping
	public String index(HttpServletRequest req, Device device, ModelMap map) {

		tdCommonService.setHeader(map, req);

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
			}
			req.getSession().setAttribute("currentOrder", currentOrder);
			map.addAttribute("currentOrder", currentOrder);
			TdSetting setting = tdSettingService.findOne(1L);
			Double firstPay = setting.getFirstPay();
			map.addAttribute("firstPay", firstPay);
		}

		return "/user/index";
	}
}
