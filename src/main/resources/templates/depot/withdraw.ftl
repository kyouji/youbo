<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-我的账户</title>
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
        <#-- 验证输入的值是否合法 -->
        var card_money = $("#card_money").val();     
        var card_name = $("#card_name").val();
        var card_bank = $("#card_bank").val();
        var card_number = $("#card_number").val();
        
        if(""==card_money||""==card_name||""==card_bank||""==card_number){
            alert("请输入完整的信息！");
            return;
        }
        
        if(! /^0\.([1-9]|\d[1-9])$|^[1-9]\d{0,8}\.\d{0,2}$|^[1-9]\d{0,8}$/.test(card_money)){
            alert("请输入一个正确的数字（精确度不超过小数点后2位）！");
            return;
        }
        
        document.getElementById("bg").style.display="block";
        document.getElementById("popbox").style.display="block" ;
     }
    function pupclose(){
        document.getElementById("bg").style.display="none";
        document.getElementById("popbox").style.display="none" ;
    }
    function rechargeCheck(){
        var pwd = $("#recharge_pwd").val();
        var card_money = $("#card_money").val();     
        $.post("/depot/myaccount/recharge/check",{
            "pwd":pwd,
            "money":card_money
        },function(res){
            pupclose();
            alert(res.message);
            if(0 == res.status){
                window.location.href = "/depot/myaccount";
            }
        });
    }
</script>
</head>

<body>

<!--弹窗-->
<div id="bg"></div>
<div id="popbox">
    <p class="fz_1">请输入支付密码，以验证身份</p>
    <p class="password"><input id="recharge_pwd" type="password" /></p>
    <p class="yes_no">
        <a href="#"  onclick="pupclose()">关闭</a>
        <a href="#" class="blue"  onclick="rechargeCheck();">确定</a>
    </p>
</div>
<!--弹窗-->

    <!--头部开始-->
    <div class="header">
        <p>我的账户</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--我的账户开始-->
    <div class="account_withdrawals">
    <form>
        <ul>
            <li style="height:60px;line-height:60px;">
            <p class="p1" >总金额</p>
            <P class="p2" style="padding:0 13px; width:60%;font-size:1.4em;color:#ef0000;">${allMoney}</p>
            </li>
            <li>
                <p class="p1">持卡人</p>
                <p class="p2">
                    <input type="text" id="card_name">
                </p>
            </li>
            <li>
                <p class="p1">所属银行</p>
                <p class="p2">
                    <select id="card_bank" name="bankcard">
                        <#if all_pay_type??>
                            <#list all_pay_type as item>
                                <option value="${item.title!''}">${item.title!''}</option>
                            </#list>                        
                        </#if>
                    </select>
                </p>
            </li>
            <li>
                <p class="p1">银行卡号</p>
                <p class="p2">
                    <input type="text" id="card_number">
                </p>
            </li>
            <li>
                <p class="p1">金额(元)</p>
                <p class="p2"><input type="text" id="card_money" name="money" placeholder="当前可提现金额<#if allMoney??>${allMoney?string("0.00")}<#else>0.00</#if>元" /></p>
            </li>
        </ul>
        <#if allMoney??&&allMoney gt 0>
            <input class="bank_sure" style="border-radius:0px;" type="button"  onclick="pupopen();" value="下一步">
        <#else>
            <input class="bank_sure" style="border-radius:0px;" type="button" style="background-color:#999999;" value="下一步">
        </#if>
    </form>
    </div>
    <!--我的账户结束-->

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
