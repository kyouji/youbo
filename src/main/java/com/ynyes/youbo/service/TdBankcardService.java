package com.ynyes.youbo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.youbo.entity.TdBankcard;
import com.ynyes.youbo.repository.TdBankcardRepo;

/**
 * TdBrandcard 服务类
 * 
 * @author mdj
 *
 */
@Service
@Transactional
public class TdBankcardService {
	@Autowired
	TdBankcardRepo repository;
	
	public void save(TdBankcard e)
	{
		repository.save(e);
	}
	
	public List<TdBankcard> findByDiyId(Long diyId){
		if(null == diyId){
			return null;
		}
		return repository.findBySiteId(diyId);
	}
	
	
}
