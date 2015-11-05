<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>月份明细</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

    <div class="header">
        <p>月份明细</p>
        <a href="javascript:history.back(-1);" class="a4"></a>
    </div>
    
    <div class="main">
        <ul class="detail_month" style="background:#f5f5f5;">
            <li><span>总收入：</span></li>
            <li class="detail_month_style"><span><#if totalPrice??>${totalPrice?string("0.00")}元</#if></span></li>
        </ul>
        <#if days??>
            <#list 1..days as item>
                <a href="/depot/myaccount/detailDay?year=${year?c}&month=${month}&day=${item}">
                    <ul class="detail_month" style="border-bottom:1px #dddddd solid;">
                        <li><span>${item}日</span></li>
                        <li class="detail_month_style"><span><#if totalPrice??>${("income"+item)?eval?string("0.00")}元</#if></span></li>
                    </ul>
                </a>
            </#list>
        </#if>
    </div><!--main END-->

</body>
</html>
