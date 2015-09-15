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
    $(function(){
        changeOrderStatus(1);
    });
    
    function changeOrderStatus(type){
        if(1 == type){
            $("#the_checking").addClass("sel");
            $("#the_false").removeClass("sel");
            $("#the_true").removeClass("sel");
            $("#the_all").removeClass("sel");        
            
            $("#checking_refund").css("display","block");
            $("#false_refund").css("display","none");
            $("#true_refund").css("display","none");
            $("#all_refund").css("display","none");
        }
        if(2 == type){
            $("#the_checking").removeClass("sel");
            $("#the_false").addClass("sel");
            $("#the_true").removeClass("sel");
            $("#the_all").removeClass("sel");        
            
            $("#checking_refund").css("display","none");
            $("#false_refund").css("display","block");
            $("#true_refund").css("display","none");
            $("#all_refund").css("display","none");        
        }
        if(3 == type){
            $("#the_checking").removeClass("sel");
            $("#the_false").removeClass("sel");
            $("#the_true").addClass("sel");
            $("#the_all").removeClass("sel");        
            
            $("#checking_refund").css("display","none");
            $("#false_refund").css("display","none");
            $("#true_refund").css("display","block");
            $("#all_refund").css("display","none");        
        }
        if(4 == type){
            $("#the_checking").removeClass("sel");
            $("#the_false").removeClass("sel");
            $("#the_true").removeClass("sel");
            $("#the_all").addClass("sel");        
            
            $("#checking_refund").css("display","none");
            $("#false_refund").css("display","none");
            $("#true_refund").css("display","none");
            $("#all_refund").css("display","block");        
        }        
    }
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>退款申请</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->

    <div class="ybtx">
        <!--退款申请开始-->
        <div class="refund_request">
            <div class="payment_situation">
                <a href="javascript:changeOrderStatus(1);" id="the_checking" class="a1 sel">待审核</a>
                <a href="javascript:changeOrderStatus(2);" id="the_false" class="a1">未通过</a>
                <a href="javascript:changeOrderStatus(3);" id="the_true" class="a1">已通过</a>
                <a href="javascript:changeOrderStatus(4);" id="the_all" class="a1 ">全部</a>
            </div>
    
            <div id="checking_refund">
                <#if checking_refund??&&checking_refund?size gt 0>
                    <#list checking_refund as item>
                        <dl class="bespeak_list_01">
                            <dt><a>${item.diyTitle!''}</a><p class="sel_3">待审核</p></dt>
                            <dd><img src="/depot/images/bespeak_list_01.png" /><a><#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm")}</#if></a></dd>
                            <dt><b>退款金额</b><p class="red_1">￥<span>1.00</span></p></dt>
                            <dt><b>退款理由：</b><p class="red_1"><span>${item.cancelReason!''}</span></p></dt>
                        </dl>
                        <div class="bespeak_list_btn">
                            <input type="button" class="input_by_dx" value="通过"/ >
                            <input type="button" class="input_by_dx" value="拒绝" style="background-color:#ef0000"/ >
                        </div>
                    </#list>
                </#if>
            </div>
    
            <div id="false_refund">
                <#if false_refund??&&false_refund?size gt 0>
                    <#list false_refund as item>
                        <dl class="bespeak_list_01">
                            <dt><a>${item.diyTitle!''}</a><p>审核未通过</p></dt>
                            <dd><img src="/depot/images/bespeak_list_01.png" /><a><#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm")}</#if></a></dd>
                            <dt><b>退款金额</b><p class="red_1">￥<span>10.00</span></p></dt>
                            <dt><b>退款理由：</b><p class="red_1"><span>${item.cancelReason!''}</span></p></dt>
                            <dt><b>客服回复：</b><p class="red_1"><span>${item.remarkInfo!''}</span></p></dt>
                        </dl>
                        <div class="bespeak_list_btn">
                            <input class="sel_2" type="button" value="退款失败"/ >
                        </div>
                    </#list>
                </#if>
            </div>
    
            <div id="true_refund">
                <#if true_refund??&&true_refund?size gt 0>
                    <#list true_refund as item>
                        <dl class="bespeak_list_01">
                            <dt><a>${item.diyTitle!''}</a><p>审核通过</p></dt>
                            <dd><img src="/depot/images/bespeak_list_01.png" /><a><#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm")}</#if></a></dd>
                            <dt><b>退款金额</b><p class="red_1">￥<span>10.00</span></p></dt>
                            <dt><b>退款理由：</b><p class="red_1"><span>${item.cancelReason!''}</span></p></dt>
                            <dt><b>客服回复：</b><p class="red_1"><span>${item.remarkInfo!''}</span></p></dt>
                        </dl>
                        <div class="bespeak_list_btn">
                            <input class="sel_2" type="button" value="退款成功"/ >
                        </div>
                    </#list>
                </#if>
            </div>
    
            <div id="all_refund">
                <#if all_refund??&&all_refund?size gt 0>
                    <#list all_refund as item>
                        <dl class="bespeak_list_01">
                            <dt><a>${item.diyTitle!''}</a><p>${item.checkStatus!''}</p></dt>
                            <dd><img src="/depot/images/bespeak_list_01.png" /><a><#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm")}</#if></a></dd>
                            <dt><b>退款金额</b><p class="red_1">￥<span>10.00</span></p></dt>
                            <dt><b>退款理由：</b><p class="red_1"><span>${item.cancelReason!''}</span></p></dt>
                            <dt><b>客服回复：</b><p class="red_1"><span>${item.remarkInfo!''}</span></p></dt>
                        </dl>
                        <div class="bespeak_list_btn">
                            <#if item.checkStatus=='审核通过'>
                                <input class="sel_2" type="button" value="退款成功"/ >
                            <#elseif item.checkStatus=='审核未通过'>
                                <input class="sel_2" type="button" value="退款失败"/ >
                            <#else>
                                <input type="button" class="input_by_dx" value="通过"/ >
                                <input type="button" class="input_by_dx" value="拒绝" style="background-color:#ef0000"/ >
                            </#if> 
                        </div>
                    </#list>
                </#if>
            </div>
        </div>
        <!--退款申请结束-->
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
