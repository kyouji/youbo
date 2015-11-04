package com.ynyes.youbo.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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

import com.ibm.icu.text.SimpleDateFormat;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdSetting;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdSettingService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.DiySiteFee;

@Controller
@RequestMapping("/user/find")
public class TdUserFindController {
	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdAdTypeService tdAdTypeService;

	@Autowired
	private TdAdService tdAdService;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdSettingService tdSettingService;

	@RequestMapping
	public String index(HttpServletRequest req, Boolean isOrder, Device device, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
		if (null != isOrder && true == isOrder) {
			TdOrder order = (TdOrder) req.getSession().getAttribute("currentOrder");
			TdDiySite diySite = tdDiySiteService.findOne(order.getDiyId());
			String href = "redirect:/user/find/navigation?x=" + diySite.getLongitude() + "&y=" + diySite.getLatitude();
			return href;
		}
		TdUser user = tdUserService.findByUsername(username);
		if (null != user.getBankcardList() && user.getBankcardList().size() > 0) {
			map.addAttribute("haveBankCard", 0);
		}
		TdSetting setting = tdSettingService.findOne(1L);
		map.addAttribute("firstPay", setting.getFirstPay());
		map.addAttribute("user", user);
		map.addAttribute("depot_list", tdDiySiteService.findByIsEnableTrue());
		return "/user/find";
	}

	/**
	 * 预约
	 * 
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping("/bespeak")
	public String bespeak(HttpServletRequest req, ModelMap map, Long depotId) {
		String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);

		if (null == user) {
			return "/user/login";
		}
		if (null == user.getCarCode() || "".equalsIgnoreCase(user.getCarCode())) {
			// 在此回到添加车牌页面
		}

		TdSetting setting = tdSettingService.findOne(1L);
		map.addAttribute("firstPay", setting.getFirstPay());
		map.addAttribute("date", new Date());
		map.addAttribute("depot", tdDiySiteService.findByIdAndIsEnableTrue(depotId));
		map.addAttribute("user", user);
		return "/user/bespeak";
	}

	/**
	 * 查看停车场详情
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/diy/detail")
	public String diyDetail(HttpServletRequest request, Long id, ModelMap map) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "/user/login";
		}
		TdDiySite diySite = tdDiySiteService.findByIdAndIsEnableTrue(id);
		map.addAttribute("diySite", diySite);
		map.addAttribute("username", username);
		return "/user/park";
	}

	/**
	 * 预定车位的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reserve(String username, Long diyId) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		if (null == username || null == diyId) {
			res.put("message", "参数错误！");
			return res;
		}
		TdUser user = tdUserService.findByUsername(username);
		TdDiySite site = tdDiySiteService.findByIdAndIsEnableTrue(diyId);
		if (null == user || null == site) {
			res.put("message", "参数错误！");
			return res;
		}
		TdSetting setting = tdSettingService.findOne(1L);
		TdOrder order = new TdOrder();
		// 以下代码用于生成订单编号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = sDate + suiji;
		// 生成订单编号结束
		order.setOrderNumber(orderNum);
		order.setUsername(username);
		order.setCarCode(user.getCarCode());
		order.setOrderTime(date);
		order.setStatusId(1L);
		order.setDiyId(site.getId());
		order.setDiyTitle(site.getTitle());
		// 订单生成完毕

		// 判断余额是否能够支付定金
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
						tdUserService.save(user);
						return res;
					}
					site.setParkingNowNumber(site.getParkingNowNumber() - 1);
					order.setStatusId(3L);
					order.setReserveTime(new Date());
					tdOrderService.save(order);
					tdDiySiteService.save(site);
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
								long reserve = theOrder.getReserveTime().getTime();
								// 设置取消时间
								theOrder.setFinishTime(new Date(reserve + (1000 * 60 * 60 * 2)));
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
								// 保存已经改动的订单信息和用户信息
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

		user = tdUserService.save(user);
		site = tdDiySiteService.save(site);
		order = tdOrderService.save(order);
		res.put("status", 0);
		res.put("id", order.getId());

		return res;

	}

	/**
	 * 跳转到下一个页面开始导航的方法
	 * 
	 * @author dengxiao
	 */
	@RequestMapping(value = "/navigation")
	public String navigation(HttpServletRequest request, String x, String y, Long id, ModelMap map, Double lat,
			Double lng) {
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "/user/login";
		}
		TdDiySite site = tdDiySiteService.findByIdAndIsEnableTrue(id);
		map.addAttribute("site", site);
		map.addAttribute("x", x);
		map.addAttribute("y", y);
		map.addAttribute("lng", lng);
		map.addAttribute("lat", lat);
		return "/user/navigation";
	}
}
