package com.ynyes.youbo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ynyes.youbo.entity.TdDiyLog;
import com.ynyes.youbo.repository.TdDiyLogRepo;

@Controller
@Transactional
public class TdDiyLogService {

	@Autowired
	private TdDiyLogRepo repository;
	
	public TdDiyLog save(TdDiyLog log){
		if(null == log){
			return log;
		}
		return repository.save(log);
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdDiyLog findOne(Long id){
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public List<TdDiyLog> findAll(){
		return (List<TdDiyLog>) repository.findAll();
	}
}
