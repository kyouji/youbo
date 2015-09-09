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
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    <!--初始化调用changeOrder()方法使页面只显示全部订单-->
    $(function(){
        changeOrder(1);
    });   
   
    <!--改变顶部订单标签（全部、已完成、未完成）的选中样式的方法-->
    function changeOrder(type){
        if(1 == type){
            $("#all_by_dx").addClass("bespeak_list_style");
            $("#finish_by_dx").removeClass("bespeak_list_style");
            $("#unfinish_by_dx").removeClass("bespeak_list_style");
            
            $("#all_order_by_dx").css("display","block");
            $("#finish_order_by_dx").css("display","none");
            $("#unfinish_order_by_dx").css("display","none")
        }
        
        if(2 == type){
            $("#all_by_dx").removeClass("bespeak_list_style");
            $("#finish_by_dx").addClass("bespeak_list_style");
            $("#unfinish_by_dx").removeClass("bespeak_list_style");
            
            $("#all_order_by_dx").css("display","none");
            $("#finish_order_by_dx").css("display","block");
            $("#unfinish_order_by_dx").css("display","none")
        }
        
        if(3 == type){
            $("#all_by_dx").removeClass("bespeak_list_style");
            $("#finish_by_dx").removeClass("bespeak_list_style");
            $("#unfinish_by_dx").addClass("bespeak_list_style");
            
            $("#all_order_by_dx").css("display","none");
            $("#finish_order_by_dx").css("display","none");
            $("#unfinish_order_by_dx").css("display","block")
        }
    }
</script>
</head>

<body>
    <div class="header">
        <p>订单中心</p>
        <a href="/user" class="a4"></a>
    </div>
    <div class="main">
        <ul class="order_list">
            <li id="all_by_dx" class="bespeak_list_style"><a href="javascript:changeOrder(1);">全部</a></li>
            <li id="finish_by_dx"><a href="javascript:changeOrder(2);">已完成</a></li>
            <li id="unfinish_by_dx"><a href="javascript:changeOrder(3);">未完成</a></li>
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
                                <#if item.finishTime??>
                                    <img src="/user/images/detail_month_02.png" />
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">定金未支付</p>
                                        <#break>
                                        <#case 2>
                                            <p class="order_green">定金已支付</p>
                                        <#break>
                                        <#case 3>
                                            <p class="order_green">预约成功</p>
                                        <#break>
                                        <#case 4>
                                            <p class="order_green">正在停车</p>
                                        <#break>
                                        <#case 5>
                                            <p class="order_green">办理出库</p>
                                        <#break>
                                        <#case 6>
                                            <p>交易完成</p>
                                        <#break>
                                        <#case 7>
                                            <p class="order_red">等待审核</p>
                                        <#break>
                                        <#case 8>
                                            <p class="order_red">审核未通过</p>
                                        <#break>
                                        <#case 9>
                                            <p>交易取消</p>
                                        <#break>
                                    </#switch>
                                </#if>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            
            <#if finish_order_list??&&finish_order_list?size gt 0>
                <div id="finish_order_by_dx">
                    <#list finish_order_list as item>
                        <dl class="order_01">
                            <dt>
                                <span>${item.diyTitle!''}</span>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                </#if>
                            </dt>   
                            <dd>
                                <img src="/user/images/detail_month_01.png" />
                                <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                            </dd>
                            <dd>
                                <img src="/user/images/detail_month_02.png" />
                                <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                <p>交易完成</p>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            
            <div id="unfinish_order_by_dx">
                <#list unfinish_order_list as item>
                    <dl class="order_01">
                        <dt>
                            <span>${item.diyTitle!''}</span>
                            <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                <p>共消费：${item.totalPrice?string("0.00")}</p>
                            <#else>
                                <#if item.statusId??&&item.statusId!=9>
                                    <#if item.firstPay??&&item.firstPay gt 0>
                                        <p>支付定金：${item.firstPay?string("0.00")}</p>
                                    </#if>
                                </#if>
                            </#if>
                        </dt>
                        <dd>
                            <img src="/user/images/detail_month_01.png" />
                            <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        </dd>
                        <dd>
                            <#if item.finishTime??>
                                <img src="/user/images/detail_month_02.png" />
                                <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                            </#if>
                            <#if item.statusId??>
                                <#switch item.statusId>
                                    <#case 1>
                                        <p class="order_red">定金未支付</p>
                                    <#break>
                                    <#case 2>
                                        <p class="order_green">定金已支付</p>
                                    <#break>
                                    <#case 3>
                                        <p class="order_green">预约成功</p>
                                    <#break>
                                    <#case 4>
                                        <p class="order_green">正在停车</p>
                                    <#break>
                                    <#case 5>
                                        <p class="order_green">办理出库</p>
                                    <#break>
                                    <#case 6>
                                        <p>交易完成</p>
                                    <#break>
                                    <#case 7>
                                        <p class="order_red">等待审核</p>
                                    <#break>
                                    <#case 8>
                                        <p class="order_red">审核未通过</p>
                                    <#break>
                                    <#case 9>
                                        <p>交易取消</p>
                                    <#break>
                                </#switch>
                            </#if>
                        </dd>
                    </dl>
                </#list>
            </div>
        </div>
    </div>
</body>
</html>
