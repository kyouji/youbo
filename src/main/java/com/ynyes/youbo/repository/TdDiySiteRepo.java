package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDiySite;

/**
 * TdDiySite 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdDiySiteRepo extends
		PagingAndSortingRepository<TdDiySite, Long>,
		JpaSpecificationExecutor<TdDiySite> 
{
    Page<TdDiySite> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
    
    List<TdDiySite> findByIsEnableTrue();
    
    List<TdDiySite> findByCityAndIsEnableTrueOrderBySortIdAsc(String city);
    /**
	 * @author lc
	 * @注释：
	 */
    TdDiySite findByUsernameAndIsEnableTrue(String username);
    List<TdDiySite> findByDisctrictAndIsEnableTrue(String disctrict);
    
    /**
     * @author dengxiao
     * 根据名称查找到指定的停车场信息
     */
    TdDiySite findByTitleAndIsEnableTrue(String title);
    
    /**
     * @author dengxiao
     * 根据ID查找到指定停车场的信息
     */
    TdDiySite findByIdAndIsEnableTrue(Long id);
    
    /**
     * @author dengxiao
     * 根据停车场的登陆用户名和密码查找到指定的停车场信息
     */
    TdDiySite findByUsernameAndPasswordAndIsEnableTrue(String username,String password);
    
    /**
     * @author dengxiao
     * 根据停车场登陆名查找停车场（用于判断是否有此登陆名的停车场）
     */
    TdDiySite findByUsername(String username);
}
