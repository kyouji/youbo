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
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<link href="/user/css/go.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/user/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/user/js/user_regist.js"></script>
</head>
<script type="text/javascript">
    window.onload = function(){
        $('.img_obox a').height($('.img_obox a').width()*0.56)
    };
    $(document).ready(function(){
        changeYzm();
        $("#regist_form").Validform({
            datatype:{
                "carCode":function(gets,obj,curform,regxp){
                      var reg = /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/,
                      carCode = document.getElementById("username").value;
                      
                      if(reg.test(carCode)){
                        return true; 
                      }
                      return false;
                }
            },
            tiptype: 3,
         });
    });
</script>
<body style="background: white;">
    <!--头部开始-->
    <div class="header">
        <p>注册</p>
        <a href="javascript:history.back(-1);" class="a4"></a>
        <a href="#" class="rich_title"></a>
    </div>
    <!--头部结束-->
    <div class="rich01">
        <form action="/user/reg/save" method="post" id="regist_form">
            <dl>
                <dd>
                    <input name="username" id="username" type="text" datatype="carCode" ajaxurl="/user/reg/check/username" nullmsg="请输入车牌号码！" errormsg="车牌号码格式不正确！" placeholder="车牌号码"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </dd>
                <dd>
                    <input name="password" id="password" type="password" datatype="*6-12" nullmsg="请输入密码！" errormsg="密码的长度不能小于6位或大于12位！" class="logintext01" placeholder="用户密码" placeholder="登陆密码"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </dd>
                <dd>
                    <input name="repassword" id="repassword" datatype="*" recheck="password" nullmsg="请再次输入密码！" errormsg="两次输入的密码不一致！" type="password" placeholder="确认密码"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </dd>
                <dd>
                    <input type="text" name="mobile" id="mobile" datatype="m" placeholder="手机号码"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </dd>
                <dd>
                    <input name="code" id="code" type="text" datatype="*" ajaxurl="/user/reg/check/yzm" nullmsg="请输入验证码！" errormsg="验证码错误！" placeholder="验证码"/>
                    <a href="javascript:changeYzm();" style="background:none;margin:0px;"><img id="yzm_image" src="" /></a>
                    <span class="Validform_checktip Validform_wrong"></span>
                </dd>
                <dd>
                    <input type="" name="" id="" placeholder="手机验证码" />
                    <a>发送</a>
                    <span class="Validform_checktip Validform_wrong"></span>             
                </dd>
            </dl>
            <div class="rich_sure01">
                <input type="submit" value="注册" />
            </div>
        </form>
    </div>

   

    <div class="mb98"></div>
    
    </div>
 
    <!--底部结束-->
    
</body>
</html>
