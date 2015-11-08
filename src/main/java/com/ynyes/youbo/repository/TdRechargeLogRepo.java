package com.ynyes.youbo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdRechargeLog;

public interface TdRechargeLogRepo
		extends PagingAndSortingRepository<TdRechargeLog, Long>, JpaSpecificationExecutor<TdRechargeLog> {

	TdRechargeLog findByNum(String num);
}
