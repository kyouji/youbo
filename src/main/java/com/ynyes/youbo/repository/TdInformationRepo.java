package com.ynyes.youbo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdInformation;

public interface TdInformationRepo
		extends PagingAndSortingRepository<TdInformation, Long>, JpaSpecificationExecutor<TdInformation> {

	// 根据用户名和消息类型查找到指定用户的某种状态的消息（已读或者是未读）按照时间排序
	List<TdInformation> findByUserIdAndRoleIdAndStatusIdOrUserIdIsNullAndRoleIdAndStatusIdOrderByReleaseTimeDesc(Long userId,Long roleId1,Long statusId1,Long roleId2,Long statusId2);

}
