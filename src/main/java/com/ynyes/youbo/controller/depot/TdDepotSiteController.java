package com.ynyes.youbo.controller.depot;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping("/depot/site")
public class TdDepotSiteController {

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdDiySiteService TdDiySiteService;

	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@RequestMapping
	public String site(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		map.addAttribute("site", site);
		return "/depot/site";
	}

	@RequestMapping("/charge")
	public String charge(HttpServletRequest req, Device device, ModelMap map) {
		return "/depot/site";
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
	public String exit(HttpServletRequest req, Device device, ModelMap map) {
		req.getSession().invalidate();
		return "/depot/index";
	}

	@RequestMapping(value = "/changePayType")
	public String change(HttpServletRequest req, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
		}
		TdDiySite site = TdDiySiteService.findOne(diyUser.getDiyId());
		map.addAttribute("site", site);
		return "/depot/change_pay_type";
	}

	@RequestMapping(value = "/savePrice")
	@ResponseBody
	public Map<String, Object> savePrice(HttpServletRequest req, Long type, Double maxPrice, Long dayType, Long nightType,
			Double dayOncePrice, Long dayBaseTime, Double dayBasePrice, Double dayHourPrice, Double nightOncePrice,
			Long nightBaseTime, Double nightBasePrice, Double nightHourPrice) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		if(null == site){
			res.put("info", "未能获取到当前登陆的车库信息，操作失败！");
			return res;
		}
		if(site.getIsCamera()){
			if(0==dayType){
				site.setDayType(dayType);
				site.setDayBaseTime(dayBaseTime);
				site.setDayBasePrice(dayBasePrice);
				site.setDayHourPrice(dayHourPrice);
			}else{
				site.setDayType(dayType);
				site.setDayOncePrice(dayOncePrice);
			}
			
			if(0==nightType){
				site.setNightType(nightType);
				site.setNightBaseTime(nightBaseTime);
				site.setNightBasePrice(nightBasePrice);
				site.setNightHourPrice(nightHourPrice);
			}else{
				site.setNightType(nightType);
				site.setNightOncePrice(nightOncePrice);
			}
		}else{
			if(0==dayType){
				site.setDayType(dayType);
				site.setDayBaseTime(dayBaseTime);
				site.setDayBasePrice(dayBasePrice);
				site.setDayHourPrice(dayHourPrice);
			}else{
				site.setDayType(dayType);
				site.setDayOncePrice(dayOncePrice);
			}
		}
		site.setMaxPrice(maxPrice);
		tdDiySiteService.save(site);
		res.put("info", "操作成功！");
		res.put("status", 0);
		return res;
	}
}
