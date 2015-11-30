package com.ynyes.youbo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.youbo.entity.TdVip;
import com.ynyes.youbo.repository.TdVipRepo;

@Service
@Transactional
public class TdVipService {

	@Autowired
	private TdVipRepo repository;

	public TdVip save(TdVip e) {
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

	public TdVip findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdVip> findAll() {
		return (List<TdVip>) repository.findAll();
	}

	/**
	 * @author dengxiao 根据用户id和停车场id查找月卡记录
	 */
	public TdVip findByUserIdAndDiyId(Long userId, Long diyId) {
		if (null == userId || null == diyId) {
			return null;
		}
		return repository.findByUserIdAndDiyId(userId, diyId);
	}

	/**
	 * @author dengxiao 根据车牌号和停车场id查找月卡记录
	 */
	public TdVip findByCarCodeAndDiyId(String carCode, Long diyId) {
		if (null == carCode || null == diyId) {
			return null;
		}
		return repository.findByCarCodeAndDiyId(carCode, diyId);
	}
	
	/**
	 * @author dengxiao 根据停车场id查找月卡记录
	 */
	public List<TdVip> findByDiyId(Long diyId){
		if(null == diyId){
			return null;
		}
		return repository.findByDiyId(diyId);
	}

}
