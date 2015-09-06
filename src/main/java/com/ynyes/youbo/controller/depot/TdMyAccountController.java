package com.ynyes.youbo.controller.depot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdBankcardService;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdPayTypeService;
import com.ynyes.youbo.service.TdUserService;

@Controller
@RequestMapping("/depot/myaccount")
public class TdMyAccountController {
	
	
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdUserService tdUserService;
	
	@Autowired
	private TdPayTypeService tdPayTypeService;
	
	@Autowired
	private TdBankcardService tdBankcardService;
	
	/**
	 *  我的账户首页
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map)
	{
		req.getSession().setAttribute("username", "depotuser");
		tdCommonService.setHeader(map, req);
		map.addAttribute("user", tdUserService.findByUsername("depotuser"));
		return "/depot/my_account";
	}
		
	/**
	 * 银行卡
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/bankcard")
    public String bankcard(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/bankcard";
	}
	
	/**
     *  银行卡添加
     * @return
     */
    @RequestMapping(value = "/addbankcard")
    public String bankcardAdd(TdBankcard bankcard, HttpServletRequest req,ModelMap map)
    {
    	map.addAttribute("paytape_list", tdPayTypeService.findAll());
    	return "/depot/bankcard_add";
    }
    /**
     *  保存银行卡
     * @param bankcard
     * @param req
     * @return
     */
    @RequestMapping(value = "bankcard/save",method = RequestMethod.POST)
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
    		return res;
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
	 * 退款申请
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/refund")
    public String refund(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/refund";
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
		return "/depot/message_center";
	}
	
	/**
	 * 提现
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/withdrawal")
    public String withdrawal(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/withdraw";
	}

	/**
	 *  提现记录
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/cashrecord")
    public String cashrecord(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/withdraw_record";
	}
}
