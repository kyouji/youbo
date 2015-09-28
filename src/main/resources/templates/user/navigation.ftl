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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=9xkTHRt1N0iCXRsRWdGpuGGW"></script>
</head>

<body>
    <div class="header">
        <p>找车位</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <div class="main">
        <div class="find_img" id="myMap">
        </div> 

        <div id="result"> 
        
        </div>
    </div>
    <!--main END-->
</body>
</html>
<script type="text/javascript">
    var map;
    var userPiont;
    var drivingRoute;//路线
    var beginPoint;
    var endPoint;

    map = new BMap.Map("myMap");
    var point = new BMap.Point(106.540307,29.591239);
    map.centerAndZoom(point, 16);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
   <#if x??>
        endPoint =  new BMap.Point(${x!"0.000000"}, ${y!"0.000000"});
   </#if>
   
   var geolocation = new BMap.Geolocation();  
    geolocation.getCurrentPosition(
        function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                console.debug(r);
                beginPoint = new BMap.Point(r.point.lng,r.point.lat);
                userPiont = r.point;
                map.panTo(r.point);
                drivingRoute = new BMap.DrivingRoute(map, {renderOptions: {map: map, panel: "result", autoViewport: true}});
                var h = drivingRoute.search(beginPoint, endPoint);     
            }
            else 
            {
                alert('failed'+this.getStatus());
            }
            $(".BMap_Marker").css("display","none");        
        },{enableHighAccuracy: true}
    )
</script>