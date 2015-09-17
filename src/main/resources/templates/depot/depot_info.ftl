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
<script type="text/javascript" src="/depot/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    function pupopen(){
             document.getElementById("bg").style.display="block";
                document.getElementById("popbox").style.display="block" ;
    }
    function pupclose(){
    document.getElementById("bg").style.display="none";
                document.getElementById("popbox").style.display="none" ;
    }
    
    function changeSurplus(){
        var theNum = document.getElementById("theNum").value;
        var maxNum = ${site.parkingTotalNumber!'0'};
        if(isNaN(theNum)){
           return;
        }
        
        if(theNum < 0){
           return;
        }
        
        if(theNum>maxNum){
            return;
        }
        
        $.post("/depot/info/edit",{"theNum":theNum},function(res){
            pupclose();
            if(0 != res.status){
                alert(res.message);
            }
            if(0 == res.status){
                document.getElementById("surplus").innerHTML = theNum;
            }
        });
    }
</script>
</head>

<body>

    <!--弹窗-->
    <div id="bg"></div>
    <div id="popbox">
        <p>修改剩余车位数</p>
        <input class="num" id="theNum" type="text" value="${site.parkingNowNumber!'0'}">
        <p class="yes_no">
            <a href="#"  onclick="pupclose()">关闭</a>
            <a href="javascript:changeSurplus()" class="blue">确定</a>
        </p>
    </div>
    <!--弹窗-->

    <!--头部开始-->
    <div class="header">
        <p>停车场信息</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
    <!--停车场信息开始-->
        <#if site??>
            <div class="park">
                <dl class="park_min">
                    <dt><img src="${site.imageUri!''}" /></dt>
                    <dd><span>${site.title!''}</span></dd>
                </dl>
        
                <dl class="park_last">
                    <dd><span>车位数<font style="font-size:0.6em;" color="gray">(点击数字可修改剩余车位数)</font></span></dd>
                    <dt onclick="pupopen()"><span style=" color:#ff665e;" id="surplus">${site.parkingNowNumber!'0'}</span>/<span>${site.parkingTotalNumber!'0'}</span></dt>
                </dl>
        
                <dl class="park_last_01">
                    <dd><span>价格</span></dd>
                    <dt>
                        <#if site.dayPrice??&&site.dayPrice gt 0>
                            <span>${site.dayPrice?string("0.00")}元/小时(08：00-20:00)</span>
                        </#if>
                        <#if site.nightPrice??&&site.nightPrice gt 0>
                            <span>${site.nightPrice?string("0.00")}/小时(20：00-08:00)</span>
                        </#if>
                    </dt>
                </dl>
        
                <dl class="park_last">
                    <dd><span>车库类型</span></dd>
                    <dt><p>${site.parkingType!''}</p></dt>
                </dl>
                
                <dl class="park_last">
                    <dd><span>车位类型</span></dd>
                    <dt><p>${site.parkingClassification!''}</p></dt>
                </dl>
                
                <dl class="park_last">
                    <dd><span>地址</span></dd>
                    <dt><p>${site.address!''}</p></dt>
                </dl>
                
                <dl class="park_last">
                    <dd><span>电话</span></dd>
                    <dt><p>${site.serviceTele!''}</p></dt>
                </dl>
            </div>
        </#if>
        <!--停车场信息结束-->
        
        <div class="mb98"></div>
    
    </div>

    <!--底部开始-->
    <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/charge">收费记录</a>
        <a class="a4 sel" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
    
</body>
</html>
