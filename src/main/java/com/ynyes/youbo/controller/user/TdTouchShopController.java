package com.ynyes.youbo.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdArticleCategoryService;
import com.ynyes.youbo.service.TdArticleService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdUserRecentVisitService;

/**
 * 
 * 同盟店
 *
 */
@Controller
@RequestMapping("/touch/shop")
public class TdTouchShopController {
	@Autowired 
	private TdArticleService tdArticleService;
	
	@Autowired 
    private TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired 
    private TdDiySiteService tdDiySiteService;
	
	@Autowired 
    private TdOrderService tdOrderService;
		
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;
    
	@RequestMapping("/list")
    public String infoList(Integer page, 
                            ModelMap map,
                            Integer cid,
                            HttpServletRequest req){
	    
	    tdCommonService.setHeader(map, req);
        
        if (null == cid)
        {
            cid = 0;
        }
        
        String[] cityArray = {"昆明", "曲靖", "大理"};
        
        map.addAttribute("city_list", cityArray);
        map.addAttribute("cid", cid);
        
        
        List<TdDiySite> tdDiySitelist;
        switch (cid)
        {
        case 1:
            map.addAttribute("shop_list", tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("曲靖"));
            
            tdDiySitelist  = tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("曲靖");
            if (null != tdDiySitelist) {
            	 for (TdDiySite diySite : tdDiySitelist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
                     map.addAttribute("shop_orderComment_"+diySite.getId(), tdDiySiteService.ContdiysiteComment(diySite.getId()));
                     map.addAttribute("shop_serviceStars"+diySite.getId(), tdDiySiteService.diysiteServiceStars(diySite.getId()));
                 }
			}
                   
            break;
            
        case 2:
            map.addAttribute("shop_list", tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("大理"));
            
            tdDiySitelist  = tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("大理");
            if (null != tdDiySitelist) {
            	 for (TdDiySite diySite : tdDiySitelist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
                     map.addAttribute("shop_orderComment_"+diySite.getId(), tdDiySiteService.ContdiysiteComment(diySite.getId()));
                     map.addAttribute("shop_serviceStars"+diySite.getId(), tdDiySiteService.diysiteServiceStars(diySite.getId()));
                 }
			}
            break;
            
        default:
          
            map.addAttribute("shop_list", tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("昆明"));
            tdDiySitelist  = tdDiySiteService.findByCityAndIsEnableTrueOrderBySortIdAsc("昆明");
            if (null != tdDiySitelist) {
            	 for (TdDiySite diySite : tdDiySitelist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
                     map.addAttribute("shop_orderComment_"+diySite.getId(), tdDiySiteService.ContdiysiteComment(diySite.getId()));
//                     int a  = tdDiySiteService.ContdiysiteComment(diySite.getId());
                     map.addAttribute("shop_serviceStars"+diySite.getId(), tdDiySiteService.diysiteServiceStars(diySite.getId()));
                 }
			}
        }
        
        return "/touch/shop_list";
    }
	
	@RequestMapping("/{id}")
    public String shop(@PathVariable Long id, ModelMap map, HttpServletRequest req){
	    tdCommonService.setHeader(map, req);
	    
	    map.addAttribute("shop", tdDiySiteService.findOne(id));
	    
        return "/touch/shop_detail";
    }
}
