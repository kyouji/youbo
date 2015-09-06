<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<#--
<title><#if site??>${site.seoTitle!''}-</#if>${site.company!''}</title>
<meta name="keywords" content="${site.seoKeywords!''}"/>
<meta name="description" content="${site.seoDescription!''}"/>
<meta name="copyright" content="${site.copyright!''}" /> -->
<title>个人中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>


<div class="header">
       <p>个人中心</p>
        <a href="/user" class="a4"></a>
</div>

<div class="main">
    <dl class="mycenter_min">
        <dt>
            <div><img src="<#if user??>${user.headImageUri!''}</#if>"/></div>
            <p>${user.mobile!''}<span>${user.carCode!'渝A·326326'}</span></p>
            <a href="/user/center/info"><img src="/user/images/aboutus_right.png" /></a>
        </dt>
        <dd>
            <a style="color:#ff665e; background:none;">订单</a>
            <a style="color:#69afff; ">预约</a>
        </dd>
    </dl>

    <a>
    <dl class="mycenter_last">
        <dd><a>明细</a></dd>
        <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a href="/user/center/bankcard">银行卡</a></dd>
        <dt><a href="/user/center/bankcard"><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a>停车记录</a></dd>
        <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a href="/user/center/message">消息中心</a></dd>
        <dt><a href="/user/center/message"><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a href="/user/code">我的二维码</a></dd>
        <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a> <a>
    <dl style="margin-top:5%;" class="mycenter_last">
        <dd><a href="/user/center/setting">设置</a></dd>
        <dt><a href="/user/center/setting"><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a href="/user/center/comment">意见反馈</a></dd>
        <dt><a href="/user/center/comment"><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
    <a>
    <dl class="mycenter_last">
        <dd><a href="/user/center/about">关于我们</a></dd>
        <dt><a href="/user/center/about"><img src="/user/images/aboutus_right.png" /></a></dt>
    </dl>
    </a>
 
    
</div><!--main END-->








</body>
</html>
