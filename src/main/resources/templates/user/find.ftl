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
        <input type="text" id="search" style=" width:70%;margin:14px 15%;height:26px;display:inline-block;float:left;border:none;">
        <a href="/user" class="a4" style=" display:inline-block;float:left;"></a>
    </div>
  <div id="mapContainer" style="top:"></div>

  <script> 
    var lat;
    var lng;
  
    var map = new AMap.Map('mapContainer', {
      center: [116.397428, 39.90923],
      zoom: 15,
      animateEnable:true,
      dragEnable:true,
      zoomEnable:true,
      resizeEnable: true
    });
    
    map.plugin(["AMap.ToolBar"],function(){
        var tool = new AMap.ToolBar({
            offset:new AMap.Pixel(10,84)
        });
        map.addControl(tool);   
    });
    
    map.plugin(['AMap.Autocomplete'],function(){
        var auto = new AMap.Autocomplete({
            input:search
        });
        map.addControl(auto);
        AMap.event.addListener(auto,'select',function(e){
            var keywords_lat = e.poi.location.lat;
            var keywords_lng = e.poi.location.lng;
            map.setCenter(new AMap.LngLat(keywords_lng,keywords_lat))
            map.setZoom(18);
        })
    });
    
    map.plugin('AMap.Geolocation',function(){
        var geolocation = new AMap.Geolocation({
            showButton:true,
            buttonDom:'<img width="50px;" height="50px;" src="/images/p1.png">',
            buttonPosition: 'LB', 
            showMarker: true,        
            showCircle: true,        
            panToLocation: true  
        });
        
         map.addControl(geolocation);
        
        if(geolocation.isSupported( )){
            geolocation.getCurrentPosition();
            AMap.event.addListener(geolocation,'complete',function(e){
                lng = e.position.lng;
                lat = e.position.lat;
            })
        }else{
            alert("您的浏览器不支持定位功能！");
        }
    });
    
    <#if depot_list??>
        <#list depot_list as item>
            <#if item.longitude?? && item.latitude??>
                <#assign percent=item.parkingNowNumber/item.parkingTotalNumber>
                <#if item.parkingNowNumber==0>
                    var mk${item_index} = new AMap.Marker({
                        map:map                    
                    });
                    mk${item_index}.setPosition(new AMap.LngLat(${item.longitude},${item.latitude}));
                    mk${item_index}.setIcon(new AMap.Icon({
                        image:'/images/p_red.png'
                    }))   
                    
                    var info${item_index} = new AMap.InfoWindow({
                        isCustom:true,
                        closeWhenClickMap:true,
                        content:"<div style='margin:0 auto; width:64%;'><dl class='find01'><dt><span>${item.title!''}</span></dt>"+
                        "<dd><div>${item.parkingNowNumber}</div><span>10元/小时</span></dd>"+
                        "<dd><p>定金：${firstPay?string("0.00")}</p></dd>"+
                        "</dl>"+
                        "<dl class='find_btn'>"+
                        "<dt><a onclick='javascript:goNavigation(${item.longitude},${item.latitude},${item.id});'><img src='/user/images/park_icon01.png' /><span>导航</span></a></dt>"+
                        "<dd><a <#if haveBankCard??>onclick='reserve(${item.id},${item.longitude},${item.latitude});'<#else>onclick='userAlert();'</#if>><img src='/user/images/park_icon02.png' /><span>预约</span></a></dd>"+
                        "</dl></div>"
                    })
                    AMap.event.addListener(mk${item_index},'click',function(){
                        info${item_index}.open(map,new AMap.LngLat(${item.longitude},${item.latitude}));
                    })                     
                <#elseif percent lt 0.5>
                    var mk${item_index} = new AMap.Marker({
                        map:map                    
                    });
                    mk${item_index}.setPosition(new AMap.LngLat(${item.longitude},${item.latitude}));
                    mk${item_index}.setIcon(new AMap.Icon({
                        image:'/images/p_yellow.png'
                    }))
                    
                     var info${item_index} = new AMap.InfoWindow({
                        isCustom:true,
                        closeWhenClickMap:true,
                        content:"<div style='margin:0 auto; width:64%;'><dl class='find01'><dt><span>${item.title!''}</span></dt>"+
                        "<dd><div>${item.parkingNowNumber}</div><span>10元/小时</span></dd>"+
                        "<dd><p>定金：${firstPay?string("0.00")}</p></dd>"+
                        "</dl>"+
                        "<dl class='find_btn'>"+
                        "<dt><a onclick='javascript:goNavigation(${item.longitude},${item.latitude},${item.id});'><img src='/user/images/park_icon01.png' /><span>导航</span></a></dt>"+
                        "<dd><a <#if haveBankCard??>onclick='reserve(${item.id},${item.longitude},${item.latitude});'<#else>onclick='userAlert();'</#if>><img src='/user/images/park_icon02.png' /><span>预约</span></a></dd>"+
                        "</dl></div>"
                    })
                    AMap.event.addListener(mk${item_index},'click',function(){
                        info${item_index}.open(map,new AMap.LngLat(${item.longitude},${item.latitude}));
                    })                     
                <#elseif percent gt 0.5>   
                    var mk${item_index} = new AMap.Marker({
                        map:map                    
                    });
                    mk${item_index}.setPosition(new AMap.LngLat(${item.longitude},${item.latitude}));
                    mk${item_index}.setIcon(new AMap.Icon({
                        image:'/images/p_green.png'
                    }))
                    
                     var info${item_index} = new AMap.InfoWindow({
                        isCustom:true,
                        closeWhenClickMap:true,
                        content:"<div style='margin:0 auto; width:64%;'><dl class='find01'><dt><span>${item.title!''}</span></dt>"+
                        "<dd><div>${item.parkingNowNumber}</div><span>10元/小时</span></dd>"+
                        "<dd><p>定金：${firstPay?string("0.00")}</p></dd>"+
                        "</dl>"+
                        "<dl class='find_btn'>"+
                        "<dt><a onclick='javascript:goNavigation(${item.longitude},${item.latitude},${item.id});'><img src='/user/images/park_icon01.png' /><span>导航</span></a></dt>"+
                        "<dd><a <#if haveBankCard??>onclick='reserve(${item.id},${item.longitude},${item.latitude});'<#else>onclick='userAlert();'</#if>><img src='/user/images/park_icon02.png' /><span>预约</span></a></dd>"+
                        "</dl></div>"
                    })
                    AMap.event.addListener(mk${item_index},'click',function(){
                        info${item_index}.open(map,new AMap.LngLat(${item.longitude},${item.latitude}));
                    })                         
                </#if>
            </#if>
        </#list>
    </#if>
    
    
    function goNavigation(x,y,id){
        var u = navigator.userAgent;
        if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
            window.location.href = "androidamap://navi?sourceApplication=amap&lat="+y+"&lon="+x+"&dev=1&style=2";
            alert("启动导航功能前请确认已经安装了“高德地图”APP");
        } else if (u.indexOf('iPhone') > -1) {//苹果手机
            alert("启动导航功能前请确认已经安装了“高德地图”APP");
            window.location.href = "iosamap://navi?sourceApplication=applicationName&lat="+y+"&lon="+x+"&dev=1&style=2";
        } else{//winphone手机
            alert("只有ios和android系统的手机才能够实现导航功能！");
        }
    }
    
    function userAlert(){
        alert("绑定银行卡后才能够预约！");
        window.location.href = "/user/center/bankcard/add";
    }
    
    function reserve(depotId,x,y){
    <#if user??&&user.balance??&&user.balance gt firstPay>
        $.post("/user/find/reserve",{"username":"${user.username!''}",diyId:depotId},function(res){
            alert(res.message);
            if(0==res.status){
                var u = navigator.userAgent;
                if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {
                    window.location.href = "androidamap://navi?sourceApplication=appname&lat="+y+"&lon="+x+"&dev=1&style=2";
                } else if (u.indexOf('iPhone') > -1) {//苹果手机
                    window.location.href = "iosamap://navi?sourceApplication=applicationName&lat="+y+"&lon="+x+"&dev=1&style=2";
                } else{//winphone手机
                    alert("只有ios和android系统的手机才能够实现导航功能！");
                }
            }
        });
    <#else>
        window.location.href="/user/find/bespeak?depotId="+depotId;
    </#if>
    }
    
    </script>
</body>

</html>