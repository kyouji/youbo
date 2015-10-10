package com.ynyes.youbo.controller.depot;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdOrderService;

@Controller
@RequestMapping("/depot/search")
public class TdDepotSearchController {

	@Autowired
	private TdOrderService tdOrderService;
	
	@RequestMapping
	public String search(HttpServletRequest req){
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if(null == diyUser){
			return "/depot/login";
		}
		return "/depot/search";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String getOrders(HttpServletRequest req,String keywords,ModelMap map){
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		List<TdOrder> search_list = tdOrderService.findByDiyIdAndCarCodeContainingOrderByOrderTimeDesc(diyUser.getDiyId(), keywords);
		map.addAttribute("search_list", search_list);
		map.addAttribute("keywords", keywords);
		return "/depot/search";
	}
}
