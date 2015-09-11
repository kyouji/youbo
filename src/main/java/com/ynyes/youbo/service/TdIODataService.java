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

import com.ynyes.youbo.entity.TdIOData;
import com.ynyes.youbo.repository.TdIODataRepo;

/**
 * TdIOData 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdIODataService {
    
    @Autowired
    TdIODataRepo repository;
    
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
    public void delete(TdIOData e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdIOData> entities)
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
    public TdIOData findOne(Long id)
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
    public List<TdIOData> findAll(Iterable<Long> ids)
    {
        return (List<TdIOData>) repository.findAll(ids);
    }
    
    public List<TdIOData> findAll()
    {
        return (List<TdIOData>) repository.findAll();
    }
    
    public Page<TdIOData> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdIOData> findAllOrderBySortIdAsc()
    {
        return (List<TdIOData>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public TdIOData findByBusNoAndIoDate(String busNo, Date ioTime)
    {
        if (null == busNo || null == ioTime)
        {
            return null;
        }
        
        return repository.findTopByBusNoAndIoDate(busNo, ioTime);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdIOData save(TdIOData e)
    {
        return repository.save(e);
    }
    
    public List<TdIOData> save(List<TdIOData> entities)
    {
        
        return (List<TdIOData>) repository.save(entities);
    }
}
