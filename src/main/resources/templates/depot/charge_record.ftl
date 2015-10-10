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
<script type="text/javascript">
    var check;
    $(function(){
        changeOrder(1);
    })
    
    function changeDate(){
        var date = $("#date").val();
        $.post("/depot/charge/date",{"check":check,"date":date},function(data){
            $("#theOrder").html(data);
            changeOrder(check);
        }); 
    }
    
    function changeOrder(type){
        if(1 == type){
            check = 1;
           $("#unpayed_label").addClass("sel");
           $("#payed_label").removeClass("sel");
           
           $("#unpayed").css("display","block");
           $("#payed").css("display","none"); 
        }
        
        if(2 == type){
            check = 2;
           $("#unpayed_label").removeClass("sel");
           $("#payed_label").addClass("sel");
           
           $("#unpayed").css("display","none");
           $("#payed").css("display","block"); 
        }
    }
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>订单管理</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
        <a href="/depot/search" style="background-image:url('/images/fdj.png')" class="a3"></a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
        <!--收费信息开始-->
        <div class="pay_record">
            <div class="select_dates">
                <p class="select_1"><input type="date" style="-webkit-appearance:none;height:30px;width:90%;font-size:0.8em;padding-left:5%;" id="date" onChange="changeDate();" <#if theDate??>value="${theDate?string("YYYY-MM-dd")}"</#if>></p>
            </div>
            <div class="payment_situation">
                <a href="javascript:changeOrder(1);" id="unpayed_label" class="a1 sel">未付款</a>
                <a href="javascript:changeOrder(2);" id="payed_label" class="a1">已付款</a>
            </div>
            <div id="theOrder">
                <#include "/depot/charge_detail.ftl">
            </div>
            
        </div>
        <!--收费信息结束-->
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
