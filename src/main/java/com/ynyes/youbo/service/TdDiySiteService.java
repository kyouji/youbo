package com.ynyes.youbo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.youbo.entity.TdDiySite;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.entity.TdUserComment;
import com.ynyes.youbo.repository.TdDiySiteRepo;

/**
 * TdDiySite 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDiySiteService {
	@Autowired
	TdDiySiteRepo repository;

	@Autowired
	TdUserCommentService TdUserCommentService;

	@Autowired
	TdUserService tdUserService;

	/**
	 * 删除
	 * 
	 * @param id
	 *            菜单项ID
	 */
	public void delete(Long id) {
		if (null != id) {
			repository.delete(id);
		}
	}

	/**
	 * 删除
	 * 
	 * @param e
	 *            菜单项
	 */
	public void delete(TdDiySite e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdDiySite> entities) {
		if (null != entities) {
			repository.delete(entities);
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 *            ID
	 * @return
	 */
	public TdDiySite findOne(Long id) {
		if (null == id) {
			return null;
		}

		return repository.findOne(id);
	}

	/**
	 * 查找
	 * 
	 * @param ids
	 * @return
	 */

	public List<TdDiySite> findAll(Iterable<Long> ids) {
		return (List<TdDiySite>) repository.findAll(ids);
	}

	public List<TdDiySite> findByIsEnableTrue() {
		return repository.findByIsEnableTrue();
	}

	public List<TdDiySite> findByCityAndIsEnableTrueOrderBySortIdAsc(String city) {
		if (null == city) {
			return null;
		}

		return repository.findByCityAndIsEnableTrueOrderBySortIdAsc(city);
	}

	public Page<TdDiySite> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdDiySite> searchAllOrderBySortIdAsc(String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdDiySite save(TdDiySite e) {
//		if (null != e.getUsername()) {
//			TdUser user = tdUserService.findByUsername(e.getUsername());
//
//			if (null == user) {
//				user = tdUserService.addNewUser(e.getUsername(), e.getPassword(), e.getMobile(), null, null);
//
//				user.setRoleId(2L); // 加盟商用户
//			}
//			// 修改加盟店密码也需要修改用户密码 @author: Sharon
//			else {
//				user.setPassword(e.getPassword());
//			}
//
//			tdUserService.save(user);
//		}
		/**
		 * 不添加停车场账户到user表中
		 * @author dengxiao
		 */
		if(null == e){
			return null;
		}
		
		return repository.save(e);
	}

	public List<TdDiySite> save(List<TdDiySite> entities) {

		return (List<TdDiySite>) repository.save(entities);
	}

	/**
	 * @author lc @注释：
	 */
	public TdDiySite findbyUsername(String username) {

		return (repository.findByUsernameAndIsEnableTrue(username));
	}

	/**
	 * @author lc
	 * @注释：通过地区查询同盟店
	 */
	public List<TdDiySite> findBydisctrict(String disctrict) {
		return repository.findByDisctrictAndIsEnableTrue(disctrict);
	}

	/**
	 * @author lc
	 * @注释：同盟店评价信息
	 */
	public int ContdiysiteComment(Long diysiteId) {
		List<TdUserComment> tdUserComment_list = TdUserCommentService.findByDiysiteIdOrderByIdDesc(diysiteId);
		return tdUserComment_list.size();
	}

	public float diysiteServiceStars(Long diysiteId) {
		List<TdUserComment> tdUserComment_list = TdUserCommentService.findByDiysiteIdOrderByIdDesc(diysiteId);

		if (null != tdUserComment_list) {
			Long[] result = new Long[20];
			int temp = 0;
			if (tdUserComment_list.size() == 0) {
				return (float) 0.0;
			}
			for (int i = 0; i < 20; i++) {
				result[i] = tdUserComment_list.get(Math.abs(new Random().nextInt()) % tdUserComment_list.size())
						.getServiceStar();
				temp = (int) (temp + result[i]);
			}
			return temp / 20;
		}

		return (float) 0.0;

	}

	/**
	 * @author dengxiao 
	 * 根据名称查找到指定的停车场信息
	 */
	public TdDiySite findByTitleAndIsEnableTrue(String title) {
		if (null == title) {
			return null;
		}
		return repository.findByTitleAndIsEnableTrue(title);
	};

	/**
	 * @author dengxiao
	 * 根据id查找到指定停车场的信息
	 */
	public TdDiySite findByIdAndIsEnableTrue(Long id){
		if(null == id){
			return null;
		}
		return repository.findByIdAndIsEnableTrue(id);
	}
	
	
	/**
     * @author dengxiao
     * 根据停车场的登陆用户名和密码查找到指定的停车场信息
     */
    public TdDiySite findByUsernameAndPasswordAndIsEnableTrue(String username,String password){
    	if(null == username||null == password){
    		return null;
    	}
    	return repository.findByUsernameAndPasswordAndIsEnableTrue(username, password);
    };
    
    /**
     * @author dengxiao
     * 根据停车场登陆名查找停车场（用于判断是否有此登陆名的停车场）
     */
    public TdDiySite findByUsername(String username){
    	if(null == username){
    		return null;
    	}
    	return repository.findByUsername(username);
    }
    
    public TdDiySite findByUsernameAndIsEnableTrue(String username){
    	if(null == username){
    		return null;
    	}
    	return repository.findByUsernameAndIsEnableTrue(username);
    }
}
