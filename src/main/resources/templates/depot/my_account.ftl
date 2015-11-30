<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-我的账户</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
<script>
    
</script>
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>我的账户</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--我的账户开始-->
    <dl class="my_account">
        <dt>
            <img src="/depot/images/money.png"/>
            <p class="p1">当日累计收费</p>
            <p class="p2">￥<span><#if income??>${income?string("0.00")!''}元<#else>0.00元</#if></span></p>
        </dt>
    </dl>
    
    <#if diyUser??&&diyUser.roleId??>
        <#if diyUser.roleId==0>
            <a class="ip_1" type="button" style="ali" href="/depot/myaccount/withdrawal">我的钱包</a>
        <#else>
            <a class="ip_1" type="button" style="ali;background-color:#999999" >我的钱包</a>
        </#if>
        
    </#if>
    
    <div class="my_account_record">
        <#if diyUser??&&diyUser.roleId??>
            <#if diyUser.roleId==0>
                <a href="/depot/myaccount/cashrecord">
                    <dl>
                        <dd>提现记录</dd>
                        <dt><img src="/depot/images/advance.png" /></dt>
                    </dl>
                </a>
            </#if>
        </#if>
        <a href="/depot/myaccount/tradeDetail">
            <dl>
                <dd>交易明细</dd>
                <dt><img src="/depot/images/advance.png" /></dt>
            </dl>
        </a>
        <a href="/depot/charge">
            <dl>
                <dd>订单管理</dd>
                <dt><img src="/depot/images/advance.png" /></dt>
            </dl>
        </a>
        <a href="/depot/myaccount/reserve">
            <dl>
                <dd>预约审核</dd>
                <dt><img src="/depot/images/advance.png" /></dt>
            </dl>
        </a>
        <a href="/depot/myaccount/vip/carcode">
            <dl>
                <dd>月卡车辆</dd>
                <dt><img src="/depot/images/advance.png" /></dt>
            </dl>
        </a>
        <a href="/depot/myaccount/noCarCode">
            <dl>
                <dd>无牌车管理</dd>
                <dt><img src="/depot/images/advance.png" /></dt>
            </dl>
        </a>
        <#--
        <#if diyUser??&&diyUser.roleId??>
            <#if diyUser.roleId==0>
                <dl>
                    <dd><a href="/depot/myaccount/bankcard">银行卡</a></dd>
                    <dt><a href="/depot/myaccount//bankcard"><img src="/depot/images/advance.png" /></a></dt>
                </dl>
            </#if>
        </#if>
        <dl>
            <dd><a href="/depot/myaccount/message">消息中心</a></dd>
            <dt><a href="/depot/myaccount/message"><img src="/depot/images/advance.png" /></a></dt>
        </dl>
        -->
        <#if diyUser??&&diyUser.roleId??>
            <#if diyUser.roleId==0>
                <a href="/depot/myaccount/subAccount">
                    <dl>
                        <dd>子账户管理</dd>
                        <dt><img src="/depot/images/advance.png" /></dt>
                    </dl>
                </a>
            </#if>
        </#if>
    </div>
    <!--我的账户结束-->

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
