package com.ynyes.youbo.util;

import java.util.Date;

import com.ynyes.youbo.entity.TdDiySite;

public class DiySiteFee {

	public static final Double GET_PARKING_PRICE(TdDiySite site, Date beginDate, Date finishDate) {
		Double price = new Double(0.00);
		// 获取开始时间和结束时间的毫秒值
		long beginTime = beginDate.getTime();
		long finishTime = finishDate.getTime();
		// 获取停车天数
		long costDay = (finishTime - beginTime) / (24 * 60 * 60 * 1000);

		long costHour = (finishTime - beginTime) % (24 * 60 * 60 * 1000) / (1000 * 60 * 60);

		long costMinute = (finishTime - beginTime) % (24 * 60 * 60 * 1000) % (1000 * 60 * 60) / (1000 * 60);

		// 计算停车场天数价格
		price += (costDay * site.getMaxPrice());

		// 不足一个小时的时间按照一个小时来计算
		if (costMinute > 0) {
			costHour = costHour + 1;
		}

		//计算剩余价格
		if (costHour <= site.getDayBaseTime()) {
			price += site.getDayBasePrice();
		} else {
			Long priceTime = costHour - site.getDayBaseTime();
			Double costPrice = site.getDayHourPrice() * priceTime;
			price += costPrice;
		}

		return price;
	}
}
