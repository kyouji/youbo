package com.ynyes.youbo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.youbo.entity.TdInformation;
import com.ynyes.youbo.repository.TdInformationRepo;

/**
 * TdInformation 服务类
 * 
 * @author dengxiao
 *
 */

@Service
@Transactional
public class TdInformationService {

	@Autowired
	private TdInformationRepo repository;

	/**
	 * @author dengxiao 添加或修改消息的方法
	 */
	public TdInformation save(TdInformation info) {
		return repository.save(info);
	}

	/**
	 * @author dengxiao 删除消息的方法
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * @author dengxiao 查询指定id消息的方法
	 */
	public TdInformation findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	/**
	 * @author dengxiao 查询全部消息的方法
	 */
	public List<TdInformation> findAll() {
		return (List<TdInformation>) repository.findAll();
	}

	/**
	 * @author dengxiao 根据用户名和消息类型查找到指定用户的某种状态的消息按照时间排序
	 */
	public List<TdInformation> findByUserIdAndRoleIdAndStatusId(Long statusId, Long userId) {
		List<TdInformation> someInfo = repository
				.findByUserIdAndRoleIdAndStatusIdOrUserIdIsNullAndRoleIdAndStatusIdOrderByReleaseTimeDesc(userId,1L,statusId,1L,statusId);
		return someInfo;
	}
	
	// 根据用户名获取所有一级消息
	public List<TdInformation> findByUserIdAndParentIdIsNullOrderByReleaseTimeDesc(Long userId){
		if(null == userId){
			return null;
		}
		return repository.findByUserIdAndParentIdIsNullOrderByReleaseTimeDesc(userId);
	};
	
	//根据父id查找消息
	public TdInformation findByParentId(Long parentId){
		if(null == parentId){
			return null;
		}
		return repository.findByParentId(parentId);
	}
}
