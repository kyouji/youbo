package com.ynyes.youbo.controller.user;

import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.mail.handlers.message_rfc822;
import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.entity.TdPayType;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.entity.TdUserComment;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdCouponService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdGoodsService;
import com.ynyes.youbo.service.TdOrderGoodsService;
import com.ynyes.youbo.service.TdOrderService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdShippingAddressService;
import com.ynyes.youbo.service.TdUserCashRewardService;
import com.ynyes.youbo.service.TdUserCollectService;
import com.ynyes.youbo.service.TdUserCommentService;
import com.ynyes.youbo.service.TdUserConsultService;
import com.ynyes.youbo.service.TdUserPointService;
import com.ynyes.youbo.service.TdUserRecentVisitService;
import com.ynyes.youbo.service.TdUserReturnService;
import com.ynyes.youbo.service.TdUserService;

/**
 * 用户中心
 * @author Sharon
 *
 */
@Controller
@RequestMapping(value = "/user")
public class TdUserCenterController {
    
    @Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdGoodsService tdGoodsService;
    
    @Autowired
    private TdUserReturnService tdUserReturnService;
    
    @Autowired
    private TdOrderService tdOrderService;
    
    @Autowired
    private TdCouponService tdCoupanService;
    
    @Autowired
    private TdUserPointService tdUserPointService;
    
    @Autowired
    private TdUserCollectService tdUserCollectService;
    
    @Autowired
    private TdUserConsultService tdUserConsultService;
    
    @Autowired
    private TdUserCommentService tdUserCommentService;
    
    @Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;
    
    @Autowired
    private TdShippingAddressService tdShippingAddressService;
    
    @Autowired
    private TdOrderGoodsService tdOrderGoodsService;
    
    @Autowired
    private TdUserCashRewardService tdUserCashRewardService;
    
    @Autowired
    private TdCommonService tdCommonService;
    
    @Autowired
    private TdDiySiteService tdDiySiteService;
    
    @Autowired
    private TdPayTypeService tdPayTypeService;
    
    @Autowired
    private TdBankcardService tdBankcardService;
    
    /**
     * 用户个人中心
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/center")
    public String user(HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");
//        if (null == username)
//        {
//            return "redirect:/touch/login";
//        }
        req.getSession().setAttribute("username", "user123");
        tdCommonService.setHeader(map, req);
        map.addAttribute("user",tdUserService.findByUsername("user123"));
        map.addAttribute("server_ip", req.getLocalName());
        map.addAttribute("server_port", req.getLocalPort());
        
        return "/user/mycenter";
    }
    
    
    
    /**
     * 设置
     * @return
     */
    @RequestMapping(value = "/center/setting")
    public String setting()
    {
    	return "/user/setting";
    }
    
    /**
     * 设置城市
     * @return
     */
    @RequestMapping(value = "/center/setting/changecity")
    public String settingcity()
    {
    	return "/user/setting_city";
    }
    
    /**
     * 设置离线地图
     * @return
     */
    @RequestMapping(value = "/center/setting/map")
    public String settingmap()
    {
    	return "/user/setting_map";
    }
    
    
    /**
     *  关于我们
     * @return
     */
    @RequestMapping(value = "/center/about")
    public String about()
    {
    	return "/user/about";
    }
    
    /**
     *  银行卡
     * @return
     */
    @RequestMapping(value = "/center/bankcard")
    public String bankcard(HttpServletRequest req,ModelMap map)
    {
    	String username = (String) req.getSession().getAttribute("username");
    	TdUser user = tdUserService.findByUsername(username);
    	map.addAttribute("bankcard_list", user.getBankcardList());
    	return "/user/bankcard";
    }
    
    /**
     *  银行卡添加
     * @return
     */
    @RequestMapping(value = "/center/bankcard/add")
    public String bankcardAdd(TdBankcard bankcard, HttpServletRequest req,ModelMap map)
    {
    	map.addAttribute("paytape_list", tdPayTypeService.findAll());
    	return "/user/bankcard_add";
    }
    /**
     *  保存银行卡
     * @param bankcard
     * @param req
     * @return
     */
    @RequestMapping(value = "/center/bankcard/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bankcardSave(TdBankcard bankcard,HttpServletRequest req,ModelMap map)
    {
    	String username = (String) req.getSession().getAttribute("username");
    	Map<String, Object> res = new HashMap<String ,Object>();
    	res.put("code", 1);
    	res.put("message", "用户不存在");
    	if (username == null)
    	{
			
		}
    	TdUser user = tdUserService.findByUsername(username);
    	if (user == null)
    	{	
    		res.put("message", "用户不存在");
		}
    	
    	List<TdBankcard> list = user.getBankcardList();
    	System.err.println(list);
    	
    	if(null == user.getBankcardList()){
    		List<TdBankcard> bankCards = new ArrayList<>();
    		bankCards.add(bankcard);
    		user.setBankcardList(bankCards);
    	}else{
    		user.getBankcardList().add(bankcard);
    	}
    	
    	tdBankcardService.save(bankcard);
    	tdUserService.save(user);
    	res.put("code", 0);
    	return res;
    }
    
    
    /**
     *  用户反馈页面
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/center/comment")
    public String comment(HttpServletRequest req, ModelMap map)
    {
    	return "/user/feedback";
    }
    
    /**
     *  用户反馈
     *  
     * @author mdj
     * @param req
     * @param tdComment
     * @param code
     * @param map
     * @return
     */
    @RequestMapping(value = "/center/comment", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentAdd(HttpServletRequest req, 
                        TdUserComment tdComment,
                        ModelMap map){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == tdComment.getContent() || tdComment.getContent().equals(""))
        {
            res.put("message", "内容不能为空！");
            return res;
        }

        tdComment.setCommentTime(new Date());
        tdComment.setIsReplied(false);
        tdComment.setNegativeNumber(0L);
        tdComment.setPositiveNumber(0L);
        tdComment.setUsername(username);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        
        if (null != user)
        {
            tdComment.setUserHeadUri(user.getHeadImageUri());
        }
        
        tdComment.setStatusId(0L);
        
        tdUserCommentService.save(tdComment);
        res.put("code", 0);
        
        return res;
    }
    
}