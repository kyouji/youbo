<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>提现记录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/go.css" rel="stylesheet" type="text/css" />
</head>


<body style="background: #EFEFEF;">


    <!--头部开始-->
    <div class="header">
        <p>提现记录</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <#if deposit_list??>
        <div class="ybtx">
        <!--收费信息开始-->
            <#list deposit_list as item>
            <dl class="detail_month_0111">          
                <dd>
                    <img src="/images/detail_month_01.png" />
                    <span>
                        <#if item.belongBegin??>
                            ${item.belongBegin?string("yyyy-MM-dd")}
                        <#else>
                            <#if site??&&site.registDate??>
                                ${site.registDate?string("yyyy-MM-dd")}
                            </#if>
                        </#if>
                    </span>
                    <p>提现金额</p>
                </dd>
                <dt><span>至</span><p>￥<#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></p></dt>
                <dd>
                    <img src="/images/detail_month_02.png" />
                    <span>
                        <#if item.belongFinish??>
                            ${item.belongFinish?string("yyyy-MM-dd")}
                        </#if>
                    </span>
                    <#if isOperate??>
                        <#if isOperate==true>
                            <p>审核通过</p>
                        <#else>
                            <p>审核未通过</p>
                        </#if>
                    <#else>
                        <p>审核中</p>
                    </#if>
                </dd>
            </dl>
            </#list>
        </div>
    <!--收费信息结束-->
    </#if>
    
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
</html>
