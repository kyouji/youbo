package com.ynyes.youbo.controller.depot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping("/depot/site")
public class TdDepotSiteController {
	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@RequestMapping
    public String site(HttpServletRequest req, Device device, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("depotuser");
        if (null == username)
        {
            return "redirect:/depot/login";
        }
		return "/depot/site";
	}
	
	
	@RequestMapping("/charge")
	public String charge(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/site";
	}
	
	/**
	 * 退出登录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpServletRequest req, Device device, ModelMap map)
	{
		req.getSession().invalidate();
		return "/depot/index";
	}
}
