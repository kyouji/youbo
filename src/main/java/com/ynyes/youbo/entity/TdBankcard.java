package com.ynyes.youbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 银行卡
 * @author mdj
 *
 */

@Entity
public class TdBankcard {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	//持卡人姓名
	@Column
	private String name;
	
	//持卡人身份证号
	@Column
	private String idNumber;
	
	//银行卡号
	@Column
	private String cardNumber;
	
	//银行预留手机号
	@Column
	private String mobile;
	
	//开户银行
	@Column
	private String bankName;
	
	//卡类型
	@Column
	private String bankCardType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	

}
