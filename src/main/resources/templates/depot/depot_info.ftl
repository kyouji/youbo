<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-停车场信息</title>
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
    <p>修改剩余车位数</p>
    <input class="num" type="text" value="112">
    <p class="yes_no">
        <a href="#"  onclick="pupclose()">关闭</a>
        <a href="#" class="blue"  onclick="pupclose()">确定</a>
    </p>
</div>
<!--弹窗-->

    <!--头部开始-->
    <div class="header">
        <p>停车场信息</p>
        <a href="/depot" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--停车场信息开始-->
    <div class="park">
        <dl class="park_min">
            <dt><img src="/depot/images/park_img.png" /></dt>
            <dd><span>郎俊负一楼停车场</span></dd>
        </dl>

        <dl class="park_last">
            <dd><span>车位数</span></dd>
            <dt onclick="pupopen()"><span style=" color:#ff665e;">112</span>/<span>112</span></dt>
        </dl>
        
        <dl class="park_last_01">
            <dd><span>价格</span></dd>
            <dt><span>2元/小时(08：00-20:00)</span><span>1.5元/小时(20：00-08:00)</span></dt>
        </dl>
        
        <dl class="park_last">
            <dd><span>车位数</span></dd>
            <dt><p>平面自走式</p></dt>
        </dl>
        <dl class="park_last">
            <dd><span>车位分类</span></dd>
            <dt><p>占道停车场</p></dt>
        </dl>
        <dl class="park_last">
            <dd><span>地址</span></dd>
            <dt><p>重庆市渝北区黄龙路30号</p></dt>
        </dl>
        <dl class="park_last">
            <dd><span>电话</span></dd>
            <dt><p>023-68686888</p></dt>
        </dl>
    </div>
    <!--停车场信息结束-->

    <div class="mb98"></div>
    
    </div>
    
    <!--底部开始-->
    <div class="footer">
    <a class="a1" href="/depot">主页</a>
    <a class="a2" href="/depot/myaccount">账户<span></span></a>
    <a class="a3" href="/depot/charge">收费记录<span></span></a>
    <a class="a4 sel" href="/depot/info">车场信息<span></span></a>
    <a class="a5" href="/depot/site">设置<span></span></a>
</div>
    <!--底部结束-->
    
</body>
</html>
