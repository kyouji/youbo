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
<script type="text/javascript">
    function startReserve(username,diyId){
        $.post("/user/find/reserve",{"username":username,"diyId":diyId},function(res){
            if(0 != res.status){
                alert(res.message);
            }
            if(0==res.status){
                window.location.href="/user/order/first/pay?id="+res.id;
            }
        });
    } 
</script>
</head>

<body>

    <header class="header">
        <p>停车场详情</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </header>
    
    <div class="main">
        <#if diySite??>
            <dl class="park_min">
                <dt><img src="${diySite.imageUri!''}" /></dt>
                <dd><span>${diySite.title!''}</span></dd>
            </dl>
        
            <dl class="park_last">
                <dd><span>车位数</span></dd>
                <dt><span style=" color:#ff665e;">${diySite.parkingNowNumber!'0'}</span>/<span>${diySite.parkingTotalNumber!'0'}</span></dt>
            </dl>
        
            <dl class="park_last_01">
                <dd><span>价格</span></dd>
                <dt>
                    <#if diySite.dayPrice??>
                        <span>${diySite.dayPrice?string("0.00")}元/小时(08：00-20:00)</span>
                    </#if>
                    <#if diySite.nightPrice??>                       
                        <span>${diySite.nightPrice?string("0.00")}元/小时(20：00-08:00)</span>
                    </#if>
                </dt>
            </dl>
        
            <dl class="park_last">
                <dd><span>车库类型</span></dd>
                <dt><p>${diySite.parkingType!''}</p></dt>
            </dl>
            <dl class="park_last">
                <dd><span>车位类型</span></dd>
                <dt><p>${diySite.parkingClassification!''}</p></dt>
            </dl>
            <dl class="park_last">
                <dd><span>地址</span></dd>
                <dt><p>${diySite.address!''}</p></dt>
            </dl>
            <dl class="park_last">
                <dd><span>电话</span></dd>
                <dt><p>${diySite.mobile!''}</p></dt>
            </dl>
            <dl class="park_btn">
                <dt><a><img src="/user/images/park_icon01.png" /><span>导航</span></a></dt>
                <dd><a href="javascript:startReserve('${username!''}',${diySite.id?c})"><img src="/user/images/park_icon02.png" /><span>预约</span></a></dd>
            </dl>
        </#if>
    </div><!--main END-->

</body>
</html>
