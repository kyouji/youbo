package com.ynyes.youbo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author dengxiao 提取现金
 */
@Entity
public class TdDeposit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 提现单号
	@Column
	private String num;

	// 提现金额
	@Column
	private Double money;

	// 提现时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date depositDate;

	// 属于哪个车库
	@Column
	private Long diyId;

	// 车库名称
	@Column
	private String diyName;

	// 属于哪个用户
	@Column
	private Long userId;

	// 用户名称
	@Column
	private String username;

	// 是否同意
	@Column
	private Boolean isOperate;

	// 处理时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateTime;

	// 是否打款
	@Column
	private Boolean isRemit;

	// 打款时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date remitTime;

	// 备注
	@Column
	private String remark;

	// 提现金额所属起始时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date belongBegin;

	// 提现金额所属结束时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date belongFinish;
	
	public Date getBelongBegin() {
		return belongBegin;
	}

	public void setBelongBegin(Date belongBegin) {
		this.belongBegin = belongBegin;
	}

	public Date getBelongFinish() {
		return belongFinish;
	}

	public void setBelongFinish(Date belongFinish) {
		this.belongFinish = belongFinish;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDiyName() {
		return diyName;
	}

	public void setDiyName(String diyName) {
		this.diyName = diyName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(Boolean isOperate) {
		this.isOperate = isOperate;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Boolean getIsRemit() {
		return isRemit;
	}

	public void setIsRemit(Boolean isRemit) {
		this.isRemit = isRemit;
	}

	public Date getRemitTime() {
		return remitTime;
	}

	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public Long getDiyId() {
		return diyId;
	}

	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}

}
