package com.ynyes.youbo.controller.front;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdIOData;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdIODataService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.util.SiteMagConstant;

/**
 * @author dengxiao 提供给第三方的接口
 */
@Controller
@RequestMapping(value = "/cooper")
public class TdCooperationController {

	private final String ImageRoot = SiteMagConstant.imagePath;

	@Autowired
	private TdDiySiteService tdDiySiteService;

	@Autowired
	private TdOrderService tdOrderService;

	@Autowired
	private TdIODataService tdIoDataService;

	/**
	 * @author dengxiao 第三方登陆接口
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Boolean login(String username, String password, HttpServletRequest request) {
		// 声明一个变量result用于代表登陆结果，其初始值为"no"，表示登陆失败
		Boolean result = new Boolean(true);
		TdDiySite diySite = tdDiySiteService.findByUsernameAndPasswordAndIsEnableTrue(username, password);
		if (null != diySite) {
			result = false;
			request.getSession().setAttribute("diySite", diySite);
		}
		return result;
	}

	/**
	 * @author dengxiao 获取车辆出入库信息的接口
	 */
	@RequestMapping(value = "/iodata")
	@ResponseBody
	public Map<String, Object> ioData(TdIOData ioData, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，2代表失败
		res.put("status", 2);
		TdDiySite diySite = (TdDiySite) request.getSession().getAttribute("diySite");

		// 保存此出入库信息
		ioData = tdIoDataService.save(ioData);

		if (null == diySite) {
			res.put("message", "未获取到登陆信息");
			return res;
		}
		// 根据车牌号码停车场id订单状态（状态为3，预约成功）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场预约成功的最后一个订单）
		TdOrder order = tdOrderService.findTopByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(ioData.getBusNo(),
				diySite.getId());

		if ("正常进入".equals(ioData.getIoState())) {
			// 如果说没有找到相对应的订单，则表示该车辆没有预约，且立即为它生成一个订单
			if (null == order) {
				TdOrder theOrder = new TdOrder();
				theOrder.setDiyId(diySite.getId());
				theOrder.setDiyTitle(diySite.getTitle());
				theOrder.setOrderTime(new Date());
				order = tdOrderService.save(theOrder);
			}
			// 设置订单的入库时间
			order.setInputTime(ioData.getIoDate());
			// 将订单的状态改变为4L（正在停车）
			order.setStatusId(4L);
			// 设置status的值为1，代表处理成功
			res.put("status", 1);
			// 设置消息提示
			res.put("message", "入库信息录入成功");
		}

		if ("正常外出".equals(ioData.getIoState())) {
			order.setOutputTime(ioData.getIoDate());
			// 在此计算停车费用，并将其存储到order的totalPrice字段上

			// 将计算出来的总价格返回
			res.put("totalPrice", order.getTotalPrice());
			// 将支付的定金返回
			res.put("firstPay", order.getFirstPay());
			// 将订单的ID返回
			res.put("orderId", order.getId());
			// 设置status的值为1，代表处理成功
			res.put("status", 1);
			// 设置消息提示
			res.put("message", "出库信息录入成功");
		}
		return res;

	}

	/**
	 * @author dengxiao 获取支付信息的方法
	 */
	@RequestMapping(value = "/payInfo")
	@ResponseBody
	public Map<String, Object> isPay(Long orderId, HttpServletRequest request) {
		// 获取该停车场的信息
		TdDiySite diySite = (TdDiySite) request.getSession().getAttribute("diySite");
		Map<String, Object> res = new HashMap<>();

		// status代表处理状态，2代表失败
		res.put("status", 2);

		TdOrder order = tdOrderService.findByDiyIdAndId(diySite.getId(), orderId);
		if (null == order) {
			res.put("message", "未获取到订单信息");
			return res;
		}

		// 如果订单的状态为6（交易完成），代表已经支付了停车费用
		if (6L == order.getStatusId()) {
			res.put("status", 1);
			res.put("message", "已支付停车费用");
		}
		return res;
	}

	/**
	 * @author dengxiao 上传图片的接口
	 */
	@RequestMapping(value = " /upImg")
	@ResponseBody
	public Map<String, Object> uploadImg(@RequestParam MultipartFile imgFile) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，2代表失败
		res.put("status", 2);

		if (null == imgFile || imgFile.isEmpty() || null == imgFile.getName()) {
			res.put("message", "图片不存在");
			return res;
		}

		String name = imgFile.getOriginalFilename();
		String ext = name.substring(name.lastIndexOf("."));

		try {
			byte[] bytes = imgFile.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = ImageRoot + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
