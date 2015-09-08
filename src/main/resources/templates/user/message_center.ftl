<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>消息中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

<body>
    <!--头部开始-->
    <div class="header">
        <p>消息中心</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
        <!--消息中心开始-->
        <div class="message_center">
            <!--未读消息-->
            <#if unread_list??&&unread_list?size gt 0>
                <#list unread_list as unread>
                    <a href="/user/center/message/content/${unread.id}">
                        <ul>
                            <li class="li_1">${unread.title!''}</li>
                            <li><p class="time_1">${unread.releaseTime?string("yyyy-MM-dd")}</p></li>
                            <span class="red_dot"></span>
                        </ul>
                    </a>
                </#list>
            </#if>
            <!--已读消息-->
            <#if read_list??&&read_list?size gt 0>
                <#list read_list as read>
                    <ul>
                        <a "/user/center/message/content/${read.id}">
                            <li class="li_1">${read.title!''}</li>
                            <li><p class="time_1">${read.releaseTime?string("yyyy-MM-dd")}</p></li>
                        </a>
                    </ul>
                </#list>
            </#if>
        </div>
    </div>
</body>
</html>
