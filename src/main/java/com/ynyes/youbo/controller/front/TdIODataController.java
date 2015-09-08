package com.ynyes.youbo.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdIOData;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdIODataService;

/**
 * 停车场出入记录
 *
 */
@Controller
public class TdIODataController {

    @Autowired
    private TdIODataService tdIODataService;
    
    @Autowired
    private TdDiySiteService tdDiySiteService;

    @RequestMapping(value = "/iodata/save", method=RequestMethod.POST)
    @ResponseBody
    public String addCart(TdIOData ioData,
            HttpServletRequest req) {
        
        if (null == tdIODataService.findByBusNoAndIoTime(ioData.getBusNo(), ioData.getIoTime()))
        { 
            ioData = tdIODataService.save(ioData);
            
            if (ioData.getIoState().equals("正常进入"))
            {
                
            }
            else if (ioData.getIoState().equals("正常外出"))
            {
                
                
            }
        }

        return "ok";
    }
}
