<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约选择</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
<!--
$(function(){

    $("#btn_login").click(function(){
        alert("预约成功");
    });
});
-->

    function startReserve(username,diyId){
        $.post("/user/find/reserve",{"username":username,"diyId":diyId},function(res){
            alert(res.message);
    <#--
                在此处需要进行多种监控：
                1. 在预约成功的情况下，监控指定车辆是否在两个小时之内进入指定的车库（即可以审查订单状态是否在两个小时之内改变为4L）
                2. 在等待泊车员手动确认预约的情况下，随时监控订单是否已经预约成功
    -->
        });
    } 
</script>
</head>

<body>

<div class="header">
    <p>预约选择</p>
    <a href="javascript:history.back(-1);" class="a4"></a>
    <a href="#" class="a5">帮助</a>
</div>
<div class="main">
    <dl class="bespeak">
        <dt><div style="color:#f65741;">定金</div><p style="color:#f65741;">${firstPay?string("0.00")}元</p></dt>
        <dt><div>用户</div><p><#if user??>${user.username!''}</#if></p></dt>
        <dt><a href="/user/find/diy/detail?id=${depot.id?c}"><div >停车场</div><span><img src="/user/images/setting_guide.png" /></span><p style="margin-right:22px;"><#if depot??>${depot.title!''}</#if></p></a></dt>
        <div class="clear20"></div>
        <dd><a class="bespeak_btn" type="button" id="submitId" onClick="startReserve('${user.username!''}',${depot.id?c});">预约</a></dd>
    </dl>
</div><!--main END-->








</body>
</html>
