package com.ynyes.youbo.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdOrderService;

@Controller
@RequestMapping("/user/order")
public class TdUserOrderController {
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@RequestMapping
    public String index(String type,HttpServletRequest req, Device device, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("username");
		if (null == username)
        {
            return "redirect:/user/center/login";
        }
		
		List<TdOrder> all_order_list = tdOrderService.findByUsername(username);
		List<TdOrder> finish_order_list = tdOrderService.findByUsernameAndStatusId(username);
		List<TdOrder> unfinish_order_list = tdOrderService.findByUsernameAndStatusIdNot(username);
		
		map.addAttribute("all_order_list",all_order_list);
		map.addAttribute("finish_order_list",finish_order_list);
		map.addAttribute("unfinish_order_list",unfinish_order_list);
		
		return "/user/order_list";
	}
}
