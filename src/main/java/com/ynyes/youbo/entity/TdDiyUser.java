package com.ynyes.youbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 停车场用户
 * 
 * @author dengxiao
 *
 */
@Entity
public class TdDiyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 泊车员真实姓名
	@Column
	private String realName;

	@Column
	private String username;

	@Column
	private String password;

	// 其值为0代表车库老板，1代表泊车员
	@Column
	private Long roleId;

	// 停车场ID
	@Column
	private Long diyId;

	// 是否使能
	@Column
	private Boolean isEnable;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDiyId() {
		return diyId;
	}

	public void setDiyId(Long diyId) {
		this.diyId = diyId;
	}

	@Override
	public String toString() {
		return "TdDiyUser [id=" + id + ", realName=" + realName + ", username=" + username + ", password=" + password
				+ ", roleId=" + roleId + ", diyId=" + diyId + ", isEnable=" + isEnable + "]";
	}

	
}
