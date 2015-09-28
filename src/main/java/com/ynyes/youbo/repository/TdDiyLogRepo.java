package com.ynyes.youbo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDiyLog;

public interface TdDiyLogRepo extends PagingAndSortingRepository<TdDiyLog, Long>, JpaSpecificationExecutor<TdDiyLog> {

}
