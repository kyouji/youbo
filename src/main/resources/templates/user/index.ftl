<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-首页</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>优泊天下</p>
        <a href="#" class="a1"></a>
        <a href="#" class="a3"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--banner开始-->
    <div class="addWrap">
        <div class="swipe" id="mySwipe">
            <div class="swipe-wrap">
            <#if depot_ad_list??>
                <#list depot_ad_list as item>
                    <div><a href="${item.linkUri!'javascript:;'}"><img class="img-responsive" src="${item.fileUri!''}"/></a></div>
                </#list>
            </#if>
            </div>
      </div>
      <ul id="position">
        <li class="cur"></li>
        <#if depot_ad_list??&&depot_ad_list?size gte 2>
                <#list 2..depot_ad_list?size as item>
                    <li class=""></li>
                </#list>
            </#if>
      </ul>
    </div>
    <script src="/depot/js/swipe.js"></script> 
    <script type="text/javascript">
    var bullets = document.getElementById('position').getElementsByTagName('li');
    var banner = Swipe(document.getElementById('mySwipe'), {
        auto: 2000,
        continuous: true,
        disableScroll:false,
        callback: function(pos) {
            var i = bullets.length;
            while (i--) {
              bullets[i].className = ' ';
            }
            bullets[pos].className = 'cur';
        }
    });
    </script>
    <!--banner结束-->
    
    <!--导航开始-->
    <div class="topNav">
        <a href="/user/center" class="four_1 a1"><img src="/depot/images/index01.png"><p>个人中心</p></a>
        <a href="/user/order" class="four_1 a2"><img src="/depot/images/index02.png"><p>我的订单</p></a>
        <a href="/user/find" class="four_1 a3"><img src="/depot/images/index03.png"><p>找车位</p></a>
        <a href="/user/code" class="four_1 a4"><img src="/depot/images/index04.png"><p>二维码</p></a>
        <div class="clear"></div>
        <a href="#" class="p5"><img src="/depot/images/index05.png"></a>
    </div>
    <!--导航结束--
    <div class="mb98"></div>
    </div>
    
    <!--底部开始--
    <div class="footer">
        <a class="a1 sel" href="#">主页</a>
        <a class="a2" href="#">账户</a>
        <a class="a3" href="#">收费记录</a>
        <a class="a4" href="#">车场信息</a>
        <a class="a5" href="#">设置</a>
    </div>
    <!--底部结束-->
    
</body>
</html>
