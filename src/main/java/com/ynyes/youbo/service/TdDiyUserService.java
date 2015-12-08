package com.ynyes.youbo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.youbo.entity.TdDiyUser;
import com.ynyes.youbo.repository.TdDiyUserRepo;

@Service
@Transactional
public class TdDiyUserService {

	@Autowired
	private TdDiyUserRepo repository;

	public TdDiyUser save(TdDiyUser diyUser) {
		return repository.save(diyUser);
	}

	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	public TdDiyUser findOne(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findOne(id);
	}

	public List<TdDiyUser> findAll() {
		return (List<TdDiyUser>) repository.findAll();
	}

	public TdDiyUser findByUsernameAndPasswordAndIsEnableTrue(String username, String password) {
		if (null == username || null == password) {
			return null;
		}
		return repository.findByUsernameAndPasswordAndIsEnableTrue(username, password);
	}

	public List<TdDiyUser> findByDiyIdAndRoleId(Long diyId) {
		if (null == diyId) {
			return null;
		}
		return repository.findByDiyIdAndRoleId(diyId, 1L);
	}

	public TdDiyUser findByUsername(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsername(username);
	}

	public TdDiyUser findByDiyIdAndRoleId0(Long diyId) {
		if (null == diyId) {
			return null;
		}
		List<TdDiyUser> list = repository.findByDiyIdAndRoleId(diyId, 0L);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
