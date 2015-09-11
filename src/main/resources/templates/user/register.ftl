<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-注册</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/user/css/regist.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/user/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/user/js/user_regist.js"></script>
<script>
<!--初始化表单验证工具-->
$(document).ready(function(){
    changeYzm();
    $("#regist_form").Validform({
        tiptype: 3,
     });
});
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>注册</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="#" class="a3"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--注册开始-->
        <div class="mian">
            <form action="/user/reg/save" method="post" id="regist_form">
                <div class="logintext">         
                    <input name="username" id="username" type="text" datatype="m" ajaxurl="/user/reg/check/username" nullmsg="请输入手机号码！" errormsg="手机号码格式不正确！" class="logintext01" placeholder="手机号" />
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div class="logintext">         
                    <input name="password" id="password" type="password" datatype="*6-12" nullmsg="请输入密码！" errormsg="密码的长度不能小于6位或大于12位！" class="logintext01" placeholder="用户密码" />
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div class="logintext">         
                    <input name="repassword" id="repassword" datatype="*" recheck="password" nullmsg="请再次输入密码！" errormsg="两次输入的密码不一致！" type="password" class="logintext01" placeholder="重复密码" />
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div class="logintext logintext_y">
                    <input name="code" id="code" type="text" datatype="*" ajaxurl="/user/reg/check/yzm" nullmsg="请输入验证码！" errormsg="验证码错误！" class="logintext02" placeholder="输入验证码" />
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <a href="javascript:changeYzm();" class="login_yzm"><img id="yzm_image" src="" /></a>
                <div class="clear"></div>
                <input type="submit" class="loginbtn" value="注册" />
                <p class="login_a">
                    <a href="#">注册即表示您同意<span class="red">《用户协议》</span></a>
                </p>
            </form>
        </div>
    </div>
      
    <div class="clear h15"></div>
      
    <footer class="mainbox loginbot">
        <a href="#">100%正品保障</a>
        <a href="#">100%正品保障</a>
        <a href="#">100%正品保障</a>
    </footer>
    <!--注册结束-->

</body>
</html>
