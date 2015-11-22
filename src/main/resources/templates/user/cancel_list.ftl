<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已取消订单</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
</head>

<body>
    <div class="header">
        <p>已取消订单</p>
        <a href="/user/center" class="a4"></a>
    </div>
    <div class="main">
            <#if cancel_list??&&cancel_list?size gt 0>
                <#list cancel_list as item>
                    <dl class="order_01">
                    <dt>
                        <span>${item.diyTitle!''}</span>
                            <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                <p>共消费：${item.totalPrice?string("0.00")}</p>
                            <#else>
                                <#if item.firstPay??&&item.firstPay gt 0>
                                    <p>支付定金：${item.firstPay?string("0.00")}</p>
                                <#else>
                                    <p>未支付定金</p>
                                </#if>
                            </#if>
                    </dt>
                    <dd>
                        <img src="/user/images/detail_month_01.png" />
                        <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        <span style="float:right;"><a href="/user/order/detail?orderId=${item.id?c}">详情</a></span>
                    </dd>
                    <dd>
                        <img src="/user/images/detail_month_02.png" />
                            <#if item.finishTime??>
                                <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                            <#else>
                                <span>未完成</span>
                            </#if>
                            <p></p>
                        </dd>
                    </dl>
                </#list>
            </#if>
        </div>
    </div>
</body>
</html>
