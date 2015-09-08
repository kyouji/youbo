<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-设置</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    function pupopen(){
             document.getElementById("bg").style.display="block";
                document.getElementById("popbox").style.display="block" ;
     }
    function pupclose(){
    document.getElementById("bg").style.display="none";
                document.getElementById("popbox").style.display="none" ;
    }
</script>
</head>

<body>

<!--弹窗-->
<div id="bg"></div>
<div id="popbox">
    <p>选择模式</p>
    <p class="mode sel"><b>计时模式</b><a href="#">编辑</a></p>
    <p class="mode"><b>计次模式</b><a href="#">编辑</a></p>
    <p class="yes_no">
        <a href="#"  onclick="pupclose()">关闭</a>
        <a href="#" class="blue"  onclick="pupclose()">确定</a>
    </p>
</div>
<!--弹窗-->

    <!--头部开始-->
    <div class="header">
        <p>设置</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--设置开始-->
    <div class="site">
        <dl class="about_us_min">
            <dt>
                <img src="images/logo.png"  />
                <p>优泊天下  收费版</p>
            </dt>
        </dl>
        <div class="site_us">
            <dl class="about_us_last">
                <dd><a>检查更新</a></dd>                
                <dt class="arrow"><a href="#"><img src="/depot/images/advance.png" /></a></dt>
                <dt><p>V2.0</p></dt>
            </dl>
            <dl class="about_us_last" onclick="pupopen()">
                <dd><a>切换收费模式</a></dd>
                <dt class="arrow"><a href="#"><img src="/depot/images/advance.png" /></a></dt>
                <dt><p>计时+收费</p></dt>
            </dl>
            <dl class="about_us_last">
                <dd><a>联系客服</a></dd>
                <dt class="arrow"><a href="#"><img src="/depot/images/advance.png" /></a></dt>
                <dt><p>800-820-8820</p></dt>
            </dl>
        </div>
        <ul class="sign_out">
            <li><a href="#">退出登录</a></li>
        </ul>
    </div>
    <!--设置结束-->

    <div class="mb98"></div>
        
    </div>
    
    <!--底部开始-->
    <div class="footer">
    <a class="a1" href="/depot">主页</a>
    <a class="a2" href="/depot/myaccount">账户<span></span></a>
    <a class="a3" href="/depot/charge">收费记录<span></span></a>
    <a class="a4" href="/depot/info">车场信息<span></span></a>
    <a class="a5 sel" href="/depot/site">设置<span></span></a>
    </div>
    <!--底部结束-->
    
</body>
</html>