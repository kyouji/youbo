package com.ynyes.youbo.controller.depot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping("/depot/charge")
public class TdChargeRecordController {

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@RequestMapping
	public String site(HttpServletRequest req, Device device, ModelMap map) {
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			return "redirect:/depot/login";
		}

		// 获取该停车场所有的已付款订单
		List<TdOrder> payed_list = tdOrderService.findByDiyIdAndStatusIdOrderByOrderTimeDesc(site.getId());
		// 获取该停车场所有未支付订单
		List<TdOrder> unpayed_list = tdOrderService
				.findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotOrderByOrderTimeDesc(site.getId());
		map.addAttribute("payed_list", payed_list);
		map.addAttribute("unpayed_list", unpayed_list);
		return "/depot/charge_record";
	}

	@RequestMapping("/charge")
	public String charge(HttpServletRequest req, Device device, ModelMap map) {
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		if (null == site) {
			return "/depot/login";
		}

		return "/depot/site";
	}

	@RequestMapping(value="/date")
	public String changeDate(HttpServletRequest req,String date,ModelMap map){
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		TdDiySite site = tdDiySiteService.findbyUsername(siteUsername);
		String sBeginDate = date+" 00:00:00";
		String sFinishDate = date+" 24:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date finishDate = null;
		try {
			beginDate = sdf.parse(sBeginDate);
			finishDate = sdf.parse(sFinishDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TdOrder> unpayed_list = tdOrderService.findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotAndOrderTimeBetweenOrderByOrderTimeDesc(site.getId(), beginDate, finishDate);		
		List<TdOrder> payed_list = tdOrderService.findByDiyIdAndStatusIdAndOrderTimeBetweenOrderByOrderTimeDesc(site.getId(), beginDate, finishDate);
		
		map.addAttribute("unpayed_list", unpayed_list);
		map.addAttribute("payed_list", payed_list);
		return "/depot/charge_detail";
	}
	
	@RequestMapping(value="/cashOrFree")
	public String cashOrFree(HttpServletRequest req,Long id,Long type){
		String siteUsername = (String) req.getSession().getAttribute("siteUsername");
		if(null == siteUsername){
			return "/depot/login";
		}
		TdOrder order = tdOrderService.findOne(id);
		
		if(null != order){
			if(0==type){
				order.setPayTypeId(27L);
				order.setPayTypeTitle("免除费用");
				order.setRemarkInfo("免除该订单的停车费用");
			}
			if(1==type){
				order.setPayTypeId(26L);
				order.setPayTypeTitle("现金支付");
				order.setRemarkInfo("该订单由现金支付");
			}
			order.setStatusId(6L);
			order.setFinishTime(new Date());
			tdOrderService.save(order);
		}
		return "redirect:/depot/charge";
	}
	
}
