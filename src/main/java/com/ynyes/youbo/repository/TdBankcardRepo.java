package com.ynyes.youbo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdBankcard;

/**
 * TdBrandcard 实体数据库操作接口
 * 
 * @author mdj
 *
 */
public interface TdBankcardRepo extends
		PagingAndSortingRepository<TdBankcard, Long>,
		JpaSpecificationExecutor<TdBankcard>  {

}
