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
    var check;
    $(function(){
        changeOrder(1);
    })
    
    function changeOrder(type){
        if(1 == type){
            check = 1;
            $("#xs_label").addClass("sel");
            $("#xj_label").removeClass("sel");
            $("#md_label").removeClass("sel");
            $("#yk_label").removeClass("sel");
            $("#wy_label").removeClass("sel");
            
            $("#xs").css("display","block");
            $("#xj").css("display","none");
            $("#md").css("display","none");
            $("#yk").css("display","none");
            $("#wy").css("display","none");
        }
        
        if(2 == type){
        check = 2;
            $("#xs_label").removeClass("sel");
            $("#xj_label").addClass("sel");
            $("#md_label").removeClass("sel");
            $("#yk_label").removeClass("sel");
            $("#wy_label").removeClass("sel");
            
            $("#xs").css("display","none");
            $("#xj").css("display","block");
            $("#md").css("display","none");
            $("#yk").css("display","none");
            $("#wy").css("display","none");
        }
        
        if(3 == type){
        check = 3;
            $("#xs_label").removeClass("sel");
            $("#xj_label").removeClass("sel");
            $("#md_label").addClass("sel");
            $("#yk_label").removeClass("sel");
            $("#wy_label").removeClass("sel");
            
            $("#xs").css("display","none");
            $("#xj").css("display","none");
            $("#md").css("display","block");
            $("#yk").css("display","none");
            $("#wy").css("display","none");
        }
        
        if(4 == type){
        check = 4;
            $("#xs_label").removeClass("sel");
            $("#xj_label").removeClass("sel");
            $("#md_label").removeClass("sel");
            $("#yk_label").addClass("sel");
            $("#wy_label").removeClass("sel");
            
            $("#xs").css("display","none");
            $("#xj").css("display","none");
            $("#md").css("display","none");
            $("#yk").css("display","block");
            $("#wy").css("display","none");
        }
        
        if(5 == type){
        check = 5;
            $("#xs_label").removeClass("sel");
            $("#xj_label").removeClass("sel");
            $("#md_label").removeClass("sel");
            $("#yk_label").removeClass("sel");
            $("#wy_label").addClass("sel");
            
            $("#xs").css("display","none");
            $("#xj").css("display","none");
            $("#md").css("display","none");
            $("#yk").css("display","none");
            $("#wy").css("display","block");
        }
        
    }
    
    function changeDate(){
        var date = $("#date").val();
        $.post("/depot/charge/manageDate",{"date":date},function(data){
            $("#theOrder").html(data);
            changeOrder(check);
        }); 
    }
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>收费记录</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
        <!--收费信息开始-->
        <div class="pay_record">
            <div class="select_dates">
                <p class="select_1"><input type="date" style="-webkit-appearance:none;height:30px;width:90%;font-size:0.8em;padding-left:5%;" id="date" onChange="changeDate();" <#if theDate??>value="${theDate?string("YYYY-MM-dd")}"</#if>></p>
            </div>
            <div class="payment_situation">
                <a href="javascript:changeOrder(1);" id="xs_label" style="width:20%" class="a1 sel">线上</a>
                <a href="javascript:changeOrder(2);" id="xj_label" style="width:20%" class="a1">现金</a>
                <a href="javascript:changeOrder(3);" id="md_label" style="width:20%" class="a1">免单</a>
                <a href="javascript:changeOrder(4);" id="yk_label" style="width:20%" class="a1">月卡</a>
                <a href="javascript:changeOrder(5);" id="wy_label" style="width:20%" class="a1">违约</a>
            </div>
            
            <div id="theOrder">
                <#include "/depot/manage_detail.ftl">
            </div>
            
        </div>
        <!--收费信息结束-->
        <div class="mb98"></div>
    </div>
    
    <!--底部开始-->
    <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2" href="/depot/myaccount">账户</a>
        <a class="a3 sel" href="/depot/myaccount/chargeManage">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->

</body>
</html>
