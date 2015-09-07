package com.ynyes.youbo.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;

@Controller
@RequestMapping("/user/find")
public class TdUserFindController {
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/user/center/login";
        }
		map.addAttribute("depot_list", tdDiySiteService.findByIsEnableTrue());
		return "/user/find";
	}
	
	/**
	 * 预约
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping("/bespeak")
	public String bespeak(HttpServletRequest req,ModelMap map,Long depotId)
	{
		map.addAttribute("depot", tdDiySiteService.findOne(depotId));
		return "/user/bespeak";
	}
}
