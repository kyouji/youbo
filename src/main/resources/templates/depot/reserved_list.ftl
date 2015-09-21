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
    function changeHeads(){
        var filebutton = document.getElementById("filebutton");
        filebutton.click();
    }
    function getFile(id){
        $.post("/depot/myaccount/saveOrderId",{"id":id},function(res){
            if(0==res.status){
                document.getElementById("uploadImgForm").submit();  
            }else{
                var check = confirm("获取订单信息失败，是否再试一次？");
                if(check){
                    getFile(id);
                }
            }
        });
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
                <a href="/depot/myaccount/detail?orderId=${item.id}">
                <dl class="bespeak_list_01">
                    <dt><a>${item.diyTitle!''}</a><p>退款成功</p></dt>
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
                    <input class="sel_2" type="button" style="background-color:#00aaef;" onclick="changeHeads();" value="拍摄车辆车牌号码"/ >
                    <form id="uploadImgForm" enctype="multipart/form-data" action="/depot/myaccount/headImg" method="post">
                        <input style="display:none" name="Filedata" type="file" onchange="getFile(${item.id?c});" id="filebutton">
                    </from>
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
        <a class="a3" href="/depot/charge">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->

</body>
</html>
