<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-退款申请</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/depot/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    function reserveResult(id,type){
        if(null == id || null == type){
            alert("参数错误，操作失败！");
            return;
        }
        var message = null;
        if(0 == type){
            message = "确定同意预约（一旦确定，不可更改）？";
        }
        if(1 == type){
            message = "确定拒绝预约（一旦确定，不可更改）？";
        }
        var check = confirm(message);
        if(check){
            window.location.href = "/depot/myaccount/operate?id="+id+"&type="+type;
        }        
    }
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
    <p>预约订单</p>
        <a href="/depot/myaccount" class="a4"></a>    
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
        <#if reserved_list??>
            <#list reserved_list as item>
                <a href="/depot/myaccount/detail?orderId=${item.id?c}">
                <dl class="bespeak_list_01">
                    <dt><a>${item.carCode!''}</a><p></p></dt>
                    <dd>
                        <img src="/depot/images/bespeak_list_01.png" />
                        <#if item.orderTime??>
                            <a>${item.orderTime?string("yyyy-MM-dd HH:mm")}</a>
                        </#if>
                    </dd>
                    <dt>
                        <b>支付定金：</b>
                        <p class="red_1">￥
                        <#if item.firstPay??&&item.firstPay gt 0>
                            <span>${item.firstPay?string("0.00")}</span>
                        <#else>
                            <span>0.00</span>
                        </#if>
                        </p>
                    </dt>
                </dl>
                <div class="bespeak_list_btn">
                    <input class="sel_2" type="button" style="background-color:#00aaef;width:50%;float:left;" onclick="reserveResult(${item.id?c},0)" value="同意"/ >
                    <input type="button" style="width:50%;float:left;background-color:#ef0000;" onclick="reserveResult(${item.id?c},1)" value="拒绝">
                </div>
                </a>
            </#list>
        </#if>
    </div>
    
    <div class="mb98"></div>
    </div>
    
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
