<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-收费记录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/depot/js/jquery-1.9.1.min.js"></script>
<script>
    function sureInput(id){
        if(null == id){
            alert("订单信息获取失败！");
            return; 
        }
        var keywords = document.getElementById("keywords").value;
        window.location.href="/depot/myaccount/sureInput?id="+id+"&keywords="+keywords;
    }
    function addNewCar(){
        var carCode = prompt("请输入新入库车辆的车牌号：");
        var reg = /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/;
        if(!reg.test(carCode)){
            alert("输入了非法的车牌号码，操作失败！");
            return;
        }
        $.post("/depot/myaccount/addNewCar",{"carCode":carCode},function(res){
            alert(res.info);
        });
    }
</script>
</head>
<body>

    <!--头部开始-->
    <div class="header">
        <p>订单查询</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
        <a href="javascript:addNewCar();" class="lcy_a1"></a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
        <!--收费信息开始-->
        <div class="pay_record">
            <div style="width:90%;float:left;height:45px;margin:15px 5% 2px 5%;background:#ffffff;border:1px #dddddd solid;">
                <form action="/depot/search" method="post">
                    <input type="text" id="keywords" style="height:45px;width:75%;float:left;font-size:0.8em;border:0;" name="keywords" <#if keywords??>value="${keywords!''}"</#if>>
                    <input type="submit" value="查 找"style="width:25%;float:right;height:45px;background:#f65741;border:0;border-radius:0;color:#ffffff;font-size:1em;">
                </form>
            </div>
        </div>
    </div>
    
    <div id="search">
        <#if search_list??> 
            <#list search_list as item>  
                <dl class="detail_month_01">
                    <dt>
                        <span>${item.carCode!''}</span>
                        <p style="color:red">
                            <#if item.statusId??>
                                <#switch item.statusId>
                                    <#case 1>未支付定金<#break>
                                    <#case 2>已支付定金<#break>
                                    <#case 3>预约成功<#break>
                                    <#case 4>正在停车<#break>
                                    <#case 6>交易完成<#break>
                                    <#case 9>交易取消<#break>
                                </#switch>
                            </#if>
                        </p>
                    </dt>
                    <dd>
                        <img src="/depot/images/detail_month_01.png" />
                        <span>
                            <#if item.orderTime??>
                                ${item.orderTime?string("yyyy-MM-dd HH:mm")}
                            </#if>
                        </span>
                        <span style="float:right;color:red;">
                            <#if item.totalPrice??&&item.totalPrice gt 0>停车费用：
                                ${item.totalPrice?string("0.00")}
                            </#if>
                        </span>
                    </dd>
                    <dd>
                        <img src="/depot/images/detail_month_02.png" />
                        <#if item.finishTime??>
                            <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                        <#else>
                            <span>未完成</span>
                        </#if>
                        <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                    </dd>
                </dl>
                <div class="bespeak_list_btn">
                    <input class="sel_2" type="button" style="background-color:<#if item.statusId==3>#00aaef<#else>#999999</#if>;width:100%;float:left;"<#if item.statusId==3>onclick="sureInput(${item.id?c})"<#else> disabled=""</#if> value="确认入库"/ >
                </div>
            </#list>
        </#if>
    </div>
    
    <div class="mb98"></div>
    <div style='height:70px;width:100%; float:left;'></div>
    <!--底部开始-->
    <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2 sel" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->

</body>
</html>
