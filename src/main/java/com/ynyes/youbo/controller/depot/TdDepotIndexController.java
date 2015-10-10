package com.ynyes.youbo.controller.depot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.util.SiteMagConstant;

@Controller
@RequestMapping("/depot")
public class TdDepotIndexController {
	
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@RequestMapping(method=RequestMethod.GET)
    public String index(HttpServletRequest req, Device device, ModelMap map) {
		
		tdCommonService.setHeader(map, req);
		System.err.println(SiteMagConstant.imagePath);
		
		//停车场首页广告
		TdAdType adType = tdAdTypeService.findByTitle("停车场顶部轮播");

        if (null != adType) {
            map.addAttribute("depot_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }
		return "/depot/index";
	}
}
