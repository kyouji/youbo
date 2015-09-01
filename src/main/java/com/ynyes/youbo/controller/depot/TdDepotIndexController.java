package com.ynyes.youbo.controller.depot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/depot")
public class TdDepotIndexController {
	@RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map) {
		return "/depot/index";
	}
}
