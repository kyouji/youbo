<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script>
    function frost(){
        window.location.href = "/user/center/frost";        
    }    
</script>
</head>

<body>
<div class="header">
        <p>设置</p>
        <a onclick="window.location.href='/user/center'" class="a4"></a>
        <a href="#" class="a5"></a>
</div>
<div style=" margin-top:5%;" class="main">
    <#--
    <a href="/user/center/setting/map">
    <dl class="setting_last">
        <dd><span>离线地图</span></dd>
        <dt><span><img src="/user/images/setting_guide.png"/></span></dt>
    </dl>
    </a>
    -->
  <a> 
    <dl class="setting_last" onclick="frost();">
        <dd><span><#if user??&&user.isFrost??&&user.isFrost==true>解除冻结<#else>冻结余额</#if></span></dd>
        <dt><span><img src="/user/images/setting_guide.png"/></span><p></p></dt>
    </dl>
  </a>

   
    
</div><!--main END-->
</body>
</html>
