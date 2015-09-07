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
import org.springframework.mobile.device.Device;
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
@RequestMapping(value = "/user/center")
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
    @RequestMapping
    public String user(HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/user/center/login";
        }
        tdCommonService.setHeader(map, req);
        map.addAttribute("user",tdUserService.findByMobile(username));
        map.addAttribute("server_ip", req.getLocalName());
        map.addAttribute("server_port", req.getLocalPort());
        
        return "/user/user_center";
    }
    
    
    
    /**
     * 设置
     * @return
     */
    @RequestMapping(value = "setting")
    public String setting()
    {
    	return "/user/setting";
    }
    
    /**
     * 设置城市
     * @return
     */
    @RequestMapping(value = "/setting/changecity")
    public String settingcity()
    {
    	return "/user/setting_city";
    }
    
    /**
     * 设置离线地图
     * @return
     */
    @RequestMapping(value = "/setting/map")
    public String settingmap()
    {
    	return "/user/setting_map";
    }
    
    
    /**
     *  关于我们
     * @return
     */
    @RequestMapping(value = "/about")
    public String about()
    {
    	return "/user/about";
    }
    
    /**
     *  银行卡
     * @return
     */
    @RequestMapping(value = "/bankcard")
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
    @RequestMapping(value = "/bankcard/add")
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
    @RequestMapping(value = "/bankcard/add",method = RequestMethod.POST)
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
    @RequestMapping(value = "/comment")
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
    @RequestMapping(value = "/comment", method=RequestMethod.POST)
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
    
    /**
	 * 消息中心
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/message")
    public String message(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/user/message_center";
	}
	
	
	/**
	 * 个人中心
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/info")
    public String info(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/user/user_info";
	}
	/**
	 * 个人中心
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/info/edit")
    public String infoEdit(HttpServletRequest req, String editType, ModelMap map)
	{
		switch (editType) 
		{
			case "username"://用户名
				return "/user/user_info_username";
			case "mobile":  //手机号
				return "/user/user_info_mobile";
			case "plate":	//车牌号
				return "/user/user_info_plate";
			case "password"://登录密码
				return "/user/user_info_password";
			case "paykey":	//支付密码
				return "/user/user_info_paykey";

			default:
				break;
		}		
		return "/user/center";
	}
	
	/**
	 * 用户登录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/login")
    public String login(HttpServletRequest req, Device device, ModelMap map,String username,String password)
	{
		String curentuser = (String) req.getSession().getAttribute("username");
		if (curentuser != null)
		{
			return "redirect:/user/center";
		}
		return "/user/login";
	}
	
	/**
	 * 用户登录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	 @RequestMapping(value = "/login", method=RequestMethod.POST)
     @ResponseBody
    public Map<String, Object> login(HttpServletRequest req, 
                        String username,
                        String password,
                        ModelMap map){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);
        
       // String user = (String) req.getSession().getAttribute("username");
        TdUser user = tdUserService.findByMobile(username);
        if (user == null)
        {
			res.put("msg", "用户不存在!");
			return res;
		}
        if (!user.getPassword().equals(password))
        {
			res.put("msg", "密码错误！");
			return res;
		}
        res.put("code", 0);
        
        
        req.getSession().setAttribute("username", username);
        
        return res;
    }
	
	/**
	 * 用户登录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/register")
    public String register(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/user/register";
	}
    
}