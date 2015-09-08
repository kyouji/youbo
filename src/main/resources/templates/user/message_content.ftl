<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详情</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

    <div class="header">
        <p>详情</p>
        <a href="#" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
        <!--消息详情开始-->
        <#if info??>
            <div class="message_details">
                <ul>
                    <li class="li_1"><span>${info.title!''}</span></li>
                    <li><p class="time_1">${info.releaseTime?string("yyyy-MM-dd")}</p></li>
                </ul>
                <!--消息内容-->
                <div class="content">
                    <p>${info.content!''}</p>
                    <div>
                        <#if info.infoPictures??>
                            <#list info.infoPictures?split(",") as uri>
                                <img src="${uri}" />
                            </#list>
                        </#if>
                    </div>
                </div>
            </div>
        </#if>
        <!--消息详情结束-->
        
        <div class="mb98"></div>
    
    </div>

</body>
</html>
