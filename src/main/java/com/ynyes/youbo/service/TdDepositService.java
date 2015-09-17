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
	
	/**
	 * @author dengxiao
	 * 根据停车场的ID查找所有的提现记录
	 */
	public List<TdDeposit> findByDiyIdOrderByDepositDateDesc(Long diyId){
		if(null == diyId){
			return null;
		}
		return repository.findByDiyIdOrderByDepositDateDesc(diyId);
	}
}
