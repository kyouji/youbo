package com.ynyes.youbo.controller.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdIOData;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdDiyUserService;
import com.ynyes.youbo.service.TdIODataService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdUserService;

/**
 * @author dengxiao 提供给第三方的接口
 */
@Controller
@RequestMapping(value = "/cooper")
public class TdCooperationController {

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdIODataService tdIoDataService;

	@Autowired
	private TdDiyUserService tdDiyUserService;

	@Autowired
	private TdUserService tdUserService;

	/**
	 * @author dengxiao 1. 第三方登陆接口
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> login(String username, String password, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，-1代表失败
		res.put("status", -1);
		if (null == username) {
			System.err.println("未能正确接收到username");
			res.put("message", "username的值为null");
			return res;
		}
		if (null == password) {
			System.err.println("未能正确接收到password");
			res.put("message", "password的值为null");
			return res;
		}
		TdDiyUser diyUser = tdDiyUserService.findByUsernameAndPasswordAndIsEnableTrue(username, password);
		System.err.println("获取到停车场的信息，进行判断");

		if (null != diyUser) {
			System.err.println("登陆成功");
			// 设置status的值为0，代表登陆成功
			res.put("status", 0);
			res.put("message", "登陆成功");
			System.err.println("将登陆的停车场信息存储到session中");
			request.getSession().setAttribute("cooperDiy", diyUser);
		} else {
			// 判断何种原因导致登录失败
			System.err.println("未能查找到指定username和password的停车场，开始查找原因");
			TdDiyUser user = tdDiyUserService.findByUsername(username);
			if (null == user) {
				res.put("message", "该用户未注册");
			} else if (!user.getIsEnable()) {
				res.put("message", "该用户已被禁用");
			} else {
				res.put("message", "密码错误");
			}
			System.err.println("结束查找原因");
		}
		return res;
	}

	/**
	 * @author dengxiao 2. 计算某停车场所有正在停车的订单价格的接口
	 */
	@RequestMapping(value = "/countPrice")
	@ResponseBody
	public Map<String, Object> countPrice(HttpServletRequest req) {
		System.err.println("开始创建map");
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		System.err.println("开始获取session中的登陆信息");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		System.err.println("开始判断session中的登陆用户是否存在");
		if (null == diyUser) {
			System.err.println("用户没有登陆");
			res.put("message", "停车场用户未登陆");
			System.err.println("返回结果");
			return res;
		}
		System.err.println("查找到所有需要计算价格的订单");
		List<TdOrder> parking_list = tdOrderService.findByDiyIdAndStatusId(diyUser.getDiyId());
		System.err.println("正在停车的订单查找完毕");
		List<TdOrder> wy_list = tdOrderService
				.findByDiyIdAndStatusIdAndFirstPayGreaterThanAndTotalPrice(diyUser.getId());
		System.err.println("违约订单查找完毕");
		List<String> order_list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		for (TdOrder parking : parking_list) {
			if (null != parking.getReserveTime()) {
				order_list.add(parking.getId() + "," + parking.getCarCode() + "," + sdf.format(parking.getReserveTime())
						+ "," + sdf.format(now));
			} else {
				if (null != parking.getInputTime()) {
					order_list.add(parking.getId() + "," + parking.getCarCode() + ","
							+ sdf.format(parking.getInputTime()) + "," + sdf.format(now));
				}
			}
		}
		for (TdOrder wy : wy_list) {
			order_list.add(
					wy.getId() + "," + wy.getCarCode() + "," + sdf.format(wy.getReserveTime()) + "," + sdf.format(now));
		}
		res.put("status", 0);
		res.put("orders", order_list);
		return res;
	}

	/**
	 * @author dengxiao 3. 获取价格计算结果的接口
	 */
	@RequestMapping(value = "/getResult")
	@ResponseBody
	public Map<String, Object> getResult(HttpServletRequest req, String prices) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		if (null == prices) {
			res.put("message", "参数prices接收失败！");
			return res;
		}

		String[] infos = prices.split(",");
		for (int i = 0; i < infos.length; i++) {
			if ((i + 1) % 3 == 0) {
				TdOrder order = tdOrderService.findOne(Long.parseLong(infos[i - 2]));
				if (null != order && null != infos[i - 1] && infos[i - 1].trim().equals(order.getCarCode())) {
					order.setTotalPrice(new Double(infos[i]));
					// 如果是违约订单，则开始根据消费金额返还定金的余额
					if (9L == order.getStatusId()) {
						TdUser user = tdUserService.findByUsername(order.getUsername());
						user.setBalance(user.getBalance() + order.getFirstPay() - order.getTotalPrice());
						tdUserService.save(user);
					}
					tdOrderService.save(order);
					res.put(infos[i - 2] + "", "车牌号为" + infos[i - 1] + "，费用为" + infos[i]);
				} else {
					if (null == order) {
						res.put("message", "未找到id为" + infos[i - 2] + "的订单");
						return res;
					} else if (null == infos[i - 1]) {
						res.put("message", "未获取到id为" + infos[i - 2] + "的订单的车牌号");
						return res;
					} else if (!infos[i - 1].trim().equals(order.getCarCode())) {
						res.put("message", "id为" + infos[i - 2] + "的订单车牌号信息不符合");
						return res;
					}
				}
			}
		}

		// for (String string : prices) {
		// String[] infos = string.split(",");
		// TdOrder order = tdOrderService.findOne(Long.parseLong(infos[0]));
		//
		// if (null != order && null != infos[1] &&
		// infos[1].trim().equals(order.getCarCode())) {
		// order.setTotalPrice(new Double(infos[2]));
		// // 如果是违约订单，则开始根据消费金额返还定金的余额
		// if (9L == order.getStatusId()) {
		// TdUser user = tdUserService.findByUsername(order.getUsername());
		// user.setBalance(user.getBalance() + order.getFirstPay() -
		// order.getTotalPrice());
		// tdUserService.save(user);
		// }
		// tdOrderService.save(order);
		// res.put(infos[0] + "", "车牌号为" + infos[1] + "，费用为" + infos[2]);
		// } else {
		// if (null == order) {
		// res.put("message", "未找到id为" + infos[0] + "的订单");
		// return res;
		// } else if (null == infos[1]) {
		// res.put("message", "未获取到id为" + infos[0] + "的订单的车牌号");
		// return res;
		// } else if (!infos[1].trim().equals(order.getCarCode())) {
		// res.put("message", "id为" + infos[0] + "的订单车牌号信息不符合");
		// return res;
		// }
		// }
		// }
		res.put("status", 0);
		return res;
	}

	/**
	 * @author dengxiao 4. 获取车辆出入库信息的接口
	 */
	@RequestMapping(value = "/iodata")
	@ResponseBody
	public Map<String, Object> ioData(String carNo, String busNo, String ioState, String ioDate, String picture,
			Boolean isVip, Boolean isrepeat, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，-1代表失败
		res.put("status", -1);
		System.err.println("开始从session中读取停车场信息");
		TdDiyUser diyUser = (TdDiyUser) request.getSession().getAttribute("cooperDiy");

		System.err.println("读取停车场信息成功，开始验证");
		if (null == diyUser) {
			System.err.println("没有获取到已登陆的停车场用户的信息");
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("session中的停车场信息验证通过");

		TdDiySite diySite = tdDiySiteService.findOne(diyUser.getDiyId());

		// 保存此出入库信息
		System.err.println("开始保存出入库信息");
		if (null == carNo || null == busNo || null == ioState || null == ioDate || null == picture || null == isVip) {
			res.put("message", "参数未接收成功");
			return res;
		}
		// 设置图片路径
		String lujing = "/root/ftpimages/" + picture;

		Date theDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			theDate = sdf.parse(ioDate);
		} catch (Exception e) {
			res.put("message", "ioDate参数格式不正确");
			return res;
		}
		System.err.println("开始设置参数");
		TdIOData ioData = new TdIOData();
		ioData.setCardNo(carNo);
		ioData.setBusNo(busNo);
		ioData.setIoState(ioState);
		ioData.setIoDate(theDate);
		ioData.setPicture(lujing);
		System.err.println("参数设置完毕");
		ioData = tdIoDataService.save(ioData);
		System.err.println("已经保存了进出库信息");

		if ("正常进入".equals(ioData.getIoState())) {
			System.err.println("接收到车辆入库数据");
			System.err.println("开始获取订单信息");
			// 根据车牌号码停车场id订单状态（状态为3，预约成功）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场预约成功的最后一个订单）
			TdOrder order = tdOrderService.findTopByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(ioData.getBusNo(),
					diySite.getId());
			System.err.println("订单信息已经获取");
			// 如果说没有找到相对应的订单，则表示该车辆没有预约，且立即为它生成一个订单
			if (null == order) {
				System.err.println("未预约车辆进入车库，创建一个新的订单");
				TdOrder theOrder = new TdOrder();
				System.err.println("开始设置属性");
				theOrder.setDiyId(diySite.getId());
				theOrder.setDiyTitle(diySite.getTitle());
				theOrder.setOrderTime(theDate);
				theOrder.setReserveTime(ioData.getIoDate());
				theOrder.setCarCode(busNo);
				theOrder.setFirstPay(0.00);
				theOrder.setUsername(busNo);
				if (null == theOrder.getCarCodePhoto()) {
					theOrder.setCarCodePhoto("");
				}
				theOrder.setCarCodePhoto(theOrder.getCarCodePhoto() + lujing + ",");
				System.err.println("开始存储新的订单");
				order = tdOrderService.save(theOrder);
				System.err.println("设置停车场的剩余数量-1");
				diySite.setParkingNowNumber(diySite.getParkingNowNumber() - 1);
				tdDiySiteService.save(diySite);
			}
			System.err.println("继续设置属性");
			// 是否为月卡用户
			if (isVip) {
				order.setThePayType(3L);
			}
			// 设置订单的入库时间
			order.setInputTime(ioData.getIoDate());
			// 将订单的状态改变为4L（正在停车）
			order.setStatusId(4L);
			tdOrderService.save(order);
			System.err.println("属性设置完毕");
			// 设置status的值为0，代表处理成功
			res.put("status", 0);
			// 设置消息提示
			res.put("message", "入库信息录入成功");
			tdOrderService.save(order);
		}

		if ("正常外出".equals(ioData.getIoState())) {
			// 根据车牌号码停车场id订单状态（状态为6，交易完成）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场停车缴费成功的最后一个订单）
			TdOrder order = tdOrderService
					.findTopByCarCodeAndDiyIdAndOrderFinishOrderByOrderTimeDescOr(ioData.getBusNo(), diySite.getId());
			System.err.println("订单信息已经获取");
			if (null != order) {
				if (isVip) {
					if (4L == order.getStatusId()) {
						order.setFinishTime(ioData.getIoDate());
						order.setStatusId(6L);
						order.setThePayType(3L);
					}
				}
				if (4L == order.getStatusId()) {
					TdUser user = tdUserService.findByUsername(busNo);
					if(user!=null){
						if(null != order.getTotalPrice()&&user.getBalance() > order.getTotalPrice()){
							user.setBalance(user.getBalance() - order.getTotalPrice() + order.getFirstPay());
							order.setFinishTime(ioData.getIoDate());
							order.setThePayType(0L);
						}else{
							res.put("message", "账户余额不足，不能完成交易");
							return res;
						}
					}else{
						res.put("message", "该车辆未注册");
						return res;
					}	
				}
				tdOrderService.save(order);
			} else {
				res.put("message", "未找到指定订单！");
				return res;
			}
			diySite.setParkingNowNumber(diySite.getParkingNowNumber() + 1);
			tdDiySiteService.save(diySite);
			order.setOutputTime(ioData.getIoDate());
			tdOrderService.save(order);
		}
		System.err.println("存储订单信息（属性设置完毕）");
		res.put("status", 0);
		return res;

	}

	/**
	 * @author dengxiao 5. 获取已支付但未出库订单的方法
	 */
	@RequestMapping(value = "/payInfo")
	@ResponseBody
	public Map<String, Object> isPay(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，-1代表失败
		res.put("status", -1);

		// 获取该停车场的信息
		System.err.println("开始获取session中的停车场信息");
		TdDiyUser diyUser = (TdDiyUser) request.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("停车场信息获取完毕");

		// 根据停车场ID，状态（6L——交易完成，已缴费），出库时间（为NULL）的所有订单
		List<TdOrder> payed_orders = tdOrderService.findByDiyIdAndStatusIdAndOutputAndIsOverTime(diyUser.getDiyId());
		List<String> carCodes = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (TdOrder tdOrder : payed_orders) {
			carCodes.add(tdOrder.getId() + "," + tdOrder.getCarCode() + "," + sdf.format(tdOrder.getFinishTime()));
		}
		res.put("carCodes", carCodes);
		res.put("status", 0);
		return res;
	}

	/**
	 * @author dengxiao 6. 获取在中央缴费区缴费的车辆的信息
	 */
	@RequestMapping(value = "/payed/order")
	@ResponseBody
	public Map<String, Object> getPayedOrder(HttpServletRequest req, String carCode, String finishDate,
			Double totalPrice, Long type) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		System.err.println("开始获取session中的停车场信息");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("停车场信息获取完毕");
		// 根据车牌号码和停车场ID查找指定车辆的订单
		List<TdOrder> orders = tdOrderService.findByDiyIdAndStatusIdAndCarCode(diyUser.getDiyId(), carCode);

		if (null == orders) {
			res.put("message", "未能获取到该车牌号码的订单");
			return res;
		}

		if (orders.size() != 1) {
			res.put("message", "数据错误，未能找到指定车牌号码的订单");
			return res;
		}

		if (null == type) {
			res.put("message", "未获取到支付方式（type）");
			return res;
		}

		if (!(1L == type || 2L == type)) {
			res.put("message", "参数type（支付方式）的值不正确");
			return res;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date finishTime = null;
		try {
			finishTime = sdf.parse(finishDate);
		} catch (ParseException e) {
			e.printStackTrace();
			res.put("message", "finishDate参数错误！");
			return res;
		}
		TdOrder order = orders.get(0);
		order.setFinishTime(finishTime);
		order.setTotalPrice(totalPrice);
		order.setStatusId(6L);
		order.setThePayType(type);
		TdUser user = tdUserService.findByUsername(carCode);
		if (null != user) {
			user.setBalance(user.getBalance() + order.getFirstPay() - order.getTotalPrice());
			tdUserService.save(user);
		}
		tdOrderService.save(order);
		res.put("status", 0);
		return res;
	}

	/**
	 * @author dengxiao 7. 缴费车辆未在15分钟以内出库，生成了新的订单
	 */
	@RequestMapping(value = "/newOrder")
	@ResponseBody
	public Map<String, Object> newOrder(HttpServletRequest req, String carCode, String orderDate, Double totalPrice) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		System.err.println("开始获取session中的停车场信息");
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("停车场信息获取完毕");

		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());

		if (null == orderDate) {
			res.put("message", "未获取到orderDate");
			return res;
		}

		Date orderTime = null;
		try {
			orderTime = sdf.parse(orderDate);
		} catch (ParseException e) {
			e.printStackTrace();
			res.put("message", "时间参数错误！");
			return res;
		}
		/**
		 * 把超时的订单设置出库
		 */
		List<TdOrder> orders = tdOrderService
				.findByDiyIdAndStatusIdAndCarCodeAndOutputTimeIsNullOrderByOrderTimeDesc(diyUser.getDiyId(), carCode);
		if (null != orders && orders.size() > 0) {
			orders.get(0).setOutputTime(new Date());
			orders.get(0).setIsOvertime(true);
			tdOrderService.save(orders.get(0));
		} else {
			res.put("message", "未找到指定车辆超时的订单！");
			return res;
		}

		// 以下代码用于生成订单编号
		Date date = new Date();
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = sDate + suiji;
		// 生成订单编号结束
		TdOrder order = new TdOrder();
		order.setOrderNumber(orderNum);
		order.setCarCode(carCode);
		order.setUsername(carCode);
		order.setDiyTitle(site.getTitle());
		order.setDiyId(site.getId());
		order.setFirstPay(0.00);
		order.setOrderTime(orderTime);
		order.setTotalPrice(totalPrice);
		order.setRemarkInfo("缴费后15分钟以内未离开车库而产生了新的订单");
		order.setUsername(carCode);
		tdOrderService.save(order);
		/**
		 * 在此应该给客户发送短信提示
		 */
		tdOrderService.save(order);

		res.put("message", "信息录入成功！");
		res.put("status", 0);
		return res;

	}

	/**
	 * @author dengxiao 8. 在新华软件修改价格之后传递到优泊B端
	 */
	@RequestMapping(value = "/setPrice")
	@ResponseBody
	public Map<String, Object> setPrice(HttpServletRequest req, Long dayType, Long nightType, Long dayBaseTime,
			Long nightBaseTime, Double dayBasePrice, Double nightBasePrice, Double dayHourPrice, Double nightHourPrice,
			Double dayOncePrice, Double nightOncePrice, Double maxPrice) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}

		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());

		if (null == maxPrice) {
			res.put("message", "未能获取到每日价格上限（maxPrice）");
			return res;
		}

		site.setMaxPrice(maxPrice);

		if (null == dayType) {
			res.put("message", "未能获取到白天收费类型（dayType）");
			return res;
		}

		if (null == nightType) {
			res.put("message", "未能获取到夜间收费类型（nightType）");
			return res;
		}

		if (0 == dayType) {
			if (null == dayBaseTime) {
				res.put("message", "未能获取到白天收费基础时间（dayBaseTime）");
				return res;
			}

			if (null == dayBasePrice) {
				res.put("message", "未能获取到白天收费基础价格（dayBasePrice）");
				return res;
			}

			if (null == dayHourPrice) {
				res.put("message", "未能获取到白天收费后续单价（dayHourPrice）");
				return res;
			}
			site.setDayType(dayType);
			site.setDayBaseTime(dayBaseTime);
			site.setDayBasePrice(dayBasePrice);
			site.setDayHourPrice(dayHourPrice);
		} else {
			if (null == dayOncePrice) {
				res.put("message", "未能获取到白天计次收费的价格（dayOncePrice）");
				return res;
			}
			site.setDayType(dayType);
			site.setDayOncePrice(dayOncePrice);
		}

		if (0 == nightType) {
			if (null == nightBaseTime) {
				res.put("message", "未能获取到夜间收费基础时间（nightBaseTime）");
				return res;
			}

			if (null == nightBasePrice) {
				res.put("message", "未能获取到夜间收费基础价格（nightBasePrice）");
				return res;
			}

			if (null == nightHourPrice) {
				res.put("message", "未能获取到夜间收费后续单价（nightHourPrice）");
				return res;
			}

			site.setNightType(nightType);
			site.setNightBaseTime(nightBaseTime);
			site.setNightBasePrice(nightBasePrice);
			site.setNightHourPrice(nightHourPrice);
		} else {
			if (null == nightOncePrice) {
				res.put("message", "未能获取到夜间计次收费的价格（nightOncePrice）");
				return res;
			}
			site.setNightType(nightType);
			site.setNightOncePrice(nightOncePrice);
		}
		tdDiySiteService.save(site);
		res.put("status", 0);
		return res;
	}

	/**
	 * @author dengxiao 9. 新华软件获取B端修改的价格
	 */
	@RequestMapping(value = "/getPrice")
	@ResponseBody
	public Map<String, Object> getPrice(HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);

		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}

		TdDiySite site = tdDiySiteService.findOne(diyUser.getDiyId());
		if (0 == site.getDayType()) {
			res.put("dayType", site.getDayType());
			res.put("dayBaseTime", site.getDayBaseTime());
			res.put("dayBasePrice", site.getDayBasePrice());
			res.put("dayHourPrice", site.getDayHourPrice());
		} else {
			res.put("dayType", site.getDayType());
			res.put("dayOncePrice", site.getDayOncePrice());
		}

		if (0 == site.getNightType()) {
			res.put("nightType", site.getNightType());
			res.put("nightBaseTime", site.getNightBaseTime());
			res.put("nightBasePrice", site.getNightBasePrice());
			res.put("nightHourPrice", site.getNightHourPrice());
		} else {
			res.put("nightType", site.getNightType());
			res.put("nightOncePrice", site.getNightOncePrice());
		}
		res.put("maxPrice", site.getMaxPrice());
		res.put("leftNum", site.getParkingNowNumber());
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping(value = "/getTime")
	@ResponseBody
	public Map<String, Object> getTime(HttpServletRequest req){
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String now = sdf.format(time);
		res.put("time", now);
		return res;
	}
	
	@RequestMapping(value = "/settlement")
	@ResponseBody
	public Map<String, Object> settlement(HttpServletRequest req,String busNo){
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		TdDiyUser diyUser = (TdDiyUser) req.getSession().getAttribute("cooperDiy");
		if (null == diyUser) {
			res.put("message", "停车场用户未登陆");
			return res;
		}
		TdOrder order = tdOrderService
				.findTopByCarCodeAndDiyIdAndOrderFinishOrderByOrderTimeDescOr(busNo, diyUser.getDiyId());
		TdUser user = tdUserService.findByUsername(busNo);
		if(user!=null){
			if(null != order.getTotalPrice()&&user.getBalance() > order.getTotalPrice()){
				if(null == order.getFirstPay()){
					order.setFirstPay(0.00);
				}
				user.setBalance(user.getBalance() - order.getTotalPrice() + order.getFirstPay());
				order.setThePayType(0L);
				order.setFinishTime(new Date());
				order.setRemarkInfo("离开车库时自动支付");
			}else{
				res.put("message", "账户余额不足，不能完成交易");
				return res;
			}
		}else{
			res.put("message", "该车辆未注册");
			return res;
		}
		res.put("status", 0);
		return res;
	}
}
