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
 * 消息
 * @author dengxiao
 */

@Entity
public class TdInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 属于哪个用户的消息(如果username为null，则代表其为指定roleId的全部用户的消息)
	@Column
	private Long userId;

	// 标题
	@Column
	private String title;

	// 显示状态(其值为1代表已读，0代表未读)
	@Column
	private Long statusId;

	// 详细内容
	@Column
	private String content;
	
	// 消息图片，多张图片以,隔开
    @Column
    private String infoPictures;
	
	// 消息类型Id: 1-普通用户 2-停车场
	@Column
	private Long roleId;
	
	// 消息发布时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseTime;
    
    //上级消息
    @Column
    private Long parentId;
    
    //是否具有未读子消息（是则是false，不是则是true）
    @Column
    private Boolean isSubRead;

	public Boolean getIsSubRead() {
		return isSubRead;
	}

	public void setIsSubRead(Boolean isSubRead) {
		this.isSubRead = isSubRead;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInfoPictures() {
		return infoPictures;
	}

	public void setInfoPictures(String infoPictures) {
		this.infoPictures = infoPictures;
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
