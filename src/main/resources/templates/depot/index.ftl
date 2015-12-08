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
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/depot/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    function analysis(){
        $.post("/depot/analysis/qrcode",{},function(){});
    }
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>优泊天下</p>
        <a href="/depot/search" class="a2"></a>
        <a href="/depot/login" class="a3"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--导航开始-->
    <div class="topNav">
        <a class="add_sao" href="javascript:alert('暂未开放！');" style="">扫一扫收费</a>
        <a class="add_sao" href="/depot/myaccount/noCarCode" style="background-color:#BF7EE2">无牌车管理</a>
        <a href="/depot/charge" class="four_1 a4" style="background-color:#00B7BF"><img src="/depot/images/index04.png"><p>订单管理</p></a>
        <a href="/depot/myaccount/reserve" class="four_1 a2"><img src="/depot/images/reserve.png"><p>预约管理</p></a>
        <a href="/depot/myaccount" class="four_1 a1"><img src="/depot/images/index01.png"><p>账 户</p></a>
        <a href="/depot/info" class="four_1 a3"><img src="/depot/images/index03.png"><p>车库信息</p></a>
        <div class="clear"></div>
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
