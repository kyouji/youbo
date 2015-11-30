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
<link href="/depot/css/go.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/depot/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/depot/js/rich_lee.js"></script>
</head>
<script type="text/javascript">
    window.onload = function(){
        $('.img_obox a').height($('.img_obox a').width()*0.56)
    };
</script>
<body>
    <!--头部开始-->
    <div class="header">
        <p>无牌车管理</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    <div class="img_obox">
        <#if no_carCode_list??>
            <#list no_carCode_list as item>
                <a href="/depot/myaccount/detail?orderId=${item.id?c}">
                    <img src="${item.carCodePhoto!''}"  />
                </a>
            </#list>
        </#if>
    </div>

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
