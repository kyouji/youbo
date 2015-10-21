<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-首页</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
</head>
<script>
    
    <#if currentOrder??>
        <#if currentOrder.statusId==4>
            getOrderPrice();
            setInterval("getOrderPrice()",1000);
        </#if>
        reloadWindow();
        setInterval("reloadWindow()",(1000*60));
    </#if>

    function reloadWindow(){
        var statusId = $("#hidden").val();
        $.post("/user/order/checkOrder",{"statusId":statusId},function(res){
            if(0==res.status){
                window.location.reload();
            }
        });
    }

    function getOrderPrice(){
        var priceSpan = $("#price");
        $.post("/user/order/price",function(res){
            if(0==res.status){
                if(0==res.isNum){
                    priceSpan.html(res.price+".00");
                }else{
                    priceSpan.html(res.price);
                }
            }
        })
    }

    function changeHeads(){
        var filebutton = document.getElementById("filebutton");
        filebutton.click();
    }
    
    function currentOrder(){
        $.post("/user/order/currentOrder",function(res){
            if(1==res.status){
                window.location.href="/user/order/detail?orderId="+res.orderId;
            }else{
                alert(res.message);
                window.location.reload();
            }
        });
    }
    $(function(){
        var u = navigator.userAgent;
        if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
            alert("安卓手机");
        } else if (u.indexOf('iPhone') > -1) {//苹果手机
            alert("苹果手机");
        } else if (u.indexOf('Windows Phone') > -1) {//winphone手机
            alert("winphone手机");
        }
    });
</script>
<body>

    <!--头部开始-->
    <div class="header">
        <p>优泊天下</p>
        <!--
        <video id="video" autoplay="" style='width:640px;height:480px'></video>
        <canvas id="canvas" width="640" height="480"></canvas>
        -->
        <a onclick="changeHeads();" id="picture" class="a1"></a>
        <a href="/user/center/login" class="a3"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--banner开始-->
    <div class="addWrap">
        <div class="swipe" id="mySwipe">
            <div class="swipe-wrap">
            <#if depot_ad_list??>
                <#list depot_ad_list as item>
                    <div><a href="${item.linkUri!'javascript:;'}"><img class="img-responsive" src="${item.fileUri!''}"/></a></div>
                </#list>
            </#if>
            </div>
      </div>
      <ul id="position">
        <li class="cur"></li>
        <#if depot_ad_list??&&depot_ad_list?size gte 2>
                <#list 2..depot_ad_list?size as item>
                    <li class=""></li>
                </#list>
            </#if>
      </ul>
    </div>
    <script src="/depot/js/swipe.js"></script> 
    <script type="text/javascript">
    var bullets = document.getElementById('position').getElementsByTagName('li');
    var banner = Swipe(document.getElementById('mySwipe'), {
        auto: 2000,
        continuous: true,
        disableScroll:false,
        callback: function(pos) {
            var i = bullets.length;
            while (i--) {
              bullets[i].className = ' ';
            }
            bullets[pos].className = 'cur';
        }
    });
    </script>
    <!--banner结束-->
    
    <!--导航开始-->
    <div class="topNav">
        <#if currentOrder??>
            <input type="hidden" id="hidden" value="${currentOrder.statusId!'0'}">
        </#if>
        <a href="/user/find?isOrder=<#if currentOrder??&&currentOrder.statusId!=6&&currentOrder.statusId!=9>true<#else>false</#if>" class="four_1 a1"><img src="/depot/images/index03.png">
            <p><#if currentOrder??&&currentOrder.statusId!=6&&currentOrder.statusId!=9>导航<#else>找车位</#if></p>
        </a>
                <#if currentOrder??>
                    <#switch currentOrder.statusId>
                        <#case 1>
                            <a href="javascript:currentOrder()" class="four_1 a2">
                                <img src="/user/images/my_order.png">
                                <span class="add_price" id="price">${firstPay?string("0.00")}</span>
                                <p>支付定金</p>
                            </a>  
                        <#break>
                        <#case 2>
                            <a href="javascript:currentOrder()" class="four_1 a2">
                                <img src="/user/images/my_order.png">
                                <span class="add_price" id="price">0.00</span>
                                <p>正在预约</p>
                            </a>  
                        <#break>
                        <#case 3>
                            <a href="javascript:currentOrder()" class="four_1 a2">
                                <img src="/user/images/my_order.png">
                                <span class="add_price" id="price">0.00</span>
                                <p>预约成功</p>
                            </a>  
                        <#break>
                        <#case 4>
                            <a href="javascript:currentOrder()" class="four_1 a2">
                                <img src="/user/images/my_order.png">
                                <span class="add_price" id="price"></span>
                                <p>结算订单</p>
                            </a> 
                        <#break>
                        <#default>
                            <a href="javascript:currentOrder()" class="four_1 a2">
                                <img src="/user/images/my_order.png">
                                <span class="add_price" id="price">0.00</span>
                                <p>查看订单</p>
                            </a> 
                        <#break>
                    </#switch>
                <#else>
                    <a href="/user/center/login" class="four_1 a2">
                        <img src="/user/images/my_order.png"">
                        <span class="add_price" id="price">0.00</span>
                        <p>最新订单</p>
                    </a>
                </#if>
        <a href="/user/center" class="four_1 a3"><img src="/depot/images/index01.png"><p>个人中心</p></a>
        <a href="/user/code" class="four_1 a4"><img src="/user/images/index04.png"><p>二维码</p></a>
        <div class="clear"></div>
        <form id="uploadImgForm" enctype="multipart/form-data" action="/user/center/headImg" method="post">
            <input style="display:none" name="Filedata" type="file" onchange="getFile();" id="filebutton">
        </from>
    </div>
    <!--导航结束--
    <div class="mb98"></div>
    </div>
    
    <!--底部开始--
    <div class="footer">
        <a class="a1 sel" href="#">主页</a>
        <a class="a2" href="#">账户</a>
        <a class="a3" href="#">收费记录</a>
        <a class="a4" href="#">车场信息</a>
        <a class="a5" href="#">设置</a>
    </div>
    <!--底部结束-->
</body>
</html>
