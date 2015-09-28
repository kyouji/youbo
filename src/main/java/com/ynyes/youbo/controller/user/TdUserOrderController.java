package com.ynyes.youbo.controller.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;

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

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdPayTypeService tdPayService;

	@Autowired
	private TdSettingService tdSettingService;

	@RequestMapping
	public String index(String type, HttpServletRequest req, Device device, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		List<TdOrder> all_order_list = tdOrderService.findByUsername(username);
		List<TdOrder> parked_order_list = tdOrderService.findByUsernameAndParked(username);
		List<TdOrder> unparked_order_list = tdOrderService.findByUsernameAndNotParked(username);

		map.addAttribute("all_order_list", all_order_list);
		map.addAttribute("parked_order_list", parked_order_list);
		map.addAttribute("unparked_order_list", unparked_order_list);

		return "/user/order_list";
	}

	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest request, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		if (null == username) {
			return "/user/login";
		}

		List<TdOrder> list = tdOrderService.findCancelOrderByUsername(username);

		map.addAttribute("cancel_list", list);
		return "/user/cancel_list";
	}

	/**
	 * 支付定金的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/first/pay")
	public String firstPay(HttpServletRequest request, Long id, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "/user/login";
		}
		TdOrder order = tdOrderService.findOne(id);
		map.addAttribute("user", user);
		map.addAttribute("order", order);
		return "/user/first_pay";
	}

	/**
	 * 在订单列表取消订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/cancelOrder")
	public String cancelOrder(HttpServletRequest req, Long id) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "/user/login";
		}
		String reason = "用户自主取消";
		TdOrder order = tdOrderService.findOne(id);
		if (null != order) {
			order.setCancelReason(reason);
			order.setStatusId(9L);
			order.setFinishTime(new Date());
			tdOrderService.save(order);
		}
		return "redirect:/user/order";

	}

	/**
	 * 查看订单详情的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/detail")
	public String orderDetail(HttpServletRequest req, Long orderId, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "/user/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if (null != order && null != order.getPayTypeId()) {
			TdPayType payType = tdPayService.findOne(order.getPayTypeId());
			map.addAttribute("payType", payType);
		}
		TdSetting setting = tdSettingService.findOne(1L);
		map.addAttribute("firstPay", setting.getFirstPay());
		map.addAttribute("user", user);
		map.addAttribute("order", order);
		return "/user/order_details";
	}

	/**
	 * 跟踪最新订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/currentOrder")
	public String orderDetail(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "/user/login";
		}
		List<TdOrder> list = tdOrderService.findByUsername(username);
		TdOrder new_order = null;
		if (null != list && list.size() > 0) {
			new_order = list.get(0);
		}
		map.addAttribute("orderId", new_order.getId());
		TdSetting setting = tdSettingService.findOne(1L);
		map.addAttribute("setting", setting);
		return "redirect:/user/order/detail";
	}
}
