<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行卡</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
<div class="header">
        <p>银行卡</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="/user/center/bankcard/add" class="a5">添加</a>
</div>
<div class="main">
    <#if bankcard_list??>
    <#list bankcard_list as item>
    <dl class="bankcard">
        <dt><a>${item.bankName!''}</a></dt>
        <dd>
            <a>${item.bankName!''}</a>
            <p>**** **** **** 8888</p>
            <span>${item.bankName!''}</span>
        </dd>
    </dl>
    </#list>
    </#if>
    
    <dl class="bankcard">
        <dt><a>中国建设银行</a></dt>
        <dd>
            <a>储蓄卡</a>
            <p>**** **** **** 8888</p>
            <span>诸葛亮</span>
        </dd>
    </dl>

    
</div><!--main END-->








</body>
</html>
