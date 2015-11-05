<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>月份明细</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

    <div class="header">
        <p>明细</p>
        <a href="javascript:history.back(-1);" class="a4"></a>
    </div>
    
    <div class="main">
        <ul class="detail_month">
            <li><span><#if year??>${year?c}</#if>年<#if month??>${month}</#if>月所有已完成订单</span></li>
            <li class="detail_month_style"><span><#if totalPrice??>${totalPrice?string("0.00")}</#if></span></li>
        </ul>
        <#if orders??>
            <#list orders as item>
                <dl class="detail_month_01">
                    <dt><span>${item.diyTitle!''}</span>
                        <p>
                            <#if item.totalPrice??>
                                ${item.totalPrice?string("0.00")}
                            <#else>
                                <#if item.firstPay??>
                                   ${item.firstPay?string("0.00")} 
                                </#if>
                            </#if>
                        </p>
                    </dt>
                    <dd>
                        <img src="/user/images/detail_month_01.png" />
                        <span><#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm")}</#if></span>
                        <span style="float:right;"><a href="/user/order/detail?orderId=${item.id?c}">详情</a></span>
                    </dd>
                    <dd>
                        <img src="/user/images/detail_month_02.png" />
                        <span><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd HH:mm")}</#if></span>
                        <#if item.statusId==9>
                            <p>交易取消</p>
                        <#elseif item.statusId==6>
                            <p>交易完成</p>
                        </#if>
                    </dd>
                </dl>
            </#list>
        </#if>
    </div><!--main END-->

</body>
</html>
