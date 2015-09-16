package com.ynyes.youbo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author dengxiao 
 * 提取现金
 */
@Entity
public class TdDeposit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//提现单号
	@Column
	private String num;
	
	//提现金额
	@Column
	private Double money;
	
	//提现时间
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date depositDate;
	
	//属于哪个车库
	@Column
	private Long diyId;

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
