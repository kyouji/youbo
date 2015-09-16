package com.ynyes.youbo.controller.depot;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.VerifServlet;

@Controller
@RequestMapping("/depot")
public class TdDepotLoginAndRegisterController {
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdDiySiteService tdDiySiteService;
	
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
		String curentuser = (String) req.getSession().getAttribute("depotuser");
		if (curentuser != null)
		{
			return "redirect:/depot/myaccount";
		}
		return "/depot/login";
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
        
        TdDiySite user = tdDiySiteService.findByUsernameAndPasswordAndIsEnableTrue(username, password);
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
        
        
        req.getSession().setAttribute("siteUsername", username);
        
        return res;
    }
	
	/**                      
	 * 用注册
	 * @param req
	 * @param device
	 * @param map
	 * @return
	 */
	@RequestMapping("/register")
    public String register(HttpServletRequest req, Device device, ModelMap map)
	{
		return "/depot/register";
	}
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public void verify(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		VerifServlet randomValidateCode = new VerifServlet();
		randomValidateCode.getRandcode(request, response);
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
    public String reg(String username,
                String mobile,
                String password,
                String email,
                String smsCode,
                String code,
                String carCode,
                Long shareId,
                HttpServletRequest request){
        String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        if (!codeBack.equalsIgnoreCase(code))
        {
            if (null == shareId)
            {
                return "redirect:/reg?errCode=1&name= "+username+"&carCode="+carCode;
            }
            else
            {
                return "redirect:/reg?errCode=1&shareId=" + shareId + "&name= "+username+"&carCode="+carCode;
            }
        }
        
        /**
         * 因为是手机注册，所以将username字段的值赋值给mobile
         * @author dengxiao
         */
        mobile = username;
        
        TdUser user = tdUserService.addNewUser(username, password, mobile, email, carCode);
        user.setRoleId(2L);
        
        user = tdUserService.save(user);
        
        request.getSession().setAttribute("username", username);
        
        String referer = (String) request.getAttribute("referer");
        
        if (null != request.getAttribute("referer"))
        {
            return "redirect:" + referer;
        }
        
        if (null == shareId)
        {
            return "redirect:/depot";
        }
        
        return "redirect:/depot";
    }
	
	
	@RequestMapping(value = "/check/{type}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> validateForm(@PathVariable String type,String param,HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		
		res.put("status", "n");
		if("username".equalsIgnoreCase(type)){
			TdUser user = tdUserService.findByUsername(param);
			if(null != user){
				res.put("info",	"该用户已经存在！");
				return res;
			}
		}
		if("yzm".equalsIgnoreCase(type)){
			String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
			if(!param.equalsIgnoreCase(codeBack)){
				res.put("info", "验证码错误！");
				return res;
			}
		}
		res.put("status", "y");
		return res;
	}
}
