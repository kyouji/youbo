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
<script type="text/javascript">
    <!--初始化调用changeOrder()方法使页面只显示全部订单-->
    $(function(){
        changeOrder(4);
    });   
   
    <!--改变顶部订单标签（全部、已完成、未完成）的选中样式的方法-->
    function changeOrder(type){
        if(1 == type){
            $("#cancel_by_dx").addClass("bespeak_list_style");
            $("#false_by_dx").removeClass("bespeak_list_style");
            $("#true_by_dx").removeClass("bespeak_list_style");
            $("#checking_by_dx").removeClass("bespeak_list_style");
            
            
            $("#cancel_order_by_dx").css("display","block");
            $("#checkfalse_order_by_dx").css("display","none");
            $("#checktrue_order_by_dx").css("display","none");
            $("#checking_order_by_dx").css("display","none");
        }
        
        if(2 == type){
            $("#cancel_by_dx").removeClass("bespeak_list_style");
            $("#false_by_dx").addClass("bespeak_list_style");
            $("#true_by_dx").removeClass("bespeak_list_style");
            $("#checking_by_dx").removeClass("bespeak_list_style");
            
            $("#cancel_order_by_dx").css("display","none");
            $("#checkfalse_order_by_dx").css("display","block");
            $("#checktrue_order_by_dx").css("display","none");
            $("#checking_order_by_dx").css("display","none");
        }
        
        if(3 == type){
            $("#cancel_by_dx").removeClass("bespeak_list_style");
            $("#false_by_dx").removeClass("bespeak_list_style");
            $("#true_by_dx").addClass("bespeak_list_style");
            $("#checking_by_dx").removeClass("bespeak_list_style");
            
            $("#cancel_order_by_dx").css("display","none");
            $("#checkfalse_order_by_dx").css("display","none");
            $("#checktrue_order_by_dx").css("display","block");
            $("#checking_order_by_dx").css("display","none");
        }
        if(4 == type){
            $("#cancel_by_dx").removeClass("bespeak_list_style");
            $("#false_by_dx").removeClass("bespeak_list_style");
            $("#true_by_dx").removeClass("bespeak_list_style");
            $("#checking_by_dx").addClass("bespeak_list_style");
            
            $("#cancel_order_by_dx").css("display","none");
            $("#checkfalse_order_by_dx").css("display","none");
            $("#checktrue_order_by_dx").css("display","none");
            $("#checking_order_by_dx").css("display","block");
        }
    }
</script>
</head>

<body>
    <div class="header">
        <p>已取消订单</p>
        <a href="/user/center" class="a4"></a>
    </div>
    <div class="main">
        <ul class="order_list">
            <li id="checking_by_dx" style="width:25%;" class="bespeak_list_style"><a href="javascript:changeOrder(4);">审核中</a></li>
            <li id="true_by_dx" style="width:25%;"><a href="javascript:changeOrder(3);">已通过</a></li>
            <li id="false_by_dx" style="width:25%;"><a href="javascript:changeOrder(2);">未通过</a></li>
            <li id="cancel_by_dx" style="width:25%;"><a href="javascript:changeOrder(1);">全部</a></li>
        </ul>
        <div>
        <#if checking_list??&&checking_list?size gt 0> 
            <div id="checking_order_by_dx">
                <#list checking_list as item>
                    <dl class="order_01">
                        <dt>
                            <span>${item.diyTitle!''}</span>
                            <#if item.statusId??&&item.statusId==9>
                                <p>交易已取消</p>
                            <#else>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                <#else>
                                    <#if item.statusId??&&item.statusId!=9>
                                        <#if item.firstPay??&&item.firstPay gt 0>
                                            <p>支付定金：${item.firstPay?string("0.00")}</p>
                                        <#else>
                                            <p>未支付定金</p>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>
                        </dt>
                        <dd>
                            <img src="/user/images/detail_month_01.png" />
                            <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        </dd>
                        <dd>
                            <img src="/user/images/detail_month_02.png" />
                                <#if item.finishTime??>
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                <#else>
                                    <span>未完成</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">未支付定金</p>
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
                            <dd>
                                <label>审核状态：${item.checkStatus!'审核中'}</label><br>
                                <label>取消原因：${item.cancelReason!'无'}</label><br>
                                <label>客服备注：${item.remarkInfo!'无'}</label>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            
            <#if checktrue_list??&&checktrue_list?size gt 0>
                <div id="checktrue_order_by_dx">
                    <#list checktrue_list as item>
                        <dl class="order_01">
                        <dt>
                            <span>${item.diyTitle!''}</span>
                            <#if item.statusId??&&item.statusId==9>
                                <p>交易已取消</p>
                            <#else>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                <#else>
                                    <#if item.statusId??&&item.statusId!=9>
                                        <#if item.firstPay??&&item.firstPay gt 0>
                                            <p>支付定金：${item.firstPay?string("0.00")}</p>
                                        <#else>
                                            <p>未支付定金</p>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>
                        </dt>
                        <dd>
                            <img src="/user/images/detail_month_01.png" />
                            <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        </dd>
                        <dd>
                            <img src="/user/images/detail_month_02.png" />
                                <#if item.finishTime??>
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                <#else>
                                    <span>未完成</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">未支付定金</p>
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
                            <dd>
                                <label>审核状态：${item.checkStatus!'审核中'}</label><br>
                                <label>取消原因：${item.cancelReason!'无'}</label><br>
                                <label>客服备注：${item.remarkInfo!'无'}</label>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            
            <#if checkfalse_list??&&checkfalse_list?size gt 0>
                <div id="checkfalse_order_by_dx">
                    <#list checkfalse_list as item>
                        <dl class="order_01">
                        <dt>
                            <span>${item.diyTitle!''}</span>
                            <#if item.statusId??&&item.statusId==9>
                                <p>交易已取消</p>
                            <#else>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                <#else>
                                    <#if item.statusId??&&item.statusId!=9>
                                        <#if item.firstPay??&&item.firstPay gt 0>
                                            <p>支付定金：${item.firstPay?string("0.00")}</p>
                                        <#else>
                                            <p>未支付定金</p>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>
                        </dt>
                        <dd>
                            <img src="/user/images/detail_month_01.png" />
                            <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        </dd>
                        <dd>
                            <img src="/user/images/detail_month_02.png" />
                                <#if item.finishTime??>
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                <#else>
                                    <span>未完成</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">未支付定金</p>
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
                            <dd>
                                <label>审核状态：${item.checkStatus!'审核中'}</label><br>
                                <label>取消原因：${item.cancelReason!'无'}</label><br>
                                <label>客服备注：${item.remarkInfo!'无'}</label>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
            
            <#if cancel_list??&&cancel_list?size gt 0>
                <div id="cancel_order_by_dx">
                    <#list cancel_list as item>
                        <dl class="order_01">
                        <dt>
                            <span>${item.diyTitle!''}</span>
                            <#if item.statusId??&&item.statusId==9>
                                <p>交易已取消</p>
                            <#else>
                                <#if item.totalPrice??&&item.totalPrice gt 0>                                    
                                    <p>共消费：${item.totalPrice?string("0.00")}</p>
                                <#else>
                                    <#if item.statusId??&&item.statusId!=9>
                                        <#if item.firstPay??&&item.firstPay gt 0>
                                            <p>支付定金：${item.firstPay?string("0.00")}</p>
                                        <#else>
                                            <p>未支付定金</p>
                                        </#if>
                                    </#if>
                                </#if>
                            </#if>
                        </dt>
                        <dd>
                            <img src="/user/images/detail_month_01.png" />
                            <span>${item.orderTime?string("yyyy-MM-dd HH:mm")}</span>
                        </dd>
                        <dd>
                            <img src="/user/images/detail_month_02.png" />
                                <#if item.finishTime??>
                                    <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                                <#else>
                                    <span>未完成</span>
                                </#if>
                                <#if item.statusId??>
                                    <#switch item.statusId>
                                        <#case 1>
                                            <p class="order_red">未支付定金</p>
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
                            <dd>
                                <label>审核状态：${item.checkStatus!'审核中'}</label><br>
                                <label>取消原因：${item.cancelReason!'无'}</label><br>
                                <label>客服备注：${item.remarkInfo!'无'}</label>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </#if>
        </div>
    </div>
</body>
</html>
