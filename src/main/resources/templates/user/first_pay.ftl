<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

    <header class="header">
        <p>支付定金</p>
        <a href="javascript:history.back(-1);" class="a4"></a>
    </header>
    
    <div class="main">
        <dl class="pay_min">
            <dt><span><#if order.diyTitle??>${order.diyTitle!''}</#if></span></dt>
            <dd><span>生成时间 </span><p><#if order.orderTime??>${order.orderTime?string("yyyy-MM-dd HH:mm")}</#if></p></dd>
            <dd><span>车牌号码  </span><p><#if order.carcode??>${order.carcode!''}</#if></p></dd>   
        </dl>
    
        <dl style="margin-bottom:5%;" class="pay_min_01">
            <dt><span>支付定金  </span><p>￥10.00</p></dt>    
        </dl>
    
        <div class="pay_way">请选择支付方式</div>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon01.png" /><a>支付宝</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon02.png" /><a>微信</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon03.png" /><a>银行卡</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
        
    </div><!--main END-->

</body>
</html>
