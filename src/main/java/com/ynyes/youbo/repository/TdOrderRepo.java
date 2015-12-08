package com.ynyes.youbo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.youbo.entity.TdOrder;

/**
 * TdOrder 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderRepo extends PagingAndSortingRepository<TdOrder, Long>, JpaSpecificationExecutor<TdOrder> {
	Page<TdOrder> findByStatusIdOrderByIdDesc(Long statusId, Pageable page);

	Page<TdOrder> findByUsernameOrderByIdDesc(String username, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterOrderByIdDesc(String username, Date time, Pageable page);

	Page<TdOrder> findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username, Date time,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndOrderNumberContainingOrderByIdDesc(String username, String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdOrderByIdDesc(String username, Long statusId, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(String username, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(String username, Long statusId, Date time,
			Pageable page);

	Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username,
			Long statusId, Date time, String keywords, Pageable page);

	Long countByUsernameAndStatusId(String username, Long statusId);

	TdOrder findByOrderNumber(String orderNumber);

	/**
	 * @author lichong
	 * @注释：同盟店订单查询
	 */
	Page<TdOrder> findByshopTitleOrderByIdDesc(String diystiename, Pageable page);

	Page<TdOrder> findByshopTitleAndOrderNumberContainingOrderByIdDesc(String diystiename, String keywords,
			Pageable page);

	Page<TdOrder> findByshopTitleAndStatusIdAndOrderNumberContainingOrderByIdDesc(String diystiename, Long statusId,
			String keywords, Pageable page);

	Page<TdOrder> findByshopTitleAndStatusIdOrderByIdDesc(String diystiename, Long statusId, Pageable page);

	Page<TdOrder> findByshopTitleAndOrderTimeAfterOrderByIdDesc(String diystiename, Date time, Pageable page);

	Page<TdOrder> findByshopTitleAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String diystiename,
			Long statusId, Date time, String keywords, Pageable page);

	Page<TdOrder> findByshopTitleAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String diystiename, Date time,
			String keywords, Pageable page);

	Page<TdOrder> findByshopTitleAndStatusIdAndOrderTimeAfterOrderByIdDesc(String diystiename, Long statusId, Date time,
			Pageable page);

	/**
	 * @author lc
	 * @注释：同盟店信息查询
	 */
	List<TdOrder> findByShopIdAndStatusIdOrderByIdDesc(long shopId, long statusId);

	/**
	 * @author lc
	 * @注释：同盟店订单收入查询
	 */
	List<TdOrder> findByStatusIdAndShopTitleOrStatusIdAndShopTitle(Long statusId, String diystiename, Long statusId1,
			String diystiename1);

	Page<TdOrder> findByStatusIdAndShopTitleOrStatusIdAndShopTitleOrderByIdDesc(Long statusId, String diystiename,
			Long statusId1, String diystiename1, Pageable page);

	List<TdOrder> findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(
			Long statusId, String diystiename, Date time, Long statusId1, String diystiename1, Date time1);

	Page<TdOrder> findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(
			Long statusId, String diystiename, Date time, Long statusId1, String diystiename1, Date time1,
			Pageable page);

	/**
	 * @author lc
	 * @注释：同盟店返利收入
	 */
	List<TdOrder> findByUsernameIn(List<String> tdUsers);

	Page<TdOrder> findByUsernameInOrderByIdDesc(List<String> tdUsers, Pageable page);

	List<TdOrder> findByUsernameInAndOrderTimeAfterOrderByIdDesc(List<String> tdUsers, Date time);

	Page<TdOrder> findByUsernameInAndOrderTimeAfterOrderByIdDesc(List<String> tdUsers, Date time, Pageable page);

	/**
	 * @author lc
	 * @注释：按订单类型和状态查询
	 */
	Page<TdOrder> findByStatusIdAndTypeIdOrderByIdDesc(long statusId, long typeId, Pageable page);

	List<TdOrder> findByStatusIdAndTypeIdOrderByIdDesc(long statusId, long typeId);

	Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, Pageable page);

	List<TdOrder> findByStatusIdOrderByIdDesc(long statusId);

	Page<TdOrder> findBytypeIdOrderByIdDesc(long typeId, Pageable page);

	List<TdOrder> findBytypeIdOrderByIdDesc(long typeId);

	/**
	 * @author lc @注释： 按时间、订单类型和订单状态查询
	 */
	Page<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time, Pageable page);

	List<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time);

	Page<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time,
			Pageable page);

	List<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time);

	Page<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time, Pageable page);

	List<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time);

	Page<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time, Pageable page);

	List<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time);

	/**
	 * 按交易状态查询
	 * 
	 * @author libiao
	 */
	List<TdOrder> findByStatusId(Long statusId);
	// List<TdOrder> findAll();

	/**
	 * @author dengxiao 根据电话号码和交易状态查找订单（不分页）
	 */

	// 查找指定用户的所有订单
	List<TdOrder> findByUsernameOrderByOrderTimeDesc(String username);

	// 查找指定用户已经完成的订单
	List<TdOrder> findByUsernameAndStatusIdOrderByOrderTimeDesc(String username, Long statusId);

	// 查找指定用户未完成的订单
	List<TdOrder> findByUsernameAndStatusIdNotOrderByOrderTimeDesc(String username, Long statusId);

	// 根据车牌号码查找订单
	TdOrder findByCarCode(String carCode);

	// 根据停车场的id和订单id查找订单
	TdOrder findByDiyIdAndId(Long diyId, Long orderId);

	// 根据车牌号码停车场id订单状态（状态为3，预约成功）查找一系列订单信息，按照时间倒序排序，选择第一个（第一个即是指定用户在指定停车场预约成功的最后一个订单）
	List<TdOrder> findByCarCodeAndDiyIdAndStatusIdOrderByOrderTimeDesc(String carCode, Long diyId, Long statusId);

	List<TdOrder> findByUsernameAndFinishTimeBetweenAndStatusIdOrderByOrderTimeAsc(String username, Date beginDate,
			Date finishDate, Long statusId);

	// 查找指定用户还未停车或已停车的订单
	List<TdOrder> findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByOrderTimeDesc(
			String username1, Long statusId1, String username2, Long statusId2, String username3, Long statusId3);

	// 查找指定用户审核通过的订单
	List<TdOrder> findByUsernameAndCheckStatusAndStatusIdOrderByOrderTimeDesc(String username, String checkStatus,
			Long statusId);

	// 查找指定用户审核未通过的订单
	List<TdOrder> findByUsernameAndCheckStatusOrderByOrderTimeDesc(String username, String checkStatus);

	// 查找所有申请退费的订单
	List<TdOrder> findByDiyIdAndCheckStatusOrDiyIdAndCheckStatusOrDiyIdAndCheckStatusOrderByOrderTimeDesc(Long id1,
			String checkStatus1, Long id2, String checkStatus2, Long id3, String checkStatus3);

	// 查找处于某种审核状态的订单
	List<TdOrder> findByDiyIdAndCheckStatusOrderByOrderTimeDesc(Long id, String checkStatus);

	// 根据停车场的ID和下单时间查找订单
	List<TdOrder> findByDiyIdAndFinishTimeBetween(Long id, Date beginDate, Date finishDate);

	// 查找指定停车场未支付的订单
	List<TdOrder> findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotOrderByOrderTimeDesc(Long id,
			Long statusId1, Long statusId2, Long statusId3, Long statusId4);

	// 查找指定停车场指定时间段的未支付订单
	List<TdOrder> findByDiyIdAndStatusIdNotAndStatusIdNotAndStatusIdNotAndStatusIdNotAndFinishTimeBetweenOrderByOrderTimeDesc(
			Long id, Long statusId1, Long statusId2, Long statusId3, Long statusId4, Date beginDate, Date finishDate);

	// 查找指定停车场已支付的订单
	List<TdOrder> findByDiyIdAndStatusIdOrderByOrderTimeDesc(Long id, Long statusId);

	// 查找指定停车场指定时间段内已支付的订单
	List<TdOrder> findByDiyIdAndStatusIdAndFinishTimeBetweenOrderByOrderTimeDesc(Long id, Long statusId, Date beginDate,
			Date finishDate);

	List<TdOrder> findByUsernameAndFinishTimeBetweenOrderByOrderTimeAsc(String username, Date beginDate,
			Date finishDate);

	// 根据订单的支付方式查找订单
	List<TdOrder> findByThePayTypeAndStatusIdAndDiyIdOrderByOrderTimeDesc(Long thePayType, Long statusId, Long diyId);

	// 根据订单的支付方式查找一定时间段的订单
	List<TdOrder> findByThePayTypeAndStatusIdAndDiyIdAndOrderTimeBetweenOrderByOrderTimeDesc(Long thePayType,
			Long statusId, Long diyId, Date beginTime, Date finishTime);

	// 根据订单的支付方式查找一定时间段的订单
	List<TdOrder> findByThePayTypeAndStatusIdAndDiyIdAndFinishTimeBetweenOrderByOrderTimeDesc(Long thePayType,
			Long statusId, Long diyId, Date beginTime, Date finishTime);

	// 查找违约订单
	List<TdOrder> findByStatusIdAndDiyIdOrderByOrderTimeDesc(Long statusId, Long diyId);

	// 查找一定时间内的违约订单
	List<TdOrder> findByStatusIdAndDiyIdAndOrderTimeBetweenOrderByOrderTimeDesc(Long statusId, Long diyId,
			Date beginTime, Date finishTime);

	List<TdOrder> findByStatusIdAndDiyIdAndFinishTimeBetweenOrderByOrderTimeDesc(Long statusId, Long diyId,
			Date beginTime, Date finishTime);

	// 根据车牌号码和停车场ID模糊查询订单
	List<TdOrder> findByDiyIdAndCarCodeContainingOrderByOrderTimeDesc(Long diyId, String keywords);

	// 查找指定停车场正在停车的订单
	List<TdOrder> findBydiyIdAndStatusId(Long diyId, Long statusId);

	// 查找到违约的订单
	List<TdOrder> findByDiyIdAndStatusIdAndFirstPayGreaterThanAndTotalPrice(Long diyId, Long statusId,
			Double firstPrice, Double totalPrice);

	// 查找指定停车场内已缴费但未出库的订单
	List<TdOrder> findByDiyIdAndStatusIdAndOutputTimeIsNull(Long diyId, Long statusId);

	// 根据停车场ID和车牌号码和订单状态查找订单
	List<TdOrder> findByDiyIdAndStatusIdAndCarCode(Long diyId, Long statusId, String carCode);

	// 根据停车场ID和车牌号和订单状态和出库时间为NULL查找订单
	List<TdOrder> findByDiyIdAndStatusIdAndCarCodeAndOutputTimeIsNullOrderByOrderTimeDesc(Long diyId, Long statusId,
			String carCode);

	List<TdOrder> findByDiyIdAndStatusIdAndOutputTimeIsNullAndIsOvertime(Long diyId, Long statusId, Boolean isOverTime);

	List<TdOrder> findByDiyIdAndStatusIdAndIsSendReserveFalse(Long diyId, Long statusId);

	List<TdOrder> findByDiyIdAndStatusIdAndIsCancelTrue(Long diyId, Long statusId);

	Page<TdOrder> findByStatusIdOrderByOrderTimeDesc(Long statusId, Pageable page);

	Page<TdOrder> findByStatusIdAndOrderNumberContainingOrStatusIdAndUsernameContainingOrStatusIdAndDiyTitleOrderByOrderTimeDesc(
			Long statusId01, String keywords01, Long statusId02, String keywords02, Long statusId03, String keywords03,
			Pageable page);

	Page<TdOrder> findByOrderNumberContainingOrUsernameContainingOrDiyTitleContainingOrderByOrderTimeDesc(
			String keywords01, String keywords02, String keywords03, Pageable page);

	List<TdOrder> findByStatusIdAndCarCode(Long statusId, String carCode);

	List<TdOrder> findByStatusIdAndCarCodeContainingAndDiyIdOrderByOrderTimeDesc(Long statusId, String carCode,
			Long diyId);

	// 根据停车场id订单状态和支付方法查找不超过指定时间的订单
	List<TdOrder> findByDiyIdAndFinishTimeBefore(Long diyId, Date finish);
	
}
