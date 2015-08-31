package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDemand;
/**
 * TdDemand  实体数据库操作接口
 * @author Zhangji
 *
 */
public interface TdDemandRepo extends 
           PagingAndSortingRepository<TdDemand, Long>,
           JpaSpecificationExecutor<TdDemand> 
{
   TdDemand findByStatusId(Long statusId);
   List<TdDemand> findByStatusIdOrderByIdDesc(Long statusId);
} 
