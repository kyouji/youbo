package com.ynyes.youbo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 停车场
 * 
 * @author Sharon
 *
 */

@Entity
public class TdDiySite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 停车场名称
    @Column
    private String title;
    
    // 银行卡
 	@OneToMany
 	@JoinColumn(name="siteId")
 	private List<TdBankcard> bankcardList;
    
	// 停车场详细地址
    @Column
    private String address;
    
    // 营业时间
    @Column
    private String openTimeSpan;
    
    // 客服电话
    @Column
    private String serviceTele;
    
    // 省份
    @Column
    private String province;
    
    // 城市
    @Column
    private String city;
    
    // 区
    @Column
    private String disctrict;
    
    // 是否启用
    @Column
    private Boolean isEnable;
    
    // 排序数字
    @Column
    private Long sortId;
    
    // 经度
    @Column
    private Double longitude;
    
    // 纬度
    @Column
    private Double latitude;
    
    // 描述说明
    @Column
    private String info;
    
    // 封面图片
    @Column
    private String imageUri;
    
    // 轮播展示图片，多张图片以,隔开
    @Column
    private String showPictures;
    
    // 登录名
    @Column
    private String username;
    
    // 登录密码
    @Column
    private String password;
    
    // 手机号
    @Column
    private String mobile;
    
    // 车库类型
    @Column
    private String parkingType;
    
    // 车位分类
    @Column
    private String parkingClassification;
    
    // 车位个数
    @Column
    private Integer parkingTotalNumber;
    
    // 车位预约个数
    @Column
    private Integer parkingRefundNumber;
    
    // 车位剩余个数
    @Column
    private Integer parkingNowNumber;
    
    //提现密码
    @Column
    private String depositPassword;
    
    //该停车场所有的收入
    @Column
    private Double allMoney;
    
    //判断停车场是否拥有摄像设备
    @Column
    private Boolean isCamera;
    
    //停车场每日收费最高价格
    @Column(scale=2)
    private Double maxPrice;

    //白天收费类型(0代表计时收费，1代表按次收费)
    @Column
    private Long dayType;
    
    //晚上收费类型(0代表计时收费，1代表按次收费)
    @Column
    private Long nightType;
    
    //白天按次收费的价格
    @Column(scale=2)
    private Double dayOncePrice;
    
    //白天计时收费的基础时间(表示前几个小时)
    @Column
    private Long dayBaseTime;
    
    //白天计时收费的基础价格
    @Column(scale=2)
    private Double dayBasePrice;
    
    //白天计时收费的计时价格
    @Column(scale=2)
    private Double dayHourPrice;
    
    //晚上按次收费的价格
    @Column(scale=2)
    private Double nightOncePrice;
    
    //晚上计时收费的基础时间（表示前几个小时）
    @Column
    private Long nightBaseTime;
    
    //晚上计时收费的基础价格
    @Column(scale=2)
    private Double nightBasePrice;
    
    //晚上计时收费的计时价格
    @Column(scale=2)
    private Double nightHourPrice;
    
    //注册时间
    @Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
    private Date registDate;
    
	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Long getDayType() {
		return dayType;
	}

	public void setDayType(Long dayType) {
		this.dayType = dayType;
	}

	public Long getNightType() {
		return nightType;
	}

	public void setNightType(Long nightType) {
		this.nightType = nightType;
	}

	public Double getDayOncePrice() {
		return dayOncePrice;
	}

	public void setDayOncePrice(Double dayOncePrice) {
		this.dayOncePrice = dayOncePrice;
	}

	public Long getDayBaseTime() {
		return dayBaseTime;
	}

	public void setDayBaseTime(Long dayBaseTime) {
		this.dayBaseTime = dayBaseTime;
	}

	public Double getDayBasePrice() {
		return dayBasePrice;
	}

	public void setDayBasePrice(Double dayBasePrice) {
		this.dayBasePrice = dayBasePrice;
	}

	public Double getDayHourPrice() {
		return dayHourPrice;
	}

	public void setDayHourPrice(Double dayHourPrice) {
		this.dayHourPrice = dayHourPrice;
	}

	public Double getNightOncePrice() {
		return nightOncePrice;
	}

	public void setNightOncePrice(Double nightOncePrice) {
		this.nightOncePrice = nightOncePrice;
	}

	public Long getNightBaseTime() {
		return nightBaseTime;
	}

	public void setNightBaseTime(Long nightBaseTime) {
		this.nightBaseTime = nightBaseTime;
	}

	public Double getNightBasePrice() {
		return nightBasePrice;
	}

	public void setNightBasePrice(Double nightBasePrice) {
		this.nightBasePrice = nightBasePrice;
	}

	public Double getNightHourPrice() {
		return nightHourPrice;
	}

	public void setNightHourPrice(Double nightHourPrice) {
		this.nightHourPrice = nightHourPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Boolean getIsCamera() {
		return isCamera;
	}

	public void setIsCamera(Boolean isCamera) {
		this.isCamera = isCamera;
	}

	public Double getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	public String getDepositPassword() {
		return depositPassword;
	}

	public void setDepositPassword(String depositPassword) {
		this.depositPassword = depositPassword;
	}

	public List<TdBankcard> getBankcardList() {
		return bankcardList;
	}

	public void setBankcardList(List<TdBankcard> bankcardList) {
		this.bankcardList = bankcardList;
	}

    public Integer getParkingTotalNumber() {
		return parkingTotalNumber;
	}

	public void setParkingTotalNumber(Integer parkingTotalNumber) {
		this.parkingTotalNumber = parkingTotalNumber;
	}

	public Integer getParkingRefundNumber() {
		return parkingRefundNumber;
	}

	public void setParkingRefundNumber(Integer parkingRefundNumber) {
		this.parkingRefundNumber = parkingRefundNumber;
	}

	public Integer getParkingNowNumber() {
		return parkingNowNumber;
	}

	public void setParkingNowNumber(Integer parkingNowNumber) {
		this.parkingNowNumber = parkingNowNumber;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTimeSpan() {
        return openTimeSpan;
    }

    public void setOpenTimeSpan(String openTimeSpan) {
        this.openTimeSpan = openTimeSpan;
    }

    public String getServiceTele() {
        return serviceTele;
    }

    public void setServiceTele(String serviceTele) {
        this.serviceTele = serviceTele;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisctrict() {
        return disctrict;
    }

    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getShowPictures() {
        return showPictures;
    }

    public void setShowPictures(String showPictures) {
        this.showPictures = showPictures;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public String getParkingClassification() {
        return parkingClassification;
    }

    public void setParkingClassification(String parkingClassification) {
        this.parkingClassification = parkingClassification;
    }
}
