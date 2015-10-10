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
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/user/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
    var usernameIsSubmit = true;
    var passwordIsSubmit = true;
    var repasswordIsSubmit = true;
    var realnameIsSubmit = true;
    
    function checkUsername(){
        var check = document.getElementById("username_span");
        var username = document.getElementById("username").value;
        if(username == ""){
            check.innerHTML="用户名不能为空！";
            usernameIsSubmit = false;
            return;
        }
        
        if(username.length<6||username.length>12){
            check.innerHTML = "用户名的长度为6-12位！";
            usernameIsSubmit = false;
            return;
        }
        
        <#if !theUser??>
            $.post("/depot/myaccount/checkSubAccount",{"username":username},function(res){
                if(-1 == res.status){
                    check.innerHTML = res.message;
                    usernameIsSubmit = false;
                    return;
                }
            });
        </#if>
        
        usernameIsSubmit = true;
        check.innerHTML = "";
    }
    
    function checkPassword(){
        var check = document.getElementById("password_span");
        var password = document.getElementById("password").value;
        if(password == ""){
            check.innerHTML = "密码不能为空!";
            passwordIsSubmit = false;
            return;
        }
        
        if(password.length<6 || password.length > 20){
            check.innerHTML = "密码的长度为6-20位！";
            passwordIsSubmit = false;
            return;
        }
        
        passwordIsSubmit = true;
        check.innerHTML = "";
    }
    
    function checkRepassword(){
        var check = document.getElementById("repassword_span");
        var password = document.getElementById("password").value;
        var repassword = document.getElementById("repassword").value;
        if(password!=repassword){
            check.innerHTML = "两次输入的密码不一致！";
            repasswordIsSubmit = false;
            return;
        }
        
        repasswordIsSubmit = true;
        check.innerHTML = "";
    }
    
    function checkRealname(){
        var check = document.getElementById("realname_span");
        var realname = document.getElementById("realname").value;
        if("" == realname){
            check.innerHTML = "真实姓名不能为空！";
            realnameIsSubmit = false;
            return;
        }
        
        realnameIsSubmit = true;
        check.innerHTML = "";
    }
    
    function submitTheForm(){
        checkUsername();
        checkPassword();
        checkRepassword();
        checkRealname();
        
        if(usernameIsSubmit == true&&passwordIsSubmit==true&&repasswordIsSubmit==true&&realnameIsSubmit==true){
            document.getElementById("form1").submit();
        }
        
    }
    
</script>
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>添加子账户</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--银行卡开始-->
        <div class="bank">
            <form id="form1" action="/depot/myaccount/editSubAccount" method="post">
                <#if theUser??>
					<input type="hidden" name="id" value="${theUser.id?c}" />
                </#if>
                <input class="bank_02" onBlur="checkUsername();" <#if theUser??>value="${theUser.username!''}" disabled="true"</#if> id="username" name="username" placeholder="账号" />
                <span id="username_span" style="color:#f65741"></span>
                
                <input class="bank_03" id="password" onBlur="checkPassword();" type="password" <#if theUser??>value="${theUser.password!''}"</#if> name="password" placeholder="密码"  />
                <span id="password_span" style="color:#f65741"></span>
                
                <input class="bank_05" id="repassword" name="repassword" type="password" <#if theUser??>value="${theUser.password!''}"</#if>  onBlur="checkRepassword();" placeholder="重复密码"  />
                <span id="repassword_span" style="color:#f65741"></span>
                
                <input class="bank_06" id="realname" name="realname" onBlur="checkRealname();"<#if theUser??>value="${theUser.realName!''}"</#if> placeholder="真实姓名"  />
                <span id="realname_span" style="color:#f65741"></span>
                
                <p style="margin-top:20px;font-size:1.2em;height:30px;"><span style="float:left;line-height:45px;margin-right:5%;">启用</span><input name="isEnable" type="checkbox" <#if theUser??&&theUser.isEnable>checked="checked"</#if> style="width:25px;height:25px;margin-top:10px;float:left;"></p>
                
                <input class="bank_sure" onClick="submitTheForm();" type="button" value="确认">
                
                
            </form>
    </div>
    <!--银行卡结束-->
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
