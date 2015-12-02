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
<script type="text/javascript">
    function navigation(x,y){
        var u = navigator.userAgent;
        if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
            window.location.href = "androidamap://navi?sourceApplication=amap&lat="+x+"&lon="+y+"&dev=1&style=2";
            alert("启动导航功能前请确认已经安装了“高德地图”APP");
        } else if (u.indexOf('iPhone') > -1) {//苹果手机
            alert("启动导航功能前请确认已经安装了“高德地图”APP");
            window.location.href = "iosamap://navi?sourceApplication=applicationName&lat="+x+"&lon="+y+"&dev=1&style=2";
        } else{//winphone手机
            alert("只有ios和android系统的手机才能够实现导航功能！");
        }
    }
</script>
</head>

<body>


    <div class="header">
        <p>个人中心</p>
        <a href="/user" class="a4"></a>
    </div>

<div class="main">
    <dl class="mycenter_min">
        <dt>
            <div><img src="<#if user??>${user.headImageUri!'/user/images/default_head.png'}</#if>" width="100px" height="100px"/></div>
            <p>
                <#if user.balance??&&user.balance gt 0>
                    <span>余额：${user.balance?string("0.00")}元</span>
                <#else>
                    <span>余额：0元</span>
                </#if>
                <span>${user.carCode!''}</span>
            </p>
            <a href="/user/center/info"><img src="/user/images/aboutus_right.png" /></a>
        </dt>
        <dd>
            <a style="color:#ff665e; background:none;" href="/user/order">我的订单</a>
            <a style="color:#69afff; " href="<#if currentOrder??&&currentOrder.statusId!=6&&currentOrder.statusId!=9>javascript:navigation(${x!'0'},${y!'0'});<#else>/user/find</#if>"><#if currentOrder??&&currentOrder.statusId!=6&&currentOrder.statusId!=9>车位导航<#else>寻找车位</#if></a>
        </dd>
    </dl>
        <div class="mycenter_title" style="background-color:#f65741;">
            <a class="blue-color" <#if user.payPassword??>href="/user/center/deposit"<#else>href="javascript:alert('请设置提现密码！');window.location.href='/user/center/info/pay'"</#if> style="color:#fff;text-align:center;">提现</a>
            <a href="/user/center/recharge" style="color:#fff;text-align:center;">充值</a>
        </div>
    
    <div style="height:15px;"></div>  
    <a href="/user/center/info">
        <dl class="mycenter_last">
            <dd>用户资料</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a href="/user/center/detail">
        <dl class="mycenter_last">
            <dd>明细</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a href="/user/center/cashrecord">
        <dl class="mycenter_last">
            <dd>提现记录</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a  href="/user/center/message">
        <dl class="mycenter_last">
            <dd>消息中心</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <#--
    <a href="/user/order/cancel">
        <dl class="mycenter_last">
            <dd>已取消订单</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    -->
    <a  href="/user/center/comment">
        <dl class="mycenter_last">
            <dd>意见反馈</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <#--
    <a>
        <dl class="mycenter_last">
            <dd><a href="/user/center/about">关于我们</a></dd>
            <dt><a href="/user/center/about"><img src="/user/images/aboutus_right.png" /></a></dt>
        </dl>
    </a>
    <a>
        <dl class="mycenter_last">
            <dd><a href="/user/code">我的二维码</a></dd>
            <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
        </dl>
    </a>
    -->
    <a <#if setting??>href="tel://${setting.telephone!''}"</#if>>
        <dl class="mycenter_last">
            <dd>联系我们</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a href="/user/center/frost">
        <dl class="mycenter_last">
            <dd><#if user??&&user.isFrost??&&user.isFrost==true>解除冻结<#else>冻结余额</#if></dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a href="javascript:alert('暂未开放！')">
        <dl class="mycenter_last">
            <dd>我的车位共享</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
    <a href="/user/center/exit">
        <dl class="mycenter_last">
            <dd>退出登录</dd>
            <dt><img src="/user/images/aboutus_right.png" /></dt>
        </dl>
    </a>
 
    
</div><!--main END-->








</body>
</html>
