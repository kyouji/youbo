<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改用户名</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/user/js/Validform_v5.3.2_min.js"></script>
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    $(document).ready(function(){
        $("#theForm").Validform({
            tiptype: 3
         });
    });
    
</script>
</head>

<body>

<div class="header">
    <p><#if payPassword??>修改支付密码<#else>设置支付密码</#if></p>
    <a href="/user/center/info" class="a4"></a>
    <#--<a class="a5" href="javascript:submitTheForm();">保存</a>-->
</div>

<div class="main">

    <form id="theForm" action="/user/center/pay/save" method="post">
        <#if payPassword??>
            <div class="personal_info_pwd">
                <input class="btn1" name="oldpassword" type="password" datatype="*" nullmsg="请输入原支付密码！" ajaxurl="/user/center/pay/check" placeholder="原支付密码"/ >
            </div>
            <div class="personal_info_pwd">
                <input class="btn2" name="password"  type="password" datatype="*6-12" nullmsg="请输入新支付密码！" placeholder="新支付密码"/ >
            </div>  
            <div class="personal_info_pwd">
                <input class="btn3" name="repassword" type="password" datatype="*" placeholder="确认支付密码" recheck="password" nullmsg="请再次输入新支付密码" / >
            </div>
            <div class="personal_info_pwd">
                <input type="submit" value="保存">
            </div>
        <#else>
            <div class="personal_info_pwd">
                <input class="btn2" name="password"  type="password" datatype="*6-6" nullmsg="请输入支付密码！" errormsg="请输入6位任意字符！" placeholder="支付密码"/ >
            </div>  
            <div class="personal_info_pwd">
                <input class="btn3" name="repassword" type="password" datatype="*" placeholder="确认支付密码" recheck="password" nullmsg="请再次输入支付密码" / >
            </div>
            <div class="personal_info_pwd">
                <input type="submit" value="保存">
            </div>
        </#if>
    </form>

    
</div><!--main END-->








</body>
</html>
