package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdDiyUser;

public interface TdDiyUserRepo
		extends PagingAndSortingRepository<TdDiyUser, Long>, JpaSpecificationExecutor<TdDiyUser> {

	// 根据账号和密码查找用户
	TdDiyUser findByUsernameAndPasswordAndIsEnableTrue(String username, String password);

	// 根据停车场ID和角色ID查找子账号
	List<TdDiyUser> findByDiyIdAndRoleId(Long diyId, Long roleId);
	
	//根据用户名查找用户
	TdDiyUser findByUsername(String username);
	
}
