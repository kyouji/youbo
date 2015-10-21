package com.ynyes.youbo.controller.depot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdDiyLog;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiyLogService;
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

	@Autowired
	private TdDiyLogService tdDiyLogService;

	@RequestMapping
	public String site(HttpServletRequest req, Device device, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "redirect:/depot/login";
		}
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
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
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		if (null == site) {
			return "/depot/login";
		}

		return "/depot/site";
	}

	@RequestMapping(value = "/date")
	public String changeDate(HttpServletRequest req, String date, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		String sBeginDate = date + " 00:00:00";
		String sFinishDate = date + " 24:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginDate = null;
		Date finishDate = null;
		try {
			beginDate = sdf.parse(sBeginDate);
			finishDate = sdf.parse(sFinishDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TdOrder> unpayed_list = tdOrderService
				.findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotAndOrderTimeBetweenOrderByOrderTimeDesc(
						site.getId(), beginDate, finishDate);
		List<TdOrder> payed_list = tdOrderService
				.findByDiyIdAndStatusIdAndOrderTimeBetweenOrderByOrderTimeDesc(site.getId(), beginDate, finishDate);

		map.addAttribute("unpayed_list", unpayed_list);
		map.addAttribute("payed_list", payed_list);
		return "/depot/charge_detail";
	}

	@RequestMapping(value = "/cashOrFree")
	public String cashOrFree(HttpServletRequest req, Long id, Long type, Boolean re) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		if (null == diyUser) {
			return "/depot/login";
		}
		TdOrder order = tdOrderService.findOne(id);
		if (null == diyUser.getRealName()) {
			diyUser.setRealName("停车场主");
		}
		TdDiyLog log = new TdDiyLog();
		log.setDiyId(diyUser.getDiyId());
		log.setCreateTime(new Date());

		if (null != order) {
			if (0 == type) {
				order.setThePayType(3L);
				order.setPayTypeTitle("免除费用");
				order.setRemarkInfo(diyUser.getRealName() + "免除了" + order.getCarCode() + "的停车费用");
				log.setActionType("免单操作");
				log.setRemark(diyUser.getRealName() + "免除了" + order.getCarCode() + "的停车费用");
			}
			if (1 == type) {
				order.setThePayType(2L);
				order.setPayTypeTitle("现金支付");
				order.setRemarkInfo(diyUser.getRealName() + "以现金的方式收取了" + order.getCarCode() + "的停车费用");
				log.setActionType("收取现金");
				log.setRemark(diyUser.getRealName() + "以现金的方式收取了" + order.getCarCode() + "的停车费用");
			}
			order.setStatusId(6L);
			order.setFinishTime(new Date());
			tdOrderService.save(order);
			TdUser user = tdUserService.findByUsername(order.getUsername());
			user.setBalance(user.getBalance() + order.getFirstPay());
		}
		if (null != re && re) {
			return "redirect:/depot/depot/myaccount/detail?orderId=" + id;
		} else {
			return "redirect:/depot/charge";
		}
	}

	@RequestMapping("/manageDate")
	public String manageDate(HttpServletRequest req, String date, ModelMap map) {
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("diyUser");
		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		String sBeginDate = date + " 00:00:00";
		String sFinishDate = date + " 24:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = null;
		Date finishTime = null;
		try {
			beginTime = sdf.parse(sBeginDate);
			finishTime = sdf.parse(sFinishDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<TdOrder> xs_list = tdOrderService.findByXszfTime(site.getId(), beginTime, finishTime);
		List<TdOrder> xj_list = tdOrderService.findByXjzfTime(site.getId(), beginTime, finishTime);
		List<TdOrder> md_list = tdOrderService.findByMdTime(site.getId(), beginTime, finishTime);
		List<TdOrder> yk_list = tdOrderService.findByYkTime(site.getId(), beginTime, finishTime);
		List<TdOrder> wy = tdOrderService.findByWyTime(site.getId(), beginTime, finishTime);
		List<TdOrder> wy_list = new ArrayList<>();
		for (TdOrder tdOrder : wy) {
			if (null != tdOrder.getTotalPrice() && tdOrder.getTotalPrice() > 0) {
				wy_list.add(tdOrder);
			}
		}
		map.addAttribute("xs_list", xs_list);
		map.addAttribute("xj_list", xj_list);
		map.addAttribute("md_list", md_list);
		map.addAttribute("yk_list", yk_list);
		return "/depot/manage_detail";
	}
}
