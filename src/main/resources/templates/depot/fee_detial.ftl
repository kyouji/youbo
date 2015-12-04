<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>明细</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />

<link href="/depot/css/go.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="js/rich_lee.js"></script>
</head>


<body style="background: #EFEFEF;">
<div class="header">
        <p>月份明细</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--设置开始-->
    <dl class="rich_get">
        <dd>
            <a>
                <p style="color:green;">总收入</p>
                <span>￥<#if totalPrice??>${totalPrice?string("0.00")}<#else>0.00</#if></span>
            </a>
            <a>
                <p style="color:green;">线上</p>
                <span>￥<#if allXs??>${allXs?string("0.00")}<#else>0.00</#if></span>
            </a>
            <a>
                <p style="color:green;">现金</p>
                <span>￥<#if allXj??>${allXj?string("0.00")}<#else>0.00</#if></span>
            </a>
            <a>
                <p style="color:green;">免单</p>
                <span>${mdNum!'0'}笔</span>
            </a>
            <a>
                <p style="color:green;">违约</p>
                <span>￥<#if allWy??>${allWy?string("0.00")}<#else>0.00</#if></span>
            </a>
            <#if days??>
                <#list 1..days as item>
                    <a href="/depot/myaccount/chargeManage?year=${year?c}&month=${month}&day=${item}">
                        <p>${item}日</p>
                        <span>￥<#if totalPrice??>${("income"+item)?eval?string("0.00")}</#if></span>
                    </a>
                </#list>
            </#if>
        </dd>
    </dl>
    <!--设置结束-->

    <div class="mb98"></div>
        
    </div>
    
    <!--底部开始-->
    <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2 sel" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
    
</body>
    
</body>
</html>
