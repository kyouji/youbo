package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDeposit;

public interface TdDepositRepo extends PagingAndSortingRepository<TdDeposit, Long>, JpaSpecificationExecutor<TdDeposit>{

	/**
	 * @author dengxiao
	 * 根据停车场ID查找到所有的提现记录
	 */
	List<TdDeposit> findByDiyIdOrderByDepositDateDesc(Long diyId);
}
