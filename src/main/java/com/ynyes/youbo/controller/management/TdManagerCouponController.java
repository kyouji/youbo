package com.ynyes.youbo.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.youbo.entity.TdCoupon;
import com.ynyes.youbo.entity.TdCouponType;
import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.service.TdCouponService;
import com.ynyes.youbo.service.TdCouponTypeService;
import com.ynyes.youbo.service.TdDiySiteService;
import com.ynyes.youbo.service.TdManagerLogService;
import com.ynyes.youbo.service.TdProductCategoryService;
import com.ynyes.youbo.util.SiteMagConstant;

/**
 * 优惠券管理
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/coupon")
public class TdManagerCouponController {
    
    @Autowired
    private TdCouponTypeService tdCouponTypeService;
    
    @Autowired
    private TdCouponService tdCouponService;
    
    @Autowired
    private TdManagerLogService tdManagerLogService;
    
    @Autowired 
    private TdDiySiteService tdDiySiteService;
    
    @Autowired
    private TdProductCategoryService tdProductCategoryService;
    
    
//    @RequestMapping(value="/check", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> validateForm(String param, Long id) {
//        Map<String, String> res = new HashMap<String, String>();
//        
//        res.put("status", "n");
//        
//        if (null == param || param.isEmpty())
//        {
//            res.put("info", "该字段不能为空");
//            return res;
//        }
//        
//        if (null == id)
//        {
//            if (null != tdSiteService.findByTitle(param))
//            {
//                res.put("info", "已存在同名站点");
//                return res;
//            }
//        }
//        else
//        {
//            if (null != tdSiteService.findByTitleAndIdNot(param, id))
//            {
//                res.put("info", "已存在同名站点");
//                return res;
//            }
//        }
//        
//        res.put("status", "y");
//        
//        return res;
//    }
    
    @RequestMapping(value="/type/list")
    public String couponType(String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnTypeDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除优惠券类型", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnTypeSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "修改优惠券类型", req);
            }
        }
        
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        List<TdCouponType> couponTypeList = null;
        
        couponTypeList = tdCouponTypeService.findAllOrderBySortIdAsc();
        
        map.addAttribute("coupon_type_list", couponTypeList);
        
        return "/site_mag/coupon_type_list";
    }
    
    @RequestMapping(value = "/type/edit")
    public String typeEdit(Long id, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("category_list", tdProductCategoryService.findAll());

        if (null != id) {
            map.addAttribute("coupon_type", tdCouponTypeService.findOne(id));
        }
        return "/site_mag/coupon_type_edit";
    }
    
    @RequestMapping(value = "/type/save")
    public String typeSave(TdCouponType tdCouponType, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        if (null == tdCouponType.getId()) {
            tdManagerLogService.addLog("add", "用户修改优惠券类型", req);
        } else {
            tdManagerLogService.addLog("edit", "用户修改优惠券类型", req);
        }

        tdCouponTypeService.save(tdCouponType);       
        return "redirect:/Verwalter/coupon/type/list";
    }
    
    
    @RequestMapping(value="/list")
    public String setting(Integer page,
                          Integer size,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除优惠券", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "修改优惠券", req);
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        Page<TdCoupon> couponPage = null;
        
        couponPage = tdCouponService.findByIsDistributtedFalseOrderBySortIdAsc(page, size);
        
        map.addAttribute("coupon_page", couponPage);
        
        return "/site_mag/coupon_list";
    }
    
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        map.addAttribute("diy_site_list", tdDiySiteService.findByIsEnableTrue());
        
        List<TdCouponType> couponTypeList = null;
        
        couponTypeList = tdCouponTypeService.findAllOrderBySortIdAsc();
        
        map.addAttribute("coupon_type_list", couponTypeList);

        if (null != id)
        {
            map.addAttribute("coupon", tdCouponService.findOne(id));
            return "/site_mag/coupon_edit_hasId";
        }
        return "/site_mag/coupon_edit";
    }
    
    @RequestMapping(value="/distributed/list")
    public String distributedList(Integer page,
                          Integer size,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除优惠券", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "修改优惠券", req);
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        Page<TdCoupon> couponPage = null;
        
        couponPage = tdCouponService.findByIsDistributtedTrueOrderBySortIdAsc(page, size);
        
        map.addAttribute("coupon_page", couponPage);
        
        return "/site_mag/coupon_distributed_list";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdCoupon tdCoupon,
                        String __VIEWSTATE,
                        Long[] leftNumbers,
                        Long typeId,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdCoupon.getId())
        {
            tdManagerLogService.addLog("add", "用户修改优惠券", req);
            
            List<TdDiySite> tdDiySiteList = tdDiySiteService.findByIsEnableTrue();
            
            if (null != tdDiySiteList && tdDiySiteList.size() > 0
                    && null != leftNumbers && leftNumbers.length > 0
                    && null != typeId)
            {
            	/**
				 * @author lc
				 * @注释：如果不是免费洗车券和免费打蜡券就不存在同盟店
				 */
            	TdCouponType tdCouponType = tdCouponTypeService.findOne(typeId);
            	
            	if (tdCouponType.getTitle().equals("免费打蜡券") || tdCouponType.getTitle().equals("免费洗车券")) {
            		 for (int i=0; i<tdDiySiteList.size(); i++)
                     {
                         TdDiySite tds = tdDiySiteList.get(i);
                         
                         if (null != tds && leftNumbers.length > i)
                         {
                             TdCoupon coupon = tdCouponService.findTopByTypeIdAndDiySiteIdAndIsDistributtedFalse(typeId, tds.getId());
                             
                             if (null == coupon)
                             {
                                 coupon = new TdCoupon();
                                 coupon.setDiySiteId(tds.getId());
                                 coupon.setLeftNumber(leftNumbers[i]);
                                 coupon.setTypeId(typeId);
                                 coupon.setSortId(99L);
                                 coupon.setPrice(tdCouponType.getPrice());
                             }
                             else
                             {
                                 coupon.setLeftNumber(coupon.getLeftNumber() + leftNumbers[i]);
                             }
                             
                             tdCouponService.save(coupon);
                         }
                     }
				}else{
					TdCoupon coupon = tdCouponService.findByTypeId(typeId);
                    
                    if (null == coupon)
                    {
                        coupon = new TdCoupon();                        
                        coupon.setLeftNumber(leftNumbers[0]);
                        coupon.setTypeId(typeId);
                        coupon.setSortId(99L);
                        coupon.setPrice(tdCouponType.getPrice());
                    }
                    else
                    {
                        coupon.setLeftNumber(coupon.getLeftNumber() + leftNumbers[0]);
                    }
                    
                    tdCouponService.save(coupon);
				}
               
            }
            
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改优惠券", req);
            tdCouponService.save(tdCoupon);
        }
        
        return "redirect:/Verwalter/coupon/list";
    }
    /**
	 * @author lc
	 * @注释：通过id返回title
	 */
    @RequestMapping(value = "/getTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(Long typeId, 
			 HttpServletRequest request) {
    	Map<String, Object> res = new HashMap<String, Object>();

		res.put("code", 1);
		if (null == typeId) {
			res.put("msg", "error");
			return res;
		}
		
		TdCouponType tdCouponType = tdCouponTypeService.findOne(typeId);
		res.put("typetitle", tdCouponType.getTitle());
		res.put("code", 0);
		return res;
    }
	
	
    @ModelAttribute
    public void getModel(@RequestParam(value = "couponTypeId", required = false) Long couponTypeId,
                        @RequestParam(value = "couponId", required = false) Long couponId,
                        Model model) {
        if (null != couponTypeId) {
            model.addAttribute("tdCouponType", tdCouponTypeService.findOne(couponTypeId));
        }
        
        if (null != couponId) {
            model.addAttribute("tdCoupon", tdCouponService.findOne(couponId));
        }
    }
    
    private void btnTypeSave(Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            TdCouponType e = tdCouponTypeService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdCouponTypeService.save(e);
                }
            }
        }
    }
    
    private void btnTypeDelete(Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                tdCouponTypeService.delete(id);
            }
        }
    }
    
    private void btnSave(Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            TdCoupon e = tdCouponService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdCouponService.save(e);
                }
            }
        }
    }
    
    private void btnDelete(Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                tdCouponService.delete(id);
            }
        }
    }
}
