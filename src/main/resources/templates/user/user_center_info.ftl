<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
    <div class="header">
        <p>个人信息</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    
    <#if user??>
        <div class="main">
            <div class="personal_info">
                <div class="personal_info_img">
                    <a><img src="${user.headImageUri!''}"></a>
                </div>
                <p></p>
            </div>
            <dl class="personal_info_list">
                <dd><div>昵称</div><p>${user.nickname!''}</p><a><span>修改</span><img src="/user/images/setting_guide.png" /></a></dd>
                <dd><div>手机号</div><p>${user.mobile!''}</p><a><span>修改</span><img src="/user/images/setting_guide.png" /></a></dd>
                <dd><div>车牌</div><p>${user.carCode!''}</p><a><span>修改</span><img src="/user/images/setting_guide.png" /></a></dd>
                <dd>
                    <div>登录密码</div>
                    <p>
                        <#list 1..user.password?length as item>
                            *
                        </#list>
                    </p>
                    <a href="/user/center/info/edit?editType=password">
                        <span>修改</span>
                        <img src="/user/images/setting_guide.png" />
                    </a>
                </dd>
                <dd><div>支付密码</div><p></p><a><span>设置/修改</span><img src="/user/images/setting_guide.png" /></a></dd>
            </dl>
        </div>
    </#if>
    <!--main END-->
</body>
</html>