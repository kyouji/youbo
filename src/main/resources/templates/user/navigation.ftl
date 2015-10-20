<!doctype html>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">

      <title>地图显示</title>
      <link href="/user/css/map.css" rel="stylesheet" type="text/css" />
      <link href="/user/css/style.css" rel="stylesheet" type="text/css" />
      <script src="/user/js/jquery-1.9.1.min.js"></script>
      <script src="http://webapi.amap.com/maps?v=1.3&key=a54918ddc2bd04fdddd9e36ee5961c33"></script>
</head>

<body>
    <div class="theMap">
        <p style="left:43%">导航</p>
        <a href="/user" class="a4"></a>
    </div>
    <div id="mapContainer" style="top:"></div>
    <div id="result" style="position:absolute;width:100%;height:10%;left:0;bottom:0;z-index:1000"></div>
    <script>
  
    var map = new AMap.Map('mapContainer', {
      center: [${lng}, ${lat}],
      zoom: 14,
      animateEnable:true,
      dragEnable:true,
      zoomEnable:true,
      resizeEnable: true
    });
    
    map.plugin(["AMap.ToolBar"],function(){
        var tool = new AMap.ToolBar({
            offset:new AMap.Pixel(10,120)
        });
        map.addControl(tool);   
    });
    
    map.plugin(['AMap.Driving'],function(){
        var driving;
        driving = new AMap.Driving({
            panel:result,
            map:map  
        });
        driving.search(new AMap.LngLat(${lng},${lat}),new AMap.LngLat(${x},${y}));
    });
    
</script>
</body>

</html>