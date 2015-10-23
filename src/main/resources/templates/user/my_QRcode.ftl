<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的二维码</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>

<div class="header">
        <p>二维码</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
</div>
<div class="main">
    <div class="myQRcode">  
        <!--
        <span><img src="/user/images/myQRcode.png"/></span>
        -->
        <span><img src="/images/${src!'/myQRcode.png'}"/></span>
        <p>给收费员扫一扫完成停车缴费</p>
    </div>
</div><!--main END-->
</body>
</html>
