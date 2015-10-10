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
@RequestMapping("/depot/info")
public class TdDepotInfoController {
	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
	@RequestMapping
    public String site(HttpServletRequest req, Device device, ModelMap map)
	{
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = (TdDiySite) req.getSession().getAttribute("site");
        if (null == diyUser)
        {
            return "redirect:/depot/login";
        }
        map.addAttribute("site",site);
		return "/depot/depot_info";
	}
	
	
	@RequestMapping("/charge")
	public String charge(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/site";
	}
	
	@RequestMapping(value="/edit")
	@ResponseBody
	public Map<String, Object> editInfo(HttpServletRequest req,Integer theNum){
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if(null == site){
			res.put("message", "未获取到车库信息，修改失败！");
			return res;
		}
		if(null == theNum){
			res.put("message", "参数错误，修改失败！");
			return res;
		}
		site.setParkingNowNumber(theNum);
		tdDiySiteService.save(site);
		res.put("status", 0);
		res.put("message", "修改成功！");
		return res;
	}
}
