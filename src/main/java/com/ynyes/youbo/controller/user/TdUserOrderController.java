package com.ynyes.youbo.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.DecimalFormat;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.DiySiteFee;

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

	@Autowired
	private TdDiySiteService tdDiySiteService;

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
	public String cancelOrder(HttpServletRequest req, Boolean isDetail, Long id) {
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
		if (true == isDetail) {
			return "redirect:/user/order/detail?orderId=" + id;
		}
		return "redirect:/user/order";

	}

	/**
	 * 结算订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/clearing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearing(HttpServletRequest req, Long id, Long type) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdOrder order = tdOrderService.findOne(id);
		TdSetting setting = tdSettingService.findOne(1L);
		TdDiySite site = tdDiySiteService.findOne(order.getDiyId());

		if (1 == type) {
			if (user.getBalance() < setting.getFirstPay()) {
				/**
				 * 此处将在后期修改为提交到第三方支付
				 * 
				 * @author dengxiao
				 */
				res.put("message", "余额不足");
				return res;
			} else {
				// 余额足够便扣除定金
				user.setBalance(user.getBalance() - setting.getFirstPay());
				// 设置订单状态为已支付定金
				order.setFirstPay(setting.getFirstPay());
				order.setStatusId(2L);
				// 判断停车场是否还有剩余车位
				if (site.getParkingNowNumber() > 0) {
					// 如果还有剩余的车位，就开始判断停车场是否有摄像头
					if (null != site.getIsCamera() && site.getIsCamera()) {// 有摄像头就自动预约成功
						if (!(site.getParkingNowNumber() > 0)) {
							order.setFirstPay(0.00);
							user.setBalance(user.getBalance() + setting.getFirstPay());
							order.setStatusId(9L);
							order.setCancelReason("指定停车场无剩余车位");
							res.put("message", "抱歉，已经没有车位了，预定失败！");
							tdOrderService.save(order);
							return res;
						}
						site.setParkingNowNumber(site.getParkingNowNumber() - 1);
						order.setStatusId(3L);
						res.put("message", "预约成功，请在2个小时之内到达指定的车库停车！");
						// 开始判定2小时后车辆是否进入车库
						final Long orderId = order.getId();
						final Double firstPay = setting.getFirstPay();
						final TdUser theUser = user;
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								TdOrder theOrder = tdOrderService.findOne(orderId);
								TdDiySite theSite = tdDiySiteService.findOne(theOrder.getDiyId());
								// 如果两小时之后订单的状态还是“预约成功”，则表示没有进入车库
								if (3L == theOrder.getStatusId()) {
									// 设置取消时间
									theOrder.setFinishTime(new Date());
									// 设置订单状态为交易取消
									theOrder.setStatusId(9L);
									// 设置取消订单的原因
									theOrder.setCancelReason("预约2小时后车辆未进入指定车库");
									// 判断消费了多少钱
									if (null != theSite && null != theSite.getIsCamera() && !theSite.getIsCamera()) {
										Double price = DiySiteFee.GET_PARKING_PRICE(theSite, theOrder.getReserveTime(),
												theOrder.getFinishTime());
										theOrder.setTotalPrice(price);
									}
									// 如果定金还有剩余就退还剩余部分的钱
									if (firstPay > theOrder.getTotalPrice()) {
										Double left = firstPay - theOrder.getTotalPrice();
										theUser.setBalance(theUser.getBalance() + left);
									}
									//保存已经改动的订单信息和用户信息
									tdOrderService.save(theOrder);
									tdUserService.save(theUser);
								}
							}
						}, 1000 * 60 * 60 * 2);
					} else {// 如果没有摄像头就需要等待泊车员手动确认预约
						res.put("message", "定金已支付，等待工作人员确认预约！");
					}
				} else {// 剩余车位不足即预定失败，订单结束
					order.setFirstPay(0.00);
					// 返还定金
					user.setBalance(user.getBalance() + setting.getFirstPay());
					// 设置订单状态为交易结束
					order.setStatusId(9L);
					// 设置订单取消的原因
					order.setCancelReason("指定停车场无剩余车位");

					tdOrderService.save(order);
					// 设置消息提示
					res.put("message", "抱歉，已经没有车位了，预定失败！");
					return res;
				}
			}

		} else if (0 == type) {
			order.setFinishTime(new Date());
			if (null == site.getIsCamera() || !site.getIsCamera()) {
				order.setTotalPrice(DiySiteFee.GET_PARKING_PRICE(site, order.getOrderTime(), order.getFinishTime()));
			}
			if (order.getTotalPrice() > user.getBalance()) {
				/**
				 * 此处在后期修改为跳转到第三方支付的页面
				 */
				res.put("message", "余额不足，支付失败！");
				return res;
			} else {
				user.setBalance(user.getBalance() - order.getTotalPrice() + setting.getFirstPay());
				order.setStatusId(6L);
				order.setPayTypeId(28L);
				res.put("status", 0);
				res.put("message", "支付成功，您可在15分钟之内离开车库！");
			}
		}
		res.put("status", 0);
		return res;
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
	@ResponseBody
	public Map<String, Object> orderDetail(HttpServletRequest req, ModelMap map) {
		Map<String, Object> res = new HashMap<>();
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		TdSetting setting = tdSettingService.findOne(1L);
		List<TdOrder> list = tdOrderService.findByUsername(username);
		TdOrder order = null;
		if (null != list && list.size() > 0) {
			order = list.get(0);
		}
		TdDiySite site = tdDiySiteService.findOne(order.getDiyId());
		if (order.getStatusId() == 1) {
			// 判断余额是否能够支付定金
			if (user.getBalance() < setting.getFirstPay()) {
				/**
				 * 此处将在后期修改为提交到第三方支付
				 * 
				 * @author dengxiao
				 */
				res.put("message", "余额不足，支付失败！");
			} else {
				// 余额足够便扣除定金
				user.setBalance(user.getBalance() - setting.getFirstPay());
				// 设置订单状态为已支付定金
				order.setFirstPay(setting.getFirstPay());
				order.setStatusId(2L);
				// 判断停车场是否还有剩余车位
				if (site.getParkingNowNumber() > 0) {
					// 如果还有剩余的车位，就开始判断停车场是否有摄像头
					if (null != site.getIsCamera() && site.getIsCamera()) {// 有摄像头就自动预约成功
						if (!(site.getParkingNowNumber() > 0)) {
							order.setFirstPay(0.00);
							user.setBalance(user.getBalance() + setting.getFirstPay());
							order.setStatusId(9L);
							order.setCancelReason("指定停车场无剩余车位");
							tdOrderService.save(order);
							res.put("message", "抱歉，已经没有车位了，预定失败！");
							return res;
						}
						site.setParkingNowNumber(site.getParkingNowNumber() - 1);
						order.setStatusId(3L);
						res.put("message", "预约成功，请在2个小时之内到达指定的车库停车！");
						// 开始判定2小时后车辆是否进入车库
						final Long orderId = order.getId();
						final Double firstPay = setting.getFirstPay();
						final TdUser theUser = user;
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							@Override
							public void run() {
								TdOrder theOrder = tdOrderService.findOne(orderId);
								TdDiySite theSite = tdDiySiteService.findOne(theOrder.getDiyId());
								// 如果两小时之后订单的状态还是“预约成功”，则表示没有进入车库
								if (3L == theOrder.getStatusId()) {
									// 设置取消时间
									theOrder.setFinishTime(new Date());
									// 设置订单状态为交易取消
									theOrder.setStatusId(9L);
									// 设置取消订单的原因
									theOrder.setCancelReason("预约2小时后车辆未进入指定车库");
									// 判断消费了多少钱
									if (null != theSite && null != theSite.getIsCamera() && !theSite.getIsCamera()) {
										Double price = DiySiteFee.GET_PARKING_PRICE(theSite, theOrder.getReserveTime(),
												theOrder.getFinishTime());
										theOrder.setTotalPrice(price);
									}
									// 如果定金还有剩余就退还剩余部分的钱
									if (firstPay > theOrder.getTotalPrice()) {
										Double left = firstPay - theOrder.getTotalPrice();
										theUser.setBalance(theUser.getBalance() + left);
									}
									//保存已经改动的订单信息和用户信息
									tdOrderService.save(theOrder);
									tdUserService.save(theUser);
								}
							}
						}, 1000 * 60 * 60 * 2);
					} else {// 如果没有摄像头就需要等待泊车员手动确认预约
						res.put("message", "定金已支付，等待工作人员确认预约！");
					}
				} else {// 剩余车位不足即预定失败，订单结束
					order.setFirstPay(0.00);
					// 返还定金
					user.setBalance(user.getBalance() + setting.getFirstPay());
					// 设置订单状态为交易结束
					order.setStatusId(9L);
					// 设置订单取消的原因
					order.setCancelReason("指定停车场无剩余车位");

					tdOrderService.save(order);
					// 设置消息提示
					res.put("message", "抱歉，已经没有车位了，预定失败！");
					return res;
				}
			}
		} else if (order.getStatusId() == 4L) {
			order.setFinishTime(new Date());
			if (null == site.getIsCamera() || !site.getIsCamera()) {
				order.setTotalPrice(DiySiteFee.GET_PARKING_PRICE(site, order.getOrderTime(), order.getFinishTime()));
			}
			if (order.getTotalPrice() > user.getBalance()) {
				/**
				 * 此处在后期修改为跳转到第三方支付的页面
				 */
				res.put("message", "余额不足，支付失败！");
				return res;
			} else {
				user.setBalance(user.getBalance() - order.getTotalPrice() + setting.getFirstPay());
				order.setStatusId(6L);
				order.setPayTypeId(28L);
				res.put("status", 0);
				res.put("message", "支付成功，您可在15分钟之内离开车库！");
			}
		} else {
			res.put("status", 1);
			res.put("orderId", order.getId());
		}
		tdOrderService.save(order);
		res.put("status", 0);
		return res;
	}

	/**
	 * 在订单详情页取消订单的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/detailCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> detailCancel(HttpServletRequest req, Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdOrder order = tdOrderService.findOne(id);
		if (null != order) {
			order.setStatusId(9L);
			order.setFinishTime(new Date());
			order.setCancelReason("用户自主取消");
			res.put("status", 0);
			res.put("message", "订单已取消");
		} else {
			res.put("message", "未找到指定的订单");
		}
		return res;
	}

	/**
	 * 查看最新订单状态是否发生改变的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/checkOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkOrder(HttpServletRequest req, Long statusId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		TdOrder currentOrder = null;
		if (null != username) {
			List<TdOrder> list = tdOrderService.findByUsername(username);
			if (null != list && list.size() > 0) {
				if (list.get(0).getStatusId() != 6 && list.get(0).getStatusId() != 9) {
					currentOrder = list.get(0);
				}
			}
		}
		if (statusId != null && statusId != currentOrder.getStatusId()) {
			res.put("status", 0);
			res.put("message", "您的订单发生改变！");
		}
		return res;
	}

	/**
	 * 查看最新订单价格的方法
	 * @author dengxiao
	 */
	@RequestMapping(value="/price")
	@ResponseBody
	public Map<String, Object> price(HttpServletRequest req,ModelMap map){
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		String username = (String) req.getSession().getAttribute("username");
		List<TdOrder> list = tdOrderService.findByUsername(username);
		TdOrder order = null;
		if (null != list && list.size() > 0) {
			order = list.get(0);
		}
		if(null != order){
			if(null == order.getTotalPrice()){
				order.setTotalPrice(0.00);
			}
			DecimalFormat df = new DecimalFormat("######0.00");
			
			res.put("status", 0);
			res.put("price",df.format(order.getTotalPrice()) );
		}
		return res;
	}
	
}
