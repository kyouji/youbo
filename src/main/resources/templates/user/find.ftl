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

<script src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/common.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=lwRXRetipHPGz8y6lzUlUZfc"></script>

<script type="text/javascript">
$(document).ready(function(){
//102.739129,25.087506
loadMap(102.739129,25.087506)
});


function loadMap(x, y)
{
    // 百度地图API功能
    /*
    var map = new BMap.Map("myMap");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(x, y), 16);  // 初始化地图,设置中心点坐标和地图级别
    
    map.setCurrentCity("昆明");          // 设置地图显示的城市 此项是必须设置的
    
    map.addOverlay(new BMap.Marker(new BMap.Point(x, y)); // 创建点
    */
    var map = new BMap.Map("myMap");
    var point = new BMap.Point(x, y);
    map.centerAndZoom(point, 16);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    var marker = new BMap.Marker(new BMap.Point(x, y)); // 创建点
    map.addOverlay(marker);
    
}
</script>
</head>

<body>


<div class="header">
        <p>找车位</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
</div>
<div class="main">
    
    <div class="find_img" id="myMap">

    </div> 
    <div class="find_float"> 
    <dl class="find01">
    <dt><span>首创·立方停车库</span><p>329m</p></dt>
    <dd><div>20 </div><span>10元/小时</span></dd>
    <dd><p>重庆市九龙坡区龙腾大道99号</p></dd>
    </dl>
      
    
    <dl class="find_btn">
    <dt><a><img src="images/park_icon01.png" /><span>导航</span></a></dt>
    <dd><a><img src="images/park_icon02.png" /><span>预约</span></a></dd>
    </dl>
    </div>
</div><!--main END-->

</body>
</html>
