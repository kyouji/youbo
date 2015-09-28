package com.ynyes.youbo.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDiyUser;

public interface TdDiyUserRepo
		extends PagingAndSortingRepository<TdDiyUser, Long>, JpaSpecificationExecutor<TdDiyUser> {

	//根据账号和密码查找用户
	TdDiyUser findByUsernameAndPasswordAndIsEnableTrue(String username,String password);
	
}
