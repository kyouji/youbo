<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-提现记录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>提现记录</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--提现记录开始-->
    <#if deposit_list??>
        <div class="withdraw_record_list">
            <#list deposit_list as item>
                <ul onclick="window.location.href='/user/center/deposit/detail/${item.id?c}'">
                    <li class="li_1">
                        <span>提现单号</span>
                        <p>${item.num!''}</p>
                    </li>
                    <li>
                    <span>提现时间</span>
                        <#if item.depositDate??>
                            <p class="time_1">${item.depositDate?string("yyyy-MM-dd HH:mm:ss")}</p>
                        </#if>
                    </li>
                </ul>
            </#list>
        </div>
    </#if>
    <!--提现记录结束-->

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
