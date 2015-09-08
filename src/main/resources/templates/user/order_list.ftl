<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
    <div class="header">
        <p>订单中心</p>
        <a href="/user" class="a4"></a>
    </div>
    <div class="main">
        <ul class="order_list">
            <li class="bespeak_list_style"><a>全部</a></li>
            <li><a>已完成</a></li>
            <li><a>已完成</a></li>
        </ul>
        <div>
            <#if all_order_list??&&all_order_list?size gt 0>
                <div id="all_order_by_dx">
                    <#list all_order_list as item>
                        <dl class="order_01">
                            <dt>
                                <span>${item.diyTitle!''}</span>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                <#else>
                                    <#if item.firstPay??&&item.firstPay gt 0>
                                        <p>支付定金：${item.firstPay?string("0.00")}</p>
                                    </#if>
                                </#if>
                            </dt>
                            <dd><img src="/user/images/detail_month_01.png" /><span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span></dd>
                            <dd>
                                <img src="/user/images/detail_month_02.png" />
                                <#if item.finishTime??>
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">定金未支付</p>
                                        <#break>
                                        <#case 2>
                                            <p class="order_red">定金已支付</p>
                                        <#break>
                                        <#case 3>
                                            <p class="order_red">预约成功</p>
                                        <#break>
                                        <#case 4>
                                            <p class="order_red">正在停车</p>
                                        <#break>
                                        <#case 5>
                                            <p class="order_red">办理出库</p>
                                        <#break>
                                        <#case 6>
                                            <p class="order_red">交易完成</p>
                                        <#break>
                                        <#case 7>
                                            <p class="order_red">等待审核</p>
                                        <#break>
                                        <#case 8>
                                            <p class="order_red">审核未通过</p>
                                        <#break>
                                        <#case 9>
                                            <p class="order_red">交易取消</p>
                                        <#break>
                                    </#switch>
                                </#if>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            <div id="finish_oder_by_dx">
            
            </div>
            <div id="unfinish_order_by_dx">
            
            </div>
        </div>
    </div>
</body>
</html>
