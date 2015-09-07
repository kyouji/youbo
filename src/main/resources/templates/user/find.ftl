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
var map;
var userPiont;
var drivingRoute;//路线

$(document).ready(function(){
//102.739129,25.087506

   // $(".find_float").css('display','');
   loadMap();
   <#if depot_list??>
    <#list depot_list as item>
        <#if item.longitude?? && item.latitude??>
            
        addMarker(${item.longitude?string("0.000000")}, ${item.latitude?string("0.000000")},'${item.title}','${item.address!""}',${item.id});
           
        </#if>
    </#list>
    </#if>
});
function loadMap()
{
    <#--自定义marker图片-->
    var myIcon = new BMap.Icon("/user/images/map.png", new BMap.Size(23, 25), {
     offset: new BMap.Size(10, 25), // 指定定位位置
    imageOffset: new BMap.Size(0, 0 - 0 * 25) // 设置图片偏移
    });
    <#--end-->
    // 百度地图API功能
    map = new BMap.Map("myMap");
    var point = new BMap.Point(106.540307,29.591239);
    map.centerAndZoom(point, 16);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.addEventListener("click", getClickPoint);//监听地图点击事件
    <#--根据ip获取坐标-->
    var geolocation = new BMap.Geolocation();  
    geolocation.getCurrentPosition(
        function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                userPiont = r.point;
                var mk = new BMap.Marker(r.point, {icon: myIcon});
                map.addOverlay(mk);
                map.panTo(r.point);
            }
            else 
            {
                alert('failed'+this.getStatus());
            }        
            },{enableHighAccuracy: true}
    )
}

//添加marker
function addMarker(x, y, title,address,depotId)
{
    var marker = new BMap.Marker(new BMap.Point(x, y)); // 创建点
    map.addOverlay(marker);
    marker.addEventListener("click", function(e){
        $(".find_float").css('display','');
        $("#guid").html("<dl class='find01'>"+
        "<dt><span>" +title+ "</span><p>329m</p></dt>"+
        "<dd><div>20 </div><span>10元/小时</span></dd>"+
        "<dd><p>"+address+"</p></dd>"+
        "</dl>"+
        "<dl class='find_btn'>"+
        "<dt><a onclick='javascript:startNavigation(" + x + "," + y +")'><img src='/user/images/park_icon01.png' /><span>导航</span></a></dt>"+
        "<dd><a href='/user/find/bespeak?depotId="+depotId+ "'><img src='/user/images/park_icon02.png' /><span>预约</span></a></dd>"+
        "</dl>");
    });
}

//计算距离
var searchComplete = function (results){
        if (transit.getStatus() != BMAP_STATUS_SUCCESS){
            return ;
        }
        var plan = results.getPlan(0);
        output += plan.getDuration(true) + "\n";                //获取时间
        output += "总路程为：" ;
        output += plan.getDistance(true) + "\n";             //获取距离
    }
    var transit = new BMap.DrivingRoute(map, {renderOptions: {map: map},
        onSearchComplete: searchComplete,
        onPolylinesSet: function(){        
            setTimeout(function(){alert(output)},"1000");
    }});

//开始导航
function startNavigation(x,y)
{
    var end = new BMap.Point(x, y);
    if(drivingRoute==null)
    {
        drivingRoute = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true},policy:BMAP_DRIVING_POLICY_LEAST_TIME});
    }
    drivingRoute.search(userPiont, end);
}

//点击地图事件
function getClickPoint(e){
    if(e.overlay){  
    $(".find_float").css('display','');
            return;
    }
    $(".find_float").css('display','none');
}
</script>
</head>

<body>


<div class="header">
        <p>找车位</p>
        <a href="/user" class="a4"></a>
</div>
<div class="main">
    <div class="find_img" id="myMap">
    </div> 
    
    <div class="find_float" style="display:none;"> 
    <span id="guid">
        <dl class="find01">
            <dt><span>首创·立方停车库</span><p>329m</p></dt>
            <dd><div>20 </div><span>10元/小时</span></dd>
            <dd><p>重庆市九龙坡区龙腾大道99号</p></dd>
        </dl>
        <dl class="find_btn">
            <dt><a><img src="/user/images/park_icon01.png" /><span>导航</span></a></dt>
            <dd><a><img src="/user/images/park_icon02.png" /><span>预约</span></a></dd>
        </dl>
     </span>
    </div>
</div><!--main END-->

</body>
</html>