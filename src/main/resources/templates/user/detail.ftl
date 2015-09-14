<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

    <header class="header">
        <p>明细</p>
        <a href="javascript:history.back(-1);" class="a4"></a>
    </header>
    
    <div class="main">
        <#if year??&&year gt 0>
            <dl class="detail">
                <dt><a>${year?c}</a></dt>
                <dd>
                    <#list 1..12 as item>
                        <a href="/user/center/detail/find?year=${year?c}&month=${item}">${item}月</a>
                    </#list>
                </dd> 
            </dl>
    
            <dl class="detail">
                <dt><a>${(year-1)?c}</a></dt>
                <dd>
                    <#list 1..12 as item>
                        <a href="/user/center/detail/find?year=${(year-1)?c}&month=${item}">${item}月</a>
                    </#list>
                </dd> 
            </dl>
        </#if>
    </div><!--main END-->

</body>
</html>
