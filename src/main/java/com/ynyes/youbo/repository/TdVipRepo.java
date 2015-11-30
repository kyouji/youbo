package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdVip;

public interface TdVipRepo extends PagingAndSortingRepository<TdVip, Long>, JpaSpecificationExecutor<TdVip> {

	//根据停车场id和用户id查找月卡记录
	TdVip findByUserIdAndDiyId(Long userId,Long diyId);
	
	//根据停车场id和车牌号查找月卡记录
	TdVip findByCarCodeAndDiyId(String carCode,Long diyId);
	
	//根据停车场id查找现有的月卡记录
	List<TdVip> findByDiyId(Long diyId);
	
}
