<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<script src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/depot/js/jquery.cookie.js"></script>

<script type="text/javascript">
$(function(){
    $("#txt_loginId").val($.cookie("diy_username")); 
    
    $("#btn_login").click(function(){
        login();
    });
});
function login(){

    var username = $("#txt_loginId").val();
    var password = $("#txt_loginPwd").val();
    
    $.cookie("diy_username", username, { expires: 90 }); // 存储一个带7天期限的 cookie 
    
    if (username.length < 6 || password.length < 6)
    {
        alert("用户名或密码长度输入不足");
        return;
    }
    $.ajax({
            type: "post",
            url: "/depot/login",
            data: { "username": username, "password": password },
            dataType: "json",
            success: function (data) { 
            <!-- 修改 -->
            if (data.code == 0) {
                    window.location.href = "/depot"; 
            } else {
                alert(data.msg);
            }
    }
});
}

</script>

</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>登录</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--登录开始-->
        <div class="mian">
           <!-- <form  action="/user/center/login" method="post">-->
                <div class="logintext">
                    <input type="text" name="username" id="txt_loginId" class="logintext01" placeholder="用户名" />
                </div>
                <div class="logintext logintext_p">
                    <input type="password" name="password" id="txt_loginPwd" class="logintext02" placeholder="密码" />
                </div>
                <input type="submit" class="loginbtn" id="btn_login" value="登录" />
                <p class="login_a">
                  <!--<a href="#">找回密码</a>-->
                  <!--<a class="a1" href="/depot/register">注册</a>-->
                </p>
           <!-- </form>-->
        </div>
    </div>
  
    <div class="clear h15"></div>
    
    <footer class="mainbox loginbot">
      <a href="#">100%正品保障</a>
      <a href="#">100%正品保障</a>
      <a href="#">100%正品保障</a>
    </footer>
    <!--登录结束-->
    
</body>
</html>
