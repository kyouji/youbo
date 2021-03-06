package com.ynyes.youbo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.youbo.entity.TdAd;
import com.ynyes.youbo.entity.TdAdType;
import com.ynyes.youbo.repository.TdAdRepo;

/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdAdService {
    
    @Autowired
    TdAdRepo repository;
    
    @Autowired
    TdAdTypeService tdAdTypeService;
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdAd e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdAd> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdAd findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdAd> findAll(Iterable<Long> ids)
    {
        return (List<TdAd>) repository.findAll(ids);
    }
    
    public List<TdAd> findAll()
    {
        return (List<TdAd>) repository.findAll();
    }
    
    public List<TdAd> findByTypeIdAndIsValidTrueOrderBySortIdAsc(Long typeId)
    {
        return repository.findByTypeIdAndStartTimeBeforeAndEndTimeAfterAndIsEnableTrueOrderBySortIdAsc(typeId, new Date(), new Date());
    }
    
    public Page<TdAd> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdAd> findAllOrderBySortIdAsc()
    {
        return (List<TdAd>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdAd save(TdAd e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        if (null != e.getTypeId())
        {
            TdAdType adType = tdAdTypeService.findOne(e.getTypeId());
            
            if (null != adType)
            {
                e.setTypeIsNewWindow(adType.getIsNewWindow());
            }
        }
        
        return repository.save(e);
    }
    
    public List<TdAd> save(List<TdAd> entities)
    {
        
        return (List<TdAd>) repository.save(entities);
    }
}
