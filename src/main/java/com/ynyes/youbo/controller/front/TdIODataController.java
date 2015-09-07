package com.ynyes.youbo.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdIOData;
import com.ynyes.youbo.service.TdIODataService;

/**
 * 停车场出入记录
 *
 */
@Controller
public class TdIODataController {

    @Autowired
    private TdIODataService tdIODataService;

    @RequestMapping(value = "/iodata/save", method=RequestMethod.POST)
    @ResponseBody
    public String addCart(TdIOData ioData,
            HttpServletRequest req) {
        
        if (null == tdIODataService.findByBusNoAndIoTime(ioData.getBusNo(), ioData.getIoTime()))
        { 
            tdIODataService.save(ioData);
        }

        return "ok";
    }
}
