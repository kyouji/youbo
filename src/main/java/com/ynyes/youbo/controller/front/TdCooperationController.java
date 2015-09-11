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
	public Map<String, Object> login(String username, String password, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		//status代表处理状态，-1代表失败
		res.put("status", -1);
		if(null == username){
			System.err.println("未能正确接收到username");
			res.put("message", "username的值为null");
			return res;
		}
		if(null == password){
			System.err.println("未能正确接收到password");
			res.put("message", "password的值为null");
			return res;
		}
		TdDiySite diySite = tdDiySiteService.findByUsernameAndPasswordAndIsEnableTrue(username, password);
		System.err.println("获取到停车场的信息，进行判断");
		
		if (null != diySite) {
			System.err.println("登陆成功");
			//设置status的值为0，代表登陆成功
			res.put("status", 0);
			res.put("message", "登陆成功");
			System.err.println("将登陆的停车场信息存储到session中");
			request.getSession().setAttribute("diySite", diySite);
		}else{
			//判断何种原因导致登录失败
			System.err.println("未能查找到指定username和password的停车场，开始查找原因");
			TdDiySite tdDiySite = tdDiySiteService.findbyUsername(username);
			if(null == tdDiySite){
				res.put("message", "该用户未注册");
			}else if(!tdDiySite.getIsEnable()){
				res.put("message", "该用户已被禁用");
			}else{
				res.put("message", "密码错误");
			}
			System.err.println("结束查找原因");
		}
		return res;
	}

	/**
	 * @author dengxiao 获取车辆出入库信息的接口
	 */
	@RequestMapping(value = "/iodata")
	@ResponseBody
	public Map<String, Object> ioData(TdIOData ioData, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，-1代表失败
		res.put("status", -1);
		System.err.println("开始从session中读取停车场信息");
		TdDiySite diySite = (TdDiySite) request.getSession().getAttribute("diySite");

		System.err.println("读取停车场信息成功，开始验证");
		if (null == diySite) {
			System.err.println("没有获取到已登陆的停车场用户的信息");
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("session中的停车场信息验证通过");

		// 保存此出入库信息
		System.err.println("开始保存出入库信息");
		if(null == ioData){
			res.put("message", "未接收到进出库信息");
			return res;
		}
		ioData = tdIoDataService.save(ioData);
		System.err.println("已经保存了进出库信息");

		System.err.println("开始获取订单信息");
		// 根据车牌号码停车场id订单状态（状态为3，预约成功）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场预约成功的最后一个订单）
		TdOrder order = tdOrderService.findTopByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(ioData.getBusNo(),
				diySite.getId());
		System.err.println("订单信息已经获取");

		if ("正常进入".equals(ioData.getIoState())) {
			System.err.println("接收到车辆入库数据");
			// 如果说没有找到相对应的订单，则表示该车辆没有预约，且立即为它生成一个订单
			if (null == order) {
				System.err.println("未预约车辆进入车库，创建一个新的订单");
				TdOrder theOrder = new TdOrder();
				System.err.println("开始设置属性");
				theOrder.setDiyId(diySite.getId());
				theOrder.setDiyTitle(diySite.getTitle());
				theOrder.setOrderTime(new Date());
				System.err.println("开始存储新的订单");
				order = tdOrderService.save(theOrder);
				System.err.println("设置停车场的剩余数量-1");
				diySite.setParkingNowNumber(diySite.getParkingNowNumber() - 1);
				tdDiySiteService.save(diySite);
			}
			System.err.println("继续设置属性");
			// 设置订单的入库时间
			order.setInputTime(ioData.getIoDate());
			// 将订单的状态改变为4L（正在停车）
			order.setStatusId(4L);
			System.err.println("属性设置完毕");
			// 设置status的值为0，代表处理成功
			res.put("status", 0);
			// 设置消息提示
			res.put("message", "入库信息录入成功");
		}

		if ("正常外出".equals(ioData.getIoState())) {
			System.err.println("接收到车辆出库数据，开始设置属性");
			order.setOutputTime(ioData.getIoDate());
			// 在此计算停车费用，并将其存储到order的totalPrice字段上

			// 将计算出来的总价格返回
			res.put("totalPrice", order.getTotalPrice());
			// 将支付的定金返回
			res.put("firstPay", order.getFirstPay());
			// 将订单的ID返回
			res.put("orderId", order.getId());
			System.err.println("属性设置完毕");
			// 设置status的值为0，代表处理成功
			res.put("status", 0);
			// 设置消息提示
			res.put("message", "出库信息录入成功");
			System.err.println("设置停车场剩余数量+1");
			diySite.setParkingNowNumber(diySite.getParkingNowNumber() + 1);
			tdDiySiteService.save(diySite);
		}
		System.err.println("存储订单信息（属性设置完毕）");
		tdOrderService.save(order);
		return res;

	}

	/**
	 * @author dengxiao 获取支付信息的方法
	 */
	@RequestMapping(value = "/payInfo")
	@ResponseBody
	public Map<String, Object> isPay(Long orderId, HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		// status代表处理状态，-1代表失败
		res.put("status", -1);

		// 获取该停车场的信息
		System.err.println("开始获取session中的停车场信息");
		TdDiySite diySite = (TdDiySite) request.getSession().getAttribute("diySite");
		if(null == diySite){
			res.put("message", "停车场用户未登陆");
			return res;
		}
		System.err.println("停车场信息获取完毕");


		System.err.println("开始获取订单信息");
		if(null == orderId){
			res.put("message", "未接收到orderId");
			return res;
		}
		TdOrder order = tdOrderService.findByDiyIdAndId(diySite.getId(), orderId);
		if (null == order) {
			System.err.println("未能获取到正确的订单信息");
			res.put("message", "未获取到指定的订单信息，无法判断其是否已交付停车费用");
			return res;
		}
		System.err.println("订单信息获取成功");
		// 如果订单的状态为6（交易完成），代表已经支付了停车费用
		if (6L == order.getStatusId()) {
			System.err.println("确认该订单已经交清费用");
			res.put("status", 0);
			res.put("message", "已支付停车费用");
		}else{
			res.put("message", "未支付停车费用");
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
		// status代表处理状态，-1代表失败
		res.put("status", -1);

		System.err.println("开始验证图片是否存在");
		if (null == imgFile || imgFile.isEmpty() || null == imgFile.getName()) {
			System.err.println("图片不存在");
			res.put("message", "图片不存在");
			return res;
		}
		System.err.println("图片存在通过验证，开始获取图片名称");
		String name = imgFile.getOriginalFilename();
		System.err.println("开始获取图片后缀名");
		String ext = name.substring(name.lastIndexOf("."));

		try {
			System.err.println("将图片转换未字节数组");
			byte[] bytes = imgFile.getBytes();

			System.err.println("获取当前时间信息已生成新的名字");
			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			System.err.println("定义存储到服务器上的图片的名字");
			String fileName = sdf.format(dt) + ext;
			System.err.println("设置存储路径");
			String uri = ImageRoot + "/" + fileName;

			System.err.println("在指定的路径上生成文件");
			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			System.err.println("开始写入文件流");
			stream.write(bytes);
			stream.close();
			System.err.println("文件流关闭");
			res.put("status", 0);
		} catch (Exception e) {
			res.put("message", "产生了IO异常");
			e.printStackTrace();
		}
		return res;
	}

}
