package com.ynyes.youbo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.youbo.entity.TdDeposit;
import com.ynyes.youbo.repository.TdDepositRepo;

@Service
@Transactional
public class TdDepositService {

	@Autowired
	private TdDepositRepo repository;

	public TdDeposit save(TdDeposit e) {
		if (null == e) {
			return null;
		}
		return repository.save(e);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdDeposit findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdDeposit> findAll() {
		return (List<TdDeposit>) repository.findAll();
	}

	/**
	 * @author dengxiao 根据停车场的ID查找所有的提现记录
	 */
	public List<TdDeposit> findByDiyIdAndIsRemitTrueOrderByDepositDateDesc(Long diyId) {
		if (null == diyId) {
			return null;
		}
		return repository.findByDiyIdAndIsRemitTrueOrderByDepositDateDesc(diyId);
	}
	
	public List<TdDeposit> findByDiyIdOrderByDepositDateDesc(Long diyId) {
		if (null == diyId) {
			return null;
		}
		return repository.findByDiyIdOrderByDepositDateDesc(diyId);
	}

	/**
	 * @author dengxiao 根据用户的ID查找所有的提现记录
	 */
	public List<TdDeposit> findByUserIdOrderByDepositDateDesc(Long userId) {
		if (null == userId) {
			return null;
		}
		return repository.findByUserIdOrderByDepositDateDesc(userId);
	}
}
