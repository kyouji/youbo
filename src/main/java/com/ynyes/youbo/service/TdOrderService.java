package com.ynyes.youbo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.youbo.entity.TdOrder;
import com.ynyes.youbo.entity.TdUser;
import com.ynyes.youbo.repository.TdOrderRepo;

import scala.xml.dtd.PublicID;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderService {
	@Autowired
	TdOrderRepo repository;

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
	public void delete(TdOrder e) {
		if (null != e) {
			repository.delete(e);
		}
	}

	public void delete(List<TdOrder> entities) {
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
	public TdOrder findOne(Long id) {
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
	public List<TdOrder> findAll(Iterable<Long> ids) {
		return (List<TdOrder>) repository.findAll(ids);
	}

	public List<TdOrder> findAll() {
		return (List<TdOrder>) repository.findAll();
	}

	public Page<TdOrder> findAllOrderBySortIdAsc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));

		return repository.findAll(pageRequest);
	}

	public Page<TdOrder> findAllOrderByIdDesc(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));

		return repository.findAll(pageRequest);
	}

	public Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByStatusIdOrderByIdDesc(statusId, pageRequest);
	}

	public Page<TdOrder> findByUsername(String username, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameOrderByIdDesc(username, pageRequest);
	}

	public TdOrder findByOrderNumber(String orderNumber) {
		return repository.findByOrderNumber(orderNumber);
	}

	public Page<TdOrder> findByUsernameAndTimeAfter(String username, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterOrderByIdDesc(username, time, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndTimeAfterAndSearch(String username, Date time, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, time, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndSearch(String username, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndOrderNumberContainingOrderByIdDesc(username, keywords, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusId(String username, long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdOrderByIdDesc(username, statusId, pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndSearch(String username, long statusId, String keywords, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(username, statusId, keywords,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfter(String username, long statusId, Date time, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(username, statusId, time,
				pageRequest);
	}

	public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfterAndSearch(String username, long statusId, Date time,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username,
				statusId, time, keywords, pageRequest);
	}

	public Long countByUsernameAndStatusId(String username, long statusId) {
		return repository.countByUsernameAndStatusId(username, statusId);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 * @return
	 */
	public TdOrder save(TdOrder e) {

		return repository.save(e);
	}

	public List<TdOrder> save(List<TdOrder> entities) {

		return (List<TdOrder>) repository.save(entities);
	}

	/**
	 * @author lichong @注释：
	 */
	public Page<TdOrder> findByDiysitename(String diysitename, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleOrderByIdDesc(diysitename, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndSearch(String diysitename, String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndOrderNumberContainingOrderByIdDesc(diysitename, keywords, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndStatusIdAndSearch(String diysitename, long statusId, String keywords,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndStatusIdAndOrderNumberContainingOrderByIdDesc(diysitename, statusId,
				keywords, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndStatusId(String diysitename, long statusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndStatusIdOrderByIdDesc(diysitename, statusId, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndTimeAfter(String diysitename, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndOrderTimeAfterOrderByIdDesc(diysitename, time, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndTimeAfterAndSearch(String diysitename, Date time, String keywords,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(diysitename, time,
				keywords, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndStatusIdAndTimeAfterAndSearch(String diysitename, long statusId, Date time,
			String keywords, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(diysitename,
				statusId, time, keywords, pageRequest);
	}

	public Page<TdOrder> findByDiysitenameAndStatusIdAndTimeAfter(String diysitename, long statusId, Date time,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByshopTitleAndStatusIdAndOrderTimeAfterOrderByIdDesc(diysitename, statusId, time,
				pageRequest);
	}

	/**
	 * @author lc
	 * @注释：线下同盟店信息
	 */
	public List<TdOrder> findByshopIdAndstatusId(long shopId, long statusId) {
		return repository.findByShopIdAndStatusIdOrderByIdDesc(shopId, statusId);
	}

	/**
	 * @author lc
	 * @注释：订单收入查询
	 */
	public List<TdOrder> findAllVerifyBelongShopTitle(String diysitename) {
		return repository.findByStatusIdAndShopTitleOrStatusIdAndShopTitle(5L, diysitename, 6L, diysitename);
	}

	public Page<TdOrder> findAllVerifyBelongShopTitleOrderByIdDesc(String diysitename, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndShopTitleOrStatusIdAndShopTitleOrderByIdDesc(5L, diysitename, 6L,
				diysitename, pageRequest);
	}

	public List<TdOrder> findAllVerifyBelongShopTitleAndTimeAfter(String diysitename, Date time) {
		return repository
				.findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(5L,
						diysitename, time, 6L, diysitename, time);
	}

	public Page<TdOrder> findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(String diysitename, Date time, int page,
			int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository
				.findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(5L,
						diysitename, time, 6L, diysitename, time, pageRequest);
	}

	/**
	 * @author lc
	 * @注释：订单返利查询
	 */
	public List<TdOrder> findByUsernameIn(List<String> tdUsers) {
		return repository.findByUsernameIn(tdUsers);
	}

	public Page<TdOrder> findByUsernameIn(List<String> tdUsers, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameInOrderByIdDesc(tdUsers, pageRequest);
	}

	public List<TdOrder> findByUsernameInAndOrderTimeAfter(List<String> tdUsers, Date time) {
		return repository.findByUsernameInAndOrderTimeAfterOrderByIdDesc(tdUsers, time);
	}

	public Page<TdOrder> findByUsernameInAndOrderTimeAfter(List<String> tdUsers, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameInAndOrderTimeAfterOrderByIdDesc(tdUsers, time, pageRequest);
	}

	/**
	 * @author lc
	 * @注释：根据订单类型和订单状态进行查询
	 */
	public Page<TdOrder> findByStatusAndTypeOrderByIdDesc(long statusId, long typeId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndTypeIdOrderByIdDesc(statusId, typeId, pageRequest);
	}

	public List<TdOrder> findByStatusAndTypeIdOrderByIdDesc(long statusId, long typeId) {
		return repository.findByStatusIdAndTypeIdOrderByIdDesc(statusId, typeId);
	}

	public Page<TdOrder> findByStatusOrderByIdDesc(long StatusId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdOrderByIdDesc(StatusId, pageRequest);
	}

	public List<TdOrder> findByStatusOrderByIdDesc(long StatusId) {
		return repository.findByStatusIdOrderByIdDesc(StatusId);
	}

	public Page<TdOrder> findBytypeIdOrderByIdDesc(long typeId, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBytypeIdOrderByIdDesc(typeId, pageRequest);
	}

	public List<TdOrder> findBytypeIdOrderByIdDesc(long typeId) {
		return repository.findBytypeIdOrderByIdDesc(typeId);
	}

	/**
	 * @author lc
	 * @注释 按时间、订单类型和订单状态查询
	 */
	public Page<TdOrder> findByTimeAfterOrderByIdDesc(Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);

		return repository.findByOrderTimeAfterOrderByIdDesc(time, pageRequest);
	}

	public List<TdOrder> findByTimeAfterOrderByIdDesc(Date time) {
		return repository.findByOrderTimeAfterOrderByIdDesc(time);
	}

	public Page<TdOrder> findByStatusIdAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time,
			int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time, pageRequest);
	}

	public List<TdOrder> findByStatusAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time) {
		return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time);
	}

	public Page<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time, pageRequest);
	}

	public List<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time) {
		return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time);
	}

	public Page<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time, int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time, pageRequest);
	}

	public List<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time) {
		return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time);
	}

	/**
	 * 按交易状态查询
	 * 
	 * @author libiao
	 */
	public List<TdOrder> findByStatusId(Long statusId) {
		return repository.findByStatusId(statusId);
	}

	public List<TdOrder> findAll(Long statusId) {
		return (List<TdOrder>) repository.findAll();
	}

	/**
	 * @author dengxiao 根据电话号码和交易状态查找订单（不分页）
	 */

	// 查找指定用户的所有订单
	public List<TdOrder> findByUsername(String username) {
		return repository.findByUsernameOrderByOrderTimeDesc(username);
	};

	// 查找指定用户已经完成的订单
	public List<TdOrder> findByUsernameAndStatusId(String username) {
		return repository.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, 6L);
	};

	// 查找指定用户未完成的订单
	public List<TdOrder> findByUsernameAndStatusIdNot(String username) {
		return repository.findByUsernameAndStatusIdNotOrderByOrderTimeDesc(username, 6L);
	};

	// 根据车牌号码查找订单信息
	public TdOrder findByCarCode(String carCode) {
		if (null == carCode) {
			return null;
		}
		return repository.findByCarCode(carCode);
	}

	// 根据订单id和停车场id查找指定订单的信息
	public TdOrder findByDiyIdAndId(Long diyId, Long orderId) {
		if (null == diyId || null == orderId) {
			return null;
		}
		return repository.findByDiyIdAndId(diyId, orderId);
	}

	// 根据车牌号码停车场id订单状态（状态为3，预约成功）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场预约成功的最后一个订单）
	public TdOrder findTopByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(String carCode, Long diyId) {
		if (null == carCode || null == diyId) {
			return null;
		}
		List<TdOrder> list = repository.findByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(carCode, diyId, 3L);
		if(null != list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	};
	
	//根据车牌号码和停车场id查找状态为4的订单按照时间倒序排序选择第一个
	public TdOrder findbyStatusFour(String carCode,Long diyId){
		if (null == carCode || null == diyId) {
			return null;
		}
		List<TdOrder> list = repository.findByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(carCode, diyId, 4L);
		if(null != list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<TdOrder> findByUsernameAndFinishTimeBetweenAndStatusId(String username, Date beginDate,
			Date finishDate) {
		if (null == username || null == beginDate || null == finishDate) {
			return null;
		}
		return repository.findByUsernameAndFinishTimeBetweenAndStatusIdOrderByOrderTimeAsc(username, beginDate,
				finishDate, 6L);
	}

	// 查找指定用户已停车的订单
	public List<TdOrder> findByUsernameAndParked(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByOrderTimeDesc(
				username, 4L, username, 5L, username, 6L);
	}

	// 查找指定用户未停车的订单
	public List<TdOrder> findByUsernameAndNotParked(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByOrderTimeDesc(
				username, 1L, username, 2L, username, 3L);
	}

	// 查找指定用户等待审核的订单
	public List<TdOrder> findCheckingOrder(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, 7L);
	}

	// 查找指定用户审核通过的订单
	public List<TdOrder> findCheckTrueOrder(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndCheckStatusAndStatusIdOrderByOrderTimeDesc(username, "审核通过", 9L);
	}

	// 查找指定用户审核未通过的订单
	public List<TdOrder> findCheckFlaseOrder(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndCheckStatusOrderByOrderTimeDesc(username, "审核未通过");
	}

	// 查找指定用户所有已取消的订单
	public List<TdOrder> findByCancelOrder(String username) {
		if (null == username) {
			return null;
		}
		return repository.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByOrderTimeDesc(
				username, 7L, username, 8L, username, 9L);
	}

	// 查找指定停车场的所有被申请退款的订单
	public List<TdOrder> findByIdAndRefund(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndCheckStatusOrDiyIdAndCheckStatusOrDiyIdAndCheckStatusOrderByOrderTimeDesc(id,
				"待审核", id, "审核通过", id, "审核未通过");
	}

	// 查找指定停车场正在审核中的退款订单
	public List<TdOrder> findChecking(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndCheckStatusOrderByOrderTimeDesc(id, "待审核");
	}

	// 查找指定停车场审核通过的退款订单
	public List<TdOrder> findCheckedTrue(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndCheckStatusOrderByOrderTimeDesc(id, "审核通过");
	}

	// 查找指定停车场审核未通过的退款订单
	public List<TdOrder> findCheckedFalse(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndCheckStatusOrderByOrderTimeDesc(id, "审核未通过");
	}

	// 根据停车场ID查找在某个时间段之内的所有订单
	public List<TdOrder> findByDiyIdAndOrderTimeBetween(Long id, Date beginDate, Date finishDate) {
		if (null == id || null == beginDate || null == finishDate) {
			return null;
		}
		return repository.findByDiyIdAndOrderTimeBetween(id, beginDate, finishDate);
	}

	// 查找指定停车场未支付的订单
	public List<TdOrder> findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotOrderByOrderTimeDesc(
			Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotOrderByOrderTimeDesc(id,
				6L, 7L, 8L, 9L);
	};

	// 查找指定停车场指定时间段的未支付订单
	public List<TdOrder> findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotAndOrderTimeBetweenOrderByOrderTimeDesc(
			Long id, Date beginDate, Date finishDate) {
		if (null == id || null == beginDate || null == finishDate) {
			return null;
		}
		return repository
				.findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotAndOrderTimeBetweenOrderByOrderTimeDesc(
						id, 6L, 7L, 8L, 9L, beginDate, finishDate);
	};

	// 查找指定停车场已支付的订单
	public List<TdOrder> findByDiyIdAndStatusIdOrderByOrderTimeDesc(Long id) {
		if (null == id) {
			return null;
		}
		return repository.findByDiyIdAndStatusIdOrderByOrderTimeDesc(id, 6L);
	};

	// 查找指定停车场指定时间段内已支付的订单
	public List<TdOrder> findByDiyIdAndStatusIdAndOrderTimeBetweenOrderByOrderTimeDesc(Long id, Date beginDate,
			Date finishDate) {
		if (null == id || null == beginDate || null == finishDate) {
			return null;
		}
		return repository.findByDiyIdAndStatusIdAndOrderTimeBetweenOrderByOrderTimeDesc(id, 6L, beginDate, finishDate);
	};
	
	//查找指定停车场指定时间段内已预约的订单
	public List<TdOrder> findReservedOrder(Long id,Date beginDate,Date finishDate){
		if(null == id || null == beginDate || null == finishDate){
			return null;
		}
		return repository.findByDiyIdAndStatusIdAndOrderTimeBetweenOrderByOrderTimeDesc(id, 3L, beginDate, finishDate);
	}
	
	//查找指定用户已经取消的订单
	public List<TdOrder> findCancelOrderByUsername(String username){
		if(null == username){
			return null;
		}
		return repository.findByUsernameAndStatusIdOrderByOrderTimeDesc(username, 9L);
	}
	
	//根据停车场ID查找预约审核的订单
	public List<TdOrder> findByDiyIdAndStatusIdOrderByOrderTime(Long diyId){
		if(null == diyId){
			return null;
		}
		return repository.findByDiyIdAndStatusIdOrderByOrderTimeDesc(diyId, 2L);
	}

}
