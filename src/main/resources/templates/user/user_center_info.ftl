<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="header">
        <p>个人信息</p>
        <a href="/user/center;" class="a4"></a>
    </div>
    
    <#if user??>
        <div class="main">
            <div class="personal_info">
                <div class="personal_info_img">
                    <a onclick="changeHeads();"><img width="100px" height="100px" src="${user.headImageUri!'/user/images/default_head.png'}"></a>
                    <script>
                        function changeHeads(){
                            var filebutton = document.getElementById("filebutton");
                            filebutton.click();
                        }
                        function getFile(){
                            document.getElementById("uploadImgForm").submit();
                            
                        }
                    </script>
                </div>
                <p></p>
            </div>
            <dl class="personal_info_list">
                <form id="uploadImgForm" enctype="multipart/form-data" action="/user/center/headImg" method="post">
                    <input style="display:none" name="Filedata" type="file" onchange="getFile();" id="filebutton">
                </from>
                <dd><div>车牌</div><p>${user.carCode!''}</p></dd>
                <dd onclick="window.location.href='/user/center/info/nickname'"><div>昵称</div><p>${user.nickname!''}</p><a href="/user/center/info/nickname"><span></span><img src="/user/images/setting_guide.png" /></a></dd>
                <dd onclick="window.location.href='/user/center/info/phone'"><div>手机</div><p style="color:#999">${user.mobile!''}</p><a href="/user/center/info/phone"><span></span><img src="/user/images/setting_guide.png" /></a></dd>
                <dd onclick="window.location.href='/user/center/info/edit?editType=password'">
                    <div>修改密码</div>
                    <p>******</p>
                    <a href="/user/center/info/edit?editType=password">
                        <span></span>
                        <img src="/user/images/setting_guide.png" />
                    </a>
                </dd>
                <dd onclick="window.location.href='/user/center/info/pay'">
                    <div>提现密码</div>
                    <p>
                        <#if user.payPassword??>
                            ******
                        </#if>
                    </p>
                    <a href="/user/center/info/pay">
                        <span></span>
                        <img src="/user/images/setting_guide.png" />
                    </a>
                </dd>
            </dl>
        </div>
    </#if>
    <!--main END-->
</body>
</html>
