package com.ynyes.youbo.controller.depot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.service.TdAdService;
import com.ynyes.youbo.service.TdAdTypeService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.util.BufferedImageLuminanceSource;
import com.ynyes.youbo.util.SiteMagConstant;

@Controller
@RequestMapping("/depot")
public class TdDepotIndexController {

	@Autowired
	private TdCommonService tdCommonService;

	@Autowired
	private TdAdTypeService tdAdTypeService;

	@Autowired
	private TdAdService tdAdService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest req, Device device, ModelMap map) {

		tdCommonService.setHeader(map, req);
		System.err.println(SiteMagConstant.imagePath);

		// 停车场首页广告
		TdAdType adType = tdAdTypeService.findByTitle("停车场顶部轮播");

		if (null != adType) {
			map.addAttribute("depot_ad_list", tdAdService.findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
		}
		return "/depot/index";
	}

	@SuppressWarnings("all")
	@RequestMapping(value = "/analysis/qrcode")
	@ResponseBody
	public Map<String, Object> analysis() {
		Map<String, Object> res = new HashMap<>();

		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			String filePath = SiteMagConstant.imagePath + "/20151023092515674637QR.jpg";
			File file = new File(filePath);
			BufferedImage image = ImageIO.read(file);
			;
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, hints);

			System.out.println("result = " + result.toString());
			System.out.println("resultFormat = " + result.getBarcodeFormat());
			System.out.println("resultText = " + result.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
