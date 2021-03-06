package com.ynyes.youbo.controller.management;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.neo4j.cypher.internal.compiler.v2_1.docbuilders.internalDocBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdDeliveryType;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.entity.TdGoods;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdOrderGoods;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.entity.TdUserPoint;
import com.ynyes.youbo.service.TdArticleService;
import com.ynyes.youbo.service.TdDeliveryTypeService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdDiyUserService;
import com.ynyes.youbo.service.TdGoodsService;
import com.ynyes.youbo.service.TdManagerLogService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdProductCategoryService;
import com.ynyes.youbo.service.TdSiteService;
import com.ynyes.youbo.service.TdUserPointService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.SMSUtil;
import com.ynyes.youbo.util.SiteMagConstant;

/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/order")
public class TdManagerOrderController {

	@Autowired
	TdProductCategoryService tdProductCategoryService;

	@Autowired
	TdArticleService tdArticleService;

	@Autowired
	TdGoodsService tdGoodsService;

	@Autowired
	TdPayTypeService tdPayTypeService;

	@Autowired
	TdDeliveryTypeService tdDeliveryTypeService;

	@Autowired
	TdDiySiteService tdDiySiteService;

	@Autowired
	TdUserPointService tdUserPointService;

	@Autowired
	TdOrderService tdOrderService;

	@Autowired
	TdUserService tdUserService;

	@Autowired
	TdManagerLogService tdManagerLogService;

	@Autowired
	TdDiyUserService tdDiyUserService;

	// 订单设置
	@RequestMapping(value = "/setting/{type}/list")
	public String setting(@PathVariable String type, Integer page, Integer size, String keywords, String __EVENTTARGET,
			String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId, Long[] listSortId,
			ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != __EVENTTARGET) {
			if (__EVENTTARGET.equalsIgnoreCase("btnDelete")) {
				btnDelete(type, listId, listChkId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("delete", "删除支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("delete", "删除配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("delete", "删除自提点", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnSave")) {
				btnSave(type, listId, listSortId);

				if (type.equalsIgnoreCase("pay")) {
					tdManagerLogService.addLog("edit", "修改支付方式", req);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdManagerLogService.addLog("edit", "修改配送方式", req);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdManagerLogService.addLog("edit", "修改自提点", req);
				}
			} else if (__EVENTTARGET.equalsIgnoreCase("btnPage")) {
				if (null != __EVENTARGUMENT) {
					page = Integer.parseInt(__EVENTARGUMENT);
				}
			}
		}

		if (null == page || page < 0) {
			page = 0;
		}

		if (null == size || size <= 0) {
			size = SiteMagConstant.pageSize;
			;
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		map.addAttribute("keywords", keywords);
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
		map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null == keywords) {
					map.addAttribute("pay_type_page", tdPayTypeService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("pay_type_page", tdPayTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/pay_type_list";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				if (null == keywords) {
					map.addAttribute("delivery_type_page", tdDeliveryTypeService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("delivery_type_page",
							tdDeliveryTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/delivery_type_list";
			} else if (type.equalsIgnoreCase("diysite")) // 配送方式
			{
				if (null == keywords) {
					map.addAttribute("diy_site_page", tdDiySiteService.findAllOrderBySortIdAsc(page, size));
				} else {
					map.addAttribute("diy_site_page", tdDiySiteService.searchAllOrderBySortIdAsc(keywords, page, size));
				}

				return "/site_mag/diy_site_list";
			}
		}

		return "/site_mag/pay_type_list";
	}

	// 订单设置编辑
	@RequestMapping(value = "/setting/{type}/edit")
	public String edit(@PathVariable String type, Long id, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);

		if (null != type) {
			if (type.equalsIgnoreCase("pay")) // 支付方式
			{
				if (null != id) {
					map.addAttribute("pay_type", tdPayTypeService.findOne(id));
				}

				return "/site_mag/pay_type_edit";
			} else if (type.equalsIgnoreCase("delivery")) // 配送方式
			{
				if (null != id) {
					map.addAttribute("delivery_type", tdDeliveryTypeService.findOne(id));
				}

				return "/site_mag/delivery_type_edit";
			} else if (type.equalsIgnoreCase("diysite")) // 自提点
			{
				if (null != id) {
					map.addAttribute("diy_site", tdDiySiteService.findOne(id));
				}

				return "/site_mag/diy_site_edit";
			}
		}

		return "/site_mag/pay_type_edit";
	}

	// 订单设置编辑
	@RequestMapping(value = "/setting/diysite/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(String param, Long id) {
		Map<String, String> res = new HashMap<String, String>();

		res.put("status", "n");

		if (null == param || param.isEmpty()) {
			res.put("info", "该字段不能为空");
			return res;
		}

		if (null == id) {
			TdDiySite site = tdDiySiteService.findbyUsername(param);
			if (null != site) {
				res.put("info", "该登录名不能使用");
				return res;
			}
		}

		if (null != id) {
			TdDiySite site = tdDiySiteService.findOne(id);
			if (!param.equals(site.getUsername())) {
				res.put("info", "不能修改登录名");
				return res;
			}
		}

		res.put("status", "y");

		return res;
	}
	// }
	// TdUser tdUser = tdUserService.findByUsername(param);
	//
	// if (null == id) // 新增
	// {
	// if (null != tdUser) {
	// res.put("info", "该登录名不能使用");
	// return res;
	// }
	// } else // 修改，查找除当前ID的所有
	// {
	// TdDiySite dSite = tdDiySiteService.findOne(id);
	//
	// if (null == dSite) {
	// if (null != tdUser && tdUser.getRoleId() == 2L) {
	// res.put("info", "该登录名不能使用");
	// return res;
	// }
	// } else {
	// if (null != tdUser && tdUser.getUsername() != dSite.getUsername() &&
	// tdUser.getRoleId() != 2L) {
	// res.put("info", "该登录名不能使用");
	// return res;
	// }
	// }
	// }

	@RequestMapping(value = "/edit")
	public String orderEdit(Long id, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);
		if (null != id) {
			TdOrder order = tdOrderService.findOne(id);
			map.addAttribute("order", order);
			TdDiySite diySite = tdDiySiteService.findByIdAndIsEnableTrue(order.getDiyId());
			map.addAttribute("diySite", diySite);
		}
		return "/site_mag/order_edit";
	}

	@RequestMapping(value = "/save")
	public String orderEdit(TdOrder tdOrder, Long statusId, String __VIEWSTATE, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		map.addAttribute("statusId", statusId);

		tdOrderService.save(tdOrder);

		tdManagerLogService.addLog("edit", "修改订单", req);

		return "redirect:/Verwalter/order/list/" + statusId;
	}

	// 订单列表
	@RequestMapping(value = "/list/{statusId}")
	public String goodsListDialog(String keywords, @PathVariable Long statusId, Integer page, Integer size,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId,
			ModelMap map,
			// String dateId,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null == page) {
			page = 0;
		}
		if (null == size) {
			size = SiteMagConstant.pageSize;
		}

		if (0L == statusId) {
			if (null == keywords || "".equals(keywords)) {
				Page<TdOrder> order_page = tdOrderService.findAll(page, size);
				map.addAttribute("order_page", order_page);
			} else {
				Page<TdOrder> order_page = tdOrderService
						.findByOrderNumberContainingOrUsernameContainingOrDiyTitleContainingOrderByOrderTimeDesc(
								keywords, page, size);
				map.addAttribute("order_page", order_page);
			}
		} else {
			if (null == keywords || "".equals(keywords)) {
				Page<TdOrder> order_page = tdOrderService.findByStatusId(statusId, page, size);
				map.addAttribute("order_page", order_page);
			} else {
				Page<TdOrder> order_page = tdOrderService
						.findByStatusIdAndOrderNumberContainingOrStatusIdAndUsernameContainingOrStatusIdAndDiyTitleOrderByOrderTimeDesc(
								statusId, keywords, page, size);
				map.addAttribute("order_page", order_page);
			}
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/site_mag/order_list";
	}

	@RequestMapping(value = "/switch/{statusId}", method = RequestMethod.POST)
	public String switchOrder(String keywords, @PathVariable Long statusId, Integer page, Integer size,
			String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE, Long[] listId, Integer[] listChkId,
			ModelMap map,
			// String dateId,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		if (null == page) {
			page = 0;
		}
		if (null == size) {
			size = SiteMagConstant.pageSize;
		}

		if (0L == statusId) {
			if (null == keywords || "".equals(keywords)) {
				Page<TdOrder> order_page = tdOrderService.findAll(page, size);
				map.addAttribute("order_page", order_page);
			} else {
				Page<TdOrder> order_page = tdOrderService
						.findByOrderNumberContainingOrUsernameContainingOrDiyTitleContainingOrderByOrderTimeDesc(
								keywords, page, size);
				map.addAttribute("order_page", order_page);
			}
		} else {
			if (null == keywords || "".equals(keywords)) {
				Page<TdOrder> order_page = tdOrderService.findByStatusId(statusId, page, size);
				map.addAttribute("order_page", order_page);
			} else {
				Page<TdOrder> order_page = tdOrderService
						.findByStatusIdAndOrderNumberContainingOrStatusIdAndUsernameContainingOrStatusIdAndDiyTitleOrderByOrderTimeDesc(
								statusId, keywords, page, size);
				map.addAttribute("order_page", order_page);
			}
		}

		map.addAttribute("page", page);
		map.addAttribute("size", size);
		return "/site_mag/order_list";
	}

	/**
	 * @author dengxiao 在后台订单详情页修改订单状态的方法
	 */
	@RequestMapping(value = "/change/{orderId}")
	public String changeOrderStatus(@PathVariable Long orderId, String event, String type, String reason,
			HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		TdOrder order = tdOrderService.findOne(orderId);
		if ("edit".equalsIgnoreCase(type)) {
			if (7 == order.getStatusId()) {
				order.setStatusId(9L);
				order.setCheckStatus("审核通过");
				order.setRemarkInfo(reason);
				order.setAbolishReason("用户申请退款，且通过管理员审核");
				order.setFinishTime(new Date());
			} else {
				if (1 == order.getStatusId()) {
					// 在此设置此订单的预约金（开发期间固定为10元）——by dengxiao（临时预约金）
					order.setFirstPay(new Double(10));
				}
				if (4 == order.getStatusId()) {
					// 在此设置总共的停车费用（开发期间固定为50元）——by dengxiao（零时停车费用）
					order.setTotalPrice(new Double(50));
				}
				if (5 == order.getStatusId()) {
					// 在此设置订单完成时间——by dengxiao
					order.setFinishTime(new Date());
				}
				order.setStatusId(order.getStatusId() + 1L);
			}
		}
		if ("cancel".equalsIgnoreCase(type)) {
			if (7 == order.getStatusId()) {
				order.setStatusId(order.getStatusId() + 1L);
				order.setRemarkInfo(reason);
				order.setCheckStatus("审核未通过");
			} else if (1 == order.getStatusId()) {
				order.setStatusId(9L);
				order.setFinishTime(new Date());
				order.setAbolishReason(reason);
			} else if (8 == order.getStatusId()) {
				order.setStatusId(9L);
				order.setFinishTime(new Date());
				order.setAbolishReason(reason);
			}
		}
		tdOrderService.save(order);
		String operation = "将" + orderId + "号订单的状态修改为" + event;
		tdManagerLogService.addLog("edit", operation, req);
		return "redirect:/Verwalter/order/list/0";
	}

	/**
	 * @author lc
	 * @注释：将page中的订单数据存入excel表格中
	 */
	@SuppressWarnings("deprecation")
	public boolean ImportData(Page<TdOrder> tdOrderPage, HSSFRow row, HSSFCell cell, HSSFSheet sheet, int timeid) {

		for (int i = 0; i < tdOrderPage.getContent().size(); i++) {

			row = sheet.createRow((int) i + 1);
			TdOrder tdOrder = tdOrderPage.getContent().get(i);
			// 获取用户信息
			TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());
			// 第四步，创建单元格，并设置值
			row.createCell((short) 0).setCellValue(tdOrder.getUsername());
			if (null != tdUser) {
				row.createCell((short) 1).setCellValue(tdUser.getUserLevelTitle());
			}
			row.createCell((short) 2).setCellValue(tdOrder.getOrderNumber());
			if (null != tdOrder.getShopTitle()) {
				row.createCell((short) 3).setCellValue(tdOrder.getShopTitle());
			}
			if (null != tdOrder.getOrderTime()) {
				cell = row.createCell((short) 4);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(tdOrder.getOrderTime()));
			}
			if (null != tdOrder.getPayTime()) {
				cell = row.createCell((short) 5);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(tdOrder.getPayTime()));
			}
			if (null != tdOrder.getAppointmentTime()) {
				cell = row.createCell((short) 6);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(tdOrder.getAppointmentTime()));
			}
			if (null != tdOrder.getServiceTime()) {
				cell = row.createCell((short) 7);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(tdOrder.getServiceTime()));
			}
			if (null != tdOrder.getTotalPrice()) {
				row.createCell((short) 8).setCellValue(tdOrder.getTotalPrice());
			}
			if (null != tdOrder.getPayTypeTitle()) {
				row.createCell((short) 9).setCellValue(tdOrder.getPayTypeTitle());
			}
			if (tdOrder.getStatusId().equals(2L)) {
				row.createCell((short) 10).setCellValue("待付款");
			} else if (tdOrder.getStatusId().equals(3L)) {
				row.createCell((short) 10).setCellValue("待付尾款");
			} else if (tdOrder.getStatusId().equals(4L)) {
				row.createCell((short) 10).setCellValue("待服务");
			} else if (tdOrder.getStatusId().equals(5L)) {
				row.createCell((short) 10).setCellValue("待评价 ");
			} else if (tdOrder.getStatusId().equals(6L)) {
				row.createCell((short) 10).setCellValue("已完成");
			} else if (tdOrder.getStatusId().equals(7L)) {
				row.createCell((short) 10).setCellValue("已取消");
			} else if (tdOrder.getStatusId().equals(8L)) {
				row.createCell((short) 10).setCellValue("支付取消(失败)");
			} else if (tdOrder.getStatusId().equals(9L)) {
				row.createCell((short) 10).setCellValue("已删除");
			}
			if (null != tdOrder.getCouponTitle()) {
				row.createCell((short) 11).setCellValue(tdOrder.getCouponTitle());
			}
			if (null != tdOrder.getPointUse()) {
				row.createCell((short) 12).setCellValue(tdOrder.getPointUse());
			}
			if (null != tdOrder.getPoints()) {
				row.createCell((short) 13).setCellValue(tdOrder.getPoints());
			}
			row.createCell((short) 14).setCellValue("");
			if (null != tdOrder.getIsReturn()) {
				if (tdOrder.getIsReturn()) {
					row.createCell((short) 15).setCellValue("已申请售后");
				} else {
					row.createCell((short) 15).setCellValue("无");
				}
			}
			row.createCell((short) 16).setCellValue("");

			Double totalprice = 0.0;
			for (TdOrderGoods tdOrderGoods : tdOrder.getOrderGoodsList()) {
				TdGoods tdGoods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
				if (null != tdGoods && null != tdGoods.getCostPrice()) {
					totalprice += tdGoods.getCostPrice();
				}
			}
			row.createCell((short) 17).setCellValue(totalprice); // 成本价
			if (null != tdOrder.getTotalGoodsPrice()) {
				row.createCell((short) 18).setCellValue(tdOrder.getTotalGoodsPrice());
			}
			if (null != tdOrder.getTrainService()) {
				row.createCell((short) 19).setCellValue(tdOrder.getTrainService());
			}
			if (null != tdOrder.getOrderIncome()) {
				row.createCell((short) 20).setCellValue(tdOrder.getOrderIncome());
			}

			if (null != tdOrder.getPlatformService()) {
				row.createCell((short) 21).setCellValue(tdOrder.getPlatformService());
			}
			if (null != tdOrder.getPlatformService()) {
				double temp = tdOrder.getPlatformService() / tdOrder.getTotalGoodsPrice();
				row.createCell((short) 22).setCellValue(temp);
			}
			if (timeid == 0) {
				row.createCell((short) 23).setCellValue("所有");
			} else if (timeid == 1) {
				row.createCell((short) 23).setCellValue("今天");
			} else if (timeid == 2) {
				row.createCell((short) 23).setCellValue("最近一周");
			} else if (timeid == 3) {
				row.createCell((short) 23).setCellValue("最近一个月");
			} else if (timeid == 4) {
				row.createCell((short) 23).setCellValue("最近三个月");
			} else if (timeid == 6) {
				row.createCell((short) 23).setCellValue("最近半年");
			} else if (timeid == 12) {
				row.createCell((short) 23).setCellValue("近一年");
			}

		}
		return true;
	}

	/**
	 * @author lc
	 * @注释：文件写入和下载
	 */
	public Boolean download(HSSFWorkbook wb, String exportUrl, HttpServletResponse resp) {
		try {
			FileOutputStream fout = new FileOutputStream(exportUrl + "order.xls");
			// OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		OutputStream os;
		try {
			os = resp.getOutputStream();
			File file = new File(exportUrl + "order.xls");

			if (file.exists()) {
				try {
					resp.reset();
					resp.setHeader("Content-Disposition", "attachment; filename=" + "order.xls");
					resp.setContentType("application/octet-stream; charset=utf-8");
					os.write(FileUtils.readFileToByteArray(file));
					os.flush();
				} finally {
					if (os != null) {
						os.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @author lc
	 * @注释：计算总额和销售额
	 */
	public Double countprice(List<TdOrder> list) {
		Double price = new Double(0.00);
		for (int i = 0; i < list.size(); i++) {
			price += list.get(i).getTotalPrice();
		}
		return price;
	}

	public Double countsales(List<TdOrder> list) {
		Double sales = new Double(0.00);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStatusId().equals(2L) || list.get(i).getStatusId().equals(7L)) {

			} else {
				sales += list.get(i).getTotalPrice();
			}
		}
		return sales;
	}

	@RequestMapping(value = "/setting/pay/save", method = RequestMethod.POST)
	public String save(TdPayType tdPayType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdPayType.getId()) {
			tdManagerLogService.addLog("add", "新增支付方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改支付方式", req);
		}
		tdPayTypeService.save(tdPayType);

		return "redirect:/Verwalter/order/setting/pay/list";
	}

	@RequestMapping(value = "/setting/delivery/save", method = RequestMethod.POST)
	public String save(TdDeliveryType tdDeliveryType, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null == tdDeliveryType.getId()) {
			tdManagerLogService.addLog("add", "新增配送方式", req);
		} else {
			tdManagerLogService.addLog("edit", "修改配送方式", req);
		}

		tdDeliveryTypeService.save(tdDeliveryType);

		return "redirect:/Verwalter/order/setting/delivery/list";
	}

	@RequestMapping(value = "/setting/diysite/save", method = RequestMethod.POST)
	public String save(TdDiySite tdDiySite, String[] hid_photo_name_show360, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}
		String uris = parsePicUris(hid_photo_name_show360);

		tdDiySite.setShowPictures(uris);
		tdDiySite.setParkingNowNumber(tdDiySite.getParkingTotalNumber());

		if (null == tdDiySite.getId()) {
			tdManagerLogService.addLog("add", "新增车库", req);
			tdDiySite.setAllMoney(new Double(0));
		} else {
			tdManagerLogService.addLog("edit", "修改车库", req);
		}

		TdDiySite diySite = tdDiySiteService.save(tdDiySite);
		TdDiyUser diyUser = tdDiyUserService.findByDiyIdAndRoleId0(diySite.getId());
		if (null == diyUser) {
			diyUser = new TdDiyUser();
			diyUser.setDiyId(diySite.getId());
			diyUser.setRoleId(0L);
			diyUser.setIsEnable(diySite.getIsEnable());
			diyUser.setRealName(diySite.getTitle());
		}
		diyUser.setUsername(diySite.getUsername());
		diyUser.setPassword(diySite.getPassword());
		tdDiyUserService.save(diyUser);
		return "redirect:/Verwalter/order/setting/diysite/list";
	}

	@RequestMapping(value = "/dialog/contact")
	public String addressDialog(ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		return "/site_mag/dialog_contact";
	}

	@RequestMapping(value = "/dialog/delivery")
	public String sendDialog(String orderNumber, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderNumber && !orderNumber.isEmpty()) {
			map.addAttribute("order", tdOrderService.findByOrderNumber(orderNumber));
		}

		map.addAttribute("delivery_type_list", tdDeliveryTypeService.findByIsEnableTrue());

		return "/site_mag/dialog_delivery";
	}

	@RequestMapping(value = "/dialog/print")
	public String printDialog(Long orderId, ModelMap map, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			return "redirect:/Verwalter/login";
		}

		if (null != orderId) {
			TdOrder order = tdOrderService.findOne(orderId);
			TdDiySite diySite = tdDiySiteService.findOne(order.getDiyId());
			map.addAttribute("order", order);
			map.addAttribute("now", new Date());
			map.addAttribute("diySite", diySite);
			map.addAttribute("manager", req.getSession().getAttribute("manager"));

			if (null != order) {
				map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(order.getUsername()));
			}
		}

		return "/site_mag/dialog_order_print";
	}

	@RequestMapping(value = "/param/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber, String type, String data, String info, String name,
			String address, String postal, String mobile, String expressNumber, Long deliverTypeId, ModelMap map,
			HttpServletRequest req) {

		Map<String, Object> res = new HashMap<String, Object>();

		res.put("code", 1);

		String username = (String) req.getSession().getAttribute("manager");
		if (null == username) {
			res.put("message", "请重新登录");
			return res;
		}

		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty()) {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);

			// 修改备注
			if (type.equalsIgnoreCase("editMark")) {
				order.setRemarkInfo(data);
			}

			if (null == order.getDeliverTypeFee()) {
				order.setDeliverTypeFee(0.0);
			}

			if (null == order.getPayTypeFee()) {
				order.setPayTypeFee(0.0);
			}

			// 修改商品总金额
			else if (type.equalsIgnoreCase("editTotalGoodsPrice")) {
				double goodsPrice = Double.parseDouble(data);
				order.setTotalGoodsPrice(goodsPrice);

				order.setTotalPrice(goodsPrice + order.getPayTypeFee() + order.getDeliverTypeFee());
				order.setTotalPriceChangeInfo(info);
			}
			// 修改配送费用
			else if (type.equalsIgnoreCase("editDeliveryPrice")) {
				double deliveryPrice = Double.parseDouble(data);
				order.setDeliverTypeFee(deliveryPrice);
				order.setTotalPrice(deliveryPrice + order.getPayTypeFee() + order.getTotalGoodsPrice());
				order.setDeliverTypePriceChangeInfo(info);
			}
			// 修改支付手续费
			else if (type.equalsIgnoreCase("editPayPrice")) {
				double payPrice = Double.parseDouble(data);
				order.setPayTypeFee(payPrice);
				order.setTotalPrice(payPrice + order.getTotalGoodsPrice() + order.getDeliverTypeFee());
				order.setPayTypePriceChangeInfo(info);
			}
			// 修改联系方式
			else if (type.equalsIgnoreCase("editContact")) {
				order.setShippingName(name);
				order.setShippingAddress(address);
				order.setShippingPhone(mobile);
				order.setPostalCode(postal);
			}
			// 确认订单
			else if (type.equalsIgnoreCase("orderConfirm")) {
				if (order.getStatusId().equals(1L)) {
					order.setStatusId(2L);
					order.setCheckTime(new Date());
				}
			}
			// 确认付款
			else if (type.equalsIgnoreCase("orderPay")) {
				if (order.getStatusId().equals(2L)) {
					// 需付尾款
					if (null != order.getTotalLeftPrice() && order.getTotalLeftPrice() > 0) {
						order.setStatusId(3L);
					}
					// 不需付尾款，直接跳到可到店服务
					else {
						order.setStatusId(4L);
						/**
						 * @author lc
						 * @注释：添加同盟店所获返利
						 */
						// 用户
						TdUser tdUser = tdUserService.findByUsername(order.getUsername());

						// 同盟店
						TdDiySite tdShop = null;
						if (null != tdUser.getUpperDiySiteId()) {
							// 同盟店
							tdShop = tdDiySiteService.findOne(tdUser.getUpperDiySiteId());
						} else {
							// 同盟店
							tdShop = tdDiySiteService.findOne(order.getShopId());
						}
						List<TdOrderGoods> tdOrderGoodsList = order.getOrderGoodsList();

						Random random = new Random();
						String smscode = String.format("%04d", random.nextInt(9999));
						// 手机短信发送
						SMSUtil.send(order.getShippingPhone(), "29040", new String[] { tdUser.getUsername(),
								order.getOrderGoodsList().get(0).getGoodsTitle(), smscode });
						order.setSmscode(smscode);
						order = tdOrderService.save(order);
						SMSUtil.send(tdShop.getMobile(), "29039",
								new String[] { tdShop.getTitle(), tdUser.getUsername(),
										order.getOrderGoodsList().get(0).getGoodsTitle(),
										order.getAppointmentTime().toString() });
						System.out.println("---Sharon---: 向同盟店" + tdShop.getMobile() + "发送短信");

						Long totalPoints = 0L;
						Double totalCash = 0.0;
						Double platformService = 0.0;
						Double trainService = 0.0;
						Double shopOrderincome = 0.0;
						Double totalSaleprice = 0.0; // 订单商品总销售价
						Double totalCostprice = 0.0; // 订单商品总成本价
						// 返利总额
						if (null != tdOrderGoodsList) {
							for (TdOrderGoods tog : tdOrderGoodsList) {
								if (0 == tog.getGoodsSaleType()) // 正常销售
								{
									TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

									if (null != tdGoods && null != tdGoods.getReturnPoints()) {
										totalPoints += tdGoods.getReturnPoints() * tog.getQuantity();

										if (null != tdGoods.getShopReturnRation()) {
											totalCash += tdGoods.getSalePrice() * tdGoods.getShopReturnRation()
													* tog.getQuantity();
										}
									}
									if (null != tdGoods && null != tdGoods.getPlatformServiceReturnRation()) {
										platformService += tdGoods.getSalePrice()
												* tdGoods.getPlatformServiceReturnRation() * tog.getQuantity();
									}
									if (null != tdGoods && null != tdGoods.getTrainServiceReturnRation()) {
										trainService += tdGoods.getCostPrice() * tdGoods.getTrainServiceReturnRation()
												* tog.getQuantity();
									}
									totalSaleprice += tdGoods.getSalePrice() * tog.getQuantity();
									totalCostprice += tdGoods.getCostPrice() * tog.getQuantity();
								}
							}
							if (order.getTypeId().equals(1L)) {
								shopOrderincome = totalSaleprice - totalCostprice - totalPoints - platformService
										- trainService - totalCash;
							}
							// 用户返利
							if (null != tdUser) {
								TdUserPoint userPoint = new TdUserPoint();

								userPoint.setDetail("购买商品赠送粮草");
								userPoint.setOrderNumber(order.getOrderNumber());
								userPoint.setPoint(totalPoints);
								userPoint.setPointTime(new Date());
								userPoint.setTotalPoint(tdUser.getTotalPoints() + totalPoints);
								userPoint.setUsername(tdUser.getUsername());

								userPoint = tdUserPointService.save(userPoint);

								tdUser.setTotalPoints(userPoint.getTotalPoint());

								tdUserService.save(tdUser);
							}
						}

						// 同盟店返利
					}

					order.setPayTime(new Date());
				}
			}
			// 确认付尾款
			else if (type.equalsIgnoreCase("orderPayLeft")) {
				if (order.getStatusId().equals(3L)) {
					order.setStatusId(4L);
					order.setPayLeftTime(new Date());
					/**
					 * @author lc
					 * @注释：添加同盟店所获返利
					 */
					// 用户
					TdUser tdUser = tdUserService.findByUsername(order.getUsername());

					// 同盟店
					TdDiySite tdShop = null;
					if (null != tdUser.getUpperDiySiteId()) {
						// 同盟店
						tdShop = tdDiySiteService.findOne(tdUser.getUpperDiySiteId());
					} else {
						// 同盟店
						tdShop = tdDiySiteService.findOne(order.getShopId());
					}

					List<TdOrderGoods> tdOrderGoodsList = order.getOrderGoodsList();

					Long totalPoints = 0L;
					Double totalCash = 0.0;
					Double platformService = 0.0;
					Double trainService = 0.0;
					Double shopOrderincome = 0.0;
					// 返利总额
					if (null != tdOrderGoodsList) {
						for (TdOrderGoods tog : tdOrderGoodsList) {
							if (0 == tog.getGoodsSaleType()) // 正常销售
							{
								TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

								if (null != tdGoods && null != tdGoods.getReturnPoints()) {
									totalPoints += tdGoods.getReturnPoints();

									if (null != tdGoods.getShopReturnRation()) {
										totalCash = tdGoods.getSalePrice() * tdGoods.getShopReturnRation();
									}
								}
								if (null != tdGoods && null != tdGoods.getPlatformServiceReturnRation()) {
									platformService += tdGoods.getSalePrice()
											* tdGoods.getPlatformServiceReturnRation();
								}
								if (null != tdGoods && null != tdGoods.getTrainServiceReturnRation()) {
									trainService += tdGoods.getCostPrice() * tdGoods.getTrainServiceReturnRation();
								}
							}
						}

						// 用户返利
						if (null != tdUser) {
							TdUserPoint userPoint = new TdUserPoint();

							userPoint.setDetail("购买商品赠送粮草");
							userPoint.setOrderNumber(order.getOrderNumber());
							userPoint.setPoint(totalPoints);
							userPoint.setPointTime(new Date());
							userPoint.setTotalPoint(tdUser.getTotalPoints() + totalPoints);
							userPoint.setUsername(tdUser.getUsername());

							userPoint = tdUserPointService.save(userPoint);

							tdUser.setTotalPoints(userPoint.getTotalPoint());

							tdUserService.save(tdUser);
						}
					}
					// 手机短信发送
					SMSUtil.send(order.getShippingPhone(), "29040",
							new String[] { tdUser.getUsername(), order.getOrderGoodsList().get(0).getGoodsTitle(),
									order.getOrderNumber().substring(order.getOrderNumber().length() - 4) });

					SMSUtil.send(tdShop.getMobile(), "29039",
							new String[] { tdShop.getTitle(), tdUser.getUsername(),
									order.getOrderGoodsList().get(0).getGoodsTitle(),
									order.getAppointmentTime().toString() });
					System.out.println("---Sharon---: 向同盟店" + tdShop.getMobile() + "发送短信");
					// 同盟店返利
				}
			}
			// 确认已服务
			else if (type.equalsIgnoreCase("orderService")) {
				if (order.getStatusId().equals(4L)) {
					order.setStatusId(5L);
					order.setServiceTime(new Date());
				}
			}
			// 货到付款确认付款
			else if (type.equalsIgnoreCase("orderPayOffline")) {
				if (order.getStatusId().equals(2L) && !order.getIsOnlinePay()) {
					order.setStatusId(5L);
					order.setPayTime(new Date());
				}
			}
			// 确认发货
			else if (type.equalsIgnoreCase("orderDelivery")) {
				if (order.getStatusId().equals(3L)) {
					order.setDeliverTypeId(deliverTypeId);
					order.setExpressNumber(expressNumber);
					order.setStatusId(4L);
					order.setSendTime(new Date());

					TdUser tdUser = tdUserService.findByUsername(order.getUsername());

					if (null != tdUser) {
						SMSUtil.send(tdUser.getMobile(), "28744",
								new String[] { order.getUsername(), order.getOrderNumber() });
					}
				}
			}
			// 确认收货
			else if (type.equalsIgnoreCase("orderReceive")) {
				if (order.getStatusId().equals(4L)) {
					order.setStatusId(5L);
					order.setReceiveTime(new Date());
				}
			}
			// 确认完成
			else if (type.equalsIgnoreCase("orderFinish")) {
				if (order.getStatusId().equals(5L)) {
					order.setStatusId(6L);
					order.setFinishTime(new Date());

					tdUserService.addTotalSpend(order.getUsername(), order.getTotalPrice());
				}
			}
			// 确认取消
			else if (type.equalsIgnoreCase("orderCancel")) {
				if (order.getStatusId().equals(1L) || order.getStatusId().equals(2L)) {
					order.setStatusId(7L);
					order.setCancelTime(new Date());
				}
			}

			tdOrderService.save(order);
			tdManagerLogService.addLog("edit", "修改订单", req);

			res.put("code", 0);
			res.put("message", "修改成功!");
			return res;
		}

		res.put("message", "参数错误!");
		return res;
	}

	@RequestMapping(value = "order/sumPrice", method = RequestMethod.GET)
	public String sumPrice(String date, String date1, HttpServletRequest request) {

		return "/";
	}

	@ModelAttribute
	public void getModel(@RequestParam(value = "payTypeId", required = false) Long payTypeId,
			@RequestParam(value = "deliveryTypeId", required = false) Long deliveryTypeId,
			@RequestParam(value = "diySiteId", required = false) Long diySiteId, Model model) {
		if (null != payTypeId) {
			model.addAttribute("tdPayType", tdPayTypeService.findOne(payTypeId));
		}

		if (null != deliveryTypeId) {
			model.addAttribute("tdDeliveryType", tdDeliveryTypeService.findOne(deliveryTypeId));
		}

		if (null != diySiteId) {
			model.addAttribute("tdDiySite", tdDiySiteService.findOne(diySiteId));
		}
	}

	private void btnSave(String type, Long[] ids, Long[] sortIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == sortIds || ids.length < 1 || sortIds.length < 1) {
			return;
		}

		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];

			if (type.equalsIgnoreCase("pay")) {
				TdPayType e = tdPayTypeService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(sortIds[i]);
						tdPayTypeService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("delivery")) {
				TdDeliveryType e = tdDeliveryTypeService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(sortIds[i]);
						tdDeliveryTypeService.save(e);
					}
				}
			} else if (type.equalsIgnoreCase("diysite")) {
				TdDiySite e = tdDiySiteService.findOne(id);

				if (null != e) {
					if (sortIds.length > i) {
						e.setSortId(sortIds[i]);
						tdDiySiteService.save(e);
					}
				}
			}
		}
	}

	private void btnDelete(String type, Long[] ids, Integer[] chkIds) {
		if (null == type || type.isEmpty()) {
			return;
		}

		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				if (type.equalsIgnoreCase("pay")) {
					tdPayTypeService.delete(id);
				} else if (type.equalsIgnoreCase("delivery")) {
					tdDeliveryTypeService.delete(id);
				} else if (type.equalsIgnoreCase("diysite")) {
					tdDiySiteService.delete(id);
				}
			}
		}
	}

	private void btnConfirm(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)订单能进行确认，确认后状态为待发货(3L)
				if (tdOrder.getStatusId().equals(1L)) {
					tdOrder.setStatusId(3L);
					tdOrder.setCheckTime(new Date()); // 确认时间
					tdOrderService.save(tdOrder);
				}
			}
		}
	}

	private void btnCancel(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有待确认(1L)、待付款(2L)订单能进行删除，确认后状态为已取消(7L)
				if (tdOrder.getStatusId().equals(1L) || tdOrder.getStatusId().equals(2L)) {
					tdOrder.setStatusId(7L);
					tdOrder.setCancelTime(new Date()); // 取消时间
					tdOrderService.save(tdOrder);
				}
			}
		}
	}

	private void btnDelete(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds || ids.length < 1 || chkIds.length < 1) {
			return;
		}

		for (int chkId : chkIds) {
			if (chkId >= 0 && ids.length > chkId) {
				Long id = ids[chkId];

				TdOrder tdOrder = tdOrderService.findOne(id);

				// 只有已取消(7L)订单能进行删除
				if (tdOrder.getStatusId().equals(7L)) {
					tdOrderService.delete(tdOrder);
				}
			}
		}
	}

	/**
	 * 图片地址字符串整理，多张图片用,隔开
	 * 
	 * @param params
	 * @return
	 */
	private String parsePicUris(String[] uris) {
		if (null == uris || 0 == uris.length) {
			return null;
		}

		String res = "";

		for (String item : uris) {
			String uri = item.substring(item.indexOf("|") + 1, item.indexOf("|", 2));

			if (null != uri) {
				res += uri;
				res += ",";
			}
		}

		return res;
	}
}
