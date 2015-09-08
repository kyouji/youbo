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
<script src="/user/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){

    $("#btn_login").click(function(){
        alert("预约成功");
    });
});
</script>
</head>

<body>

<div class="header">
        <p>预约选择</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="#" class="a5">帮助</a>
</div>
<div class="main">
    <dl class="bespeak">
        <dt><a>停车场</a><p><#if depot??>${depot.title}</#if></p></dt>
        <dt><a>车牌号</a><p>渝A32632</p></dt>
        <dd><a>请选择日期</a><input type="date" value="" /></dd>
        <dd><a>请选择时间</a><input type="time" value="" /></dd>
        <div class="clear20"></div>
        <dd><a class="bespeak_btn" type="submit" id="submitId">提 交</a></dd>
    </dl>

    
</div><!--main END-->








</body>
</html>
