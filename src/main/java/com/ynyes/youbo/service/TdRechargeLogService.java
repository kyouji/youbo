package com.ynyes.youbo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.youbo.entity.TdRechargeLog;
import com.ynyes.youbo.repository.TdRechargeLogRepo;

@Service
@Transactional
public class TdRechargeLogService {

	@Autowired
	TdRechargeLogRepo repository;
	
	public TdRechargeLog save(TdRechargeLog e){
		if(null == e){
			return null;
		}
		return repository.save(e);
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdRechargeLog findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdRechargeLog> findAll(){
		return (List<TdRechargeLog>) repository.findAll();
	}
	
	public TdRechargeLog findByNum(String num){
		if(null == num){
			return null;
		}
		return repository.findByNum(num);
	}
}
