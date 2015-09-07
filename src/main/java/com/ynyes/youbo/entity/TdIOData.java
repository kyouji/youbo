package com.ynyes.youbo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 进出口数据
 * 
 * @author Sharon
 *
 */

@Entity
public class TdIOData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 卡号
    @Column
    private String cardNo;
    
    // 车牌号
    @Column
    private String busNo;
    
    // 用户
    @Column
    private String owner;
    
    // 时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ioTime;
    
    // 出入状态
    @Column
    private String ioState;
    
    // 门ID
    @Column
    private String doorID;
    
    // 出入标志
    @Column
    private Integer inOut;
    
    // 是否修正
    @Column
    private Boolean fixed;
    
    // 图片
    @Column
    private String picture;
    
    // 照片
    @Column
    private String photo;
    
    // 用户类型
    @Column
    private String userTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getIoTime() {
        return ioTime;
    }

    public void setIoTime(Date ioTime) {
        this.ioTime = ioTime;
    }

    public String getIoState() {
        return ioState;
    }

    public void setIoState(String ioState) {
        this.ioState = ioState;
    }

    public String getDoorID() {
        return doorID;
    }

    public void setDoorID(String doorID) {
        this.doorID = doorID;
    }

    public Integer getInOut() {
        return inOut;
    }

    public void setInOut(Integer inOut) {
        this.inOut = inOut;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
