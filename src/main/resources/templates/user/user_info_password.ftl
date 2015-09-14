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
    <p>修改密码</p>
    <a href="/user/center/info" class="a4"></a>
    <#--<a class="a5" href="javascript:submitTheForm();">保存</a>-->
</div>

<div class="main">

    <form id="theForm" action="/user/center/password/save" method="post">
        <div class="personal_info_pwd">
            <input class="btn1" name="oldpassword" type="password" datatype="*" nullmsg="请输入原密码！" ajaxurl="/user/center/password/check" placeholder="原密码"/ >
        </div>
        <div class="personal_info_pwd">
            <input class="btn2" name="password"  type="password" datatype="*6-12" nullmsg="请输入新密码！" placeholder="新密码"/ >
        </div>  
        <div class="personal_info_pwd">
            <input class="btn3" name="repassword" type="password" datatype="*" placeholder="确认密码！" recheck="password" nullmsg="请再次输入新密码" / >
        </div>
        <div class="personal_info_pwd">
            <input type="submit" value="保存">
        </div>
    </form>

    
</div><!--main END-->








</body>
</html>
