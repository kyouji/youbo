<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<script src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/common.js"></script>
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script>
    function submitPhone(){
        var phone=$.trim($("#phone").val());
        console.debug(phone);
        if(!/^1\d{10}$/.test(phone)){
            alert("请输入正确的手机号码！");
            return;
        }
        $("#phoneForm").submit();
    }
    
    function cancel(){
        document.getElementById("phone").value = "";
    }
</script>
</head>
<body>

    <header class="header">
        <p><#if phone??>修改手机号码<#else>设置手机号码</#if></p>
        <a href="/user/center/info" class="a4"></a>
        <a class="main_add" href="javascript:submitPhone();">保存</a>
    </header>
    
    <form id="phoneForm" action="/user/center/phone/edit" method="post">
        <div class="main">
            <div class="personal_info_user">
                <input id="phone" class="btn" type="text" name="phone" value="${phone!''}"/ >
                <p >请输入您的手机号码</p>
                <a href="javascript:cancel();">X</a>
            </div>
        </div>
    </form>
        <!--main END-->

</body>
</html>
