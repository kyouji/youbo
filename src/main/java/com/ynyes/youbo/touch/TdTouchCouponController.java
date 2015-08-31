package com.ynyes.youbo.touch;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdCoupon;
import com.ynyes.youbo.entity.TdCouponType;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.service.TdCommonService;
import com.ynyes.youbo.service.TdCouponService;
import com.ynyes.youbo.service.TdCouponTypeService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdUserService;
import com.ynyes.youbo.util.SMSUtil;

/**
 * touch端优惠卷处理Controller
 * @author mdj
 * 
 */

@Controller
@RequestMapping("/touch/coupon")
public class TdTouchCouponController {
	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired 
	private TdDiySiteService tdDiySiteService;
	
	@Autowired 
    private TdCouponService tdCouponService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired 
    private TdCouponTypeService tdCouponTypeService;
	
	@RequestMapping("/list")
	public String couponList(HttpServletRequest req, ModelMap map)
	{
		tdCommonService.setHeader(map, req);
		
		 // 同盟店列表
        map.addAttribute("shop_list", tdDiySiteService.findByIsEnableTrue());
        
        // 优惠券种类
        List<TdCouponType> couponTypeList = tdCouponTypeService.findAllOrderBySortIdAsc();
        
        map.addAttribute("coupon_type_list", couponTypeList);
        
        if (null != couponTypeList)
        {
            for (TdCouponType ct : couponTypeList)
            {
                List<TdCoupon> couponList = tdCouponService.findByTypeIdAndIsDistributtedFalse(ct.getId());
                
                List<TdCoupon> disCouponList = tdCouponService.findByTypeIdAndIsDistributtedTrueOrderByIdDesc(ct.getId());
                
                // 未领取优惠券
                map.addAttribute("coupon_" + ct.getId() + "_list", couponList);
                
                // 已领取优惠券
                map.addAttribute("distributed_coupon_" + ct.getId() + "_list", disCouponList);
            }
        }
        //传入用户信息
        String username = (String) req.getSession().getAttribute("username");
        TdUser tdUser = tdUserService.findByUsername(username);
        if (null != tdUser) {
        	 map.addAttribute("user", tdUser);
		}
		return "/touch/coupon_list";
	}
	
	/**
	 * 优惠券领用
	 * @param page
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> couponRequest(Long couponId, 
                            String username,
                            String carCode,
                            String mobile,
                            String code,
                            HttpServletRequest req) {
	    Map<String, Object> res = new HashMap<String, Object>();
	    String codeBack = (String) req.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        
	    res.put("code", 1);
	    
	    if (null == couponId)
	    {
	        res.put("message", "未选择优惠券");
	        return res;
	    }
	    
	    if (null == code || null == codeBack || !code.equalsIgnoreCase(codeBack))
	    {
	        res.put("message", "验证码错误");
            return res;
	    }
	    
	    TdCoupon leftCoupon = tdCouponService.findOne(couponId);
	    
	    if (null == leftCoupon || leftCoupon.getLeftNumber().compareTo(1L) < 0)
	    {
	        res.put("message", "该优惠券已被领完");
            return res;
	    }
	    
	    TdCoupon coupon = tdCouponService.findByTypeIdAndMobileAndIsDistributtedTrue(leftCoupon.getTypeId(), mobile);
	    
	    if (null != coupon)
	    {
	        res.put("message", "每个用户限领一张同类型优惠券");
            return res;
	    }
	    
	    leftCoupon.setLeftNumber(leftCoupon.getLeftNumber() - 1L);
	    
	    tdCouponService.save(leftCoupon);
	    
	    TdCouponType ctype = tdCouponTypeService.findOne(leftCoupon.getTypeId());
	    
	    TdCoupon getCoupon = new TdCoupon();
	    
	    getCoupon.setCarCode(carCode);
	    getCoupon.setDiySiteId(leftCoupon.getDiySiteId());
	    getCoupon.setDiySiteTitle(leftCoupon.getDiySiteTitle());
	    getCoupon.setGetNumber(1L);
	    getCoupon.setGetTime(new Date());
	    
	    if (null != ctype && null != ctype.getTotalDays())
	    {
    	    Calendar ca = Calendar.getInstance();
    	    ca.add(Calendar.DATE, ctype.getTotalDays().intValue());
    	    getCoupon.setExpireTime(ca.getTime());
	    }
	    
	    getCoupon.setIsDistributted(true);
	    getCoupon.setIsUsed(false);
	    getCoupon.setMobile(mobile);
	    getCoupon.setTypeDescription(leftCoupon.getTypeDescription());
	    getCoupon.setTypeId(leftCoupon.getTypeId());
	    getCoupon.setTypePicUri(leftCoupon.getTypePicUri());
	    getCoupon.setTypeTitle(leftCoupon.getTypeTitle());
	    getCoupon.setUsername(username);
	    
	    tdCouponService.save(getCoupon);
	    
	    // 发送短信
	    SMSUtil.send(mobile, "28745", new String[] { username,
	                    mobile.substring(mobile.length() - 4)});
	    
	    res.put("code", 0);
	    
	    return res;
	}
	

}
