package com.ynyes.youbo.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.math.BigDecimal;
import com.ibm.icu.text.SimpleDateFormat;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdUserService;

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

	@RequestMapping
	public String index(HttpServletRequest req, Device device, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}
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
	@RequestMapping(value = "reserve")
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
		if(null == user||null == site){
			res.put("message", "参数错误！");
			return res;
		}
		TdOrder order = new TdOrder();
		//以下代码用于生成订单编号
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(date);
		Random random = new Random();
		Integer suiji = random.nextInt(900)+100;
		String orderNum = sDate + suiji;
		//生成订单编号结束
		order.setOrderNumber(orderNum);
		order.setUsername(username);
		order.setCarCode(user.getCarCode());
		order.setOrderTime(date);
		order.setStatusId(1L);
		order.setDiyId(site.getId());
		order.setDiyTitle(site.getTitle());
		order = tdOrderService.save(order);
		res.put("message", "订单已生成！");
		res.put("status", 0);
		res.put("id", order.getId());
		return res;
	}
	
	/**
	 * 跳转到下一个页面开始导航的方法
	 * @author dengxiao
	 */
	@RequestMapping(value="/navigation")
	public String navigation(HttpServletRequest request,String x,String y,Long id,ModelMap map){
		String username = (String) request.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if(null == user){
			return "/user/login";
		}
		TdDiySite site = tdDiySiteService.findByIdAndIsEnableTrue(id);
		map.addAttribute("site", site);
		map.addAttribute("x", x);
		map.addAttribute("y",y);
		return "/user/navigation";
	}
}
