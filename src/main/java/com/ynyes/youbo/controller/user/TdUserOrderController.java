package com.ynyes.youbo.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;

@Controller
@RequestMapping("/user/order")
public class TdUserOrderController {
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("username");
		if (null == username)
        {
            return "redirect:/user/center/login";
        }
		return "/user/order_list";
	}
}
