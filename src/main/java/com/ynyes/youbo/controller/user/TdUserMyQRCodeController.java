package com.ynyes.youbo.controller.user;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.ibm.icu.text.SimpleDateFormat;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.util.MatrixToImageWriter;
import com.ynyes.youbo.util.SiteMagConstant;

@Controller
@RequestMapping("/user/code")
public class TdUserMyQRCodeController {

	@Autowired
	private TdOrderService tdOrderService;

	@SuppressWarnings("all")
	@RequestMapping
	public String index(HttpServletRequest req, Device device, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");
		if (null == username) {
			return "redirect:/user/center/login";
		}

		if (null != username) {
			List<TdOrder> list = tdOrderService.findByUsername(username);
			TdOrder currentOrder = null;
			if (null != list && list.size() > 0) {
				currentOrder = list.get(0);
			}

			// 二维码内部包含的信息为订单id，订单车牌号，订单停车场
			String content = "NULL,NULL,NULL";

			if (null != currentOrder && currentOrder.getStatusId() != 4L) {
				content = currentOrder.getId() + "," + currentOrder.getCarCode() + "," + currentOrder.getDiyId();
			}

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			Random random = new Random();
			int suiji = random.nextInt(900) + 100;
			String fileName = sdf.format(dt) + suiji + "QR.jpg";

			try {
				MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

				Map hints = new HashMap();
				hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
				File file1 = new File(SiteMagConstant.imagePath + "/" + fileName);
				MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			map.addAttribute("src", "/" + fileName);

		}
		return "/user/my_QRcode";
	}
}
