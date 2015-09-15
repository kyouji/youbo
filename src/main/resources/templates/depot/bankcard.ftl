<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-银行卡</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>银行卡</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="/depot/myaccount/addbankcard" class="a5">添加</a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
        <!--银行卡开始-->
        <#if bankcardList??&&bankcardList?size gt 0>
            <#list bankcardList as item>
                <dl class="bankcard">
                    <dt><a>${item.bankName!''}</a></dt>
                    <dd>
                        <a>${item.bankCardType!''}</a>
                        <p>**** **** **** ${item.cardNumber?substring(item.cardNumber?length-5,item.cardNumber?length-1)}</p>
                        <span>${item.name!''}</span>
                    </dd>
                </dl>
            </#list>
        </#if>
        <!--银行卡结束-->
        <div class="mb98"></div>
    </div>
    
    <!--底部开始-->
   <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2 sel" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/charge">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
</body>
</html>
