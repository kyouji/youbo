<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>提现</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/go.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/depot/js/rich_lee.js"></script>
<script type="text/javascript" src="/user/js/user_regist.js"></script>
<script type="text/javascript">

    function GetInput(){//屏蔽非数字和非退格符
    var k = event.keyCode;   //48-57是大键盘的数字键，96-105是小键盘的数字键，8是退格符←
    if ((k <= 57 && k >= 48) || (k <= 105 && k >= 96) || (k== 8)){
        return true;
    } else {
        return false;
    }
}
</script>
<style>
    input{
        -webkit-appearance:none;
    }
</style>
</head>


<body style="background: white;">
    <!--头部开始-->
    <div class="header">
        <p>我的钱包</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    <div class="rich02">
        <dl>
            <dd>
                <span>总金额 : </span>
                <div>￥<#if allMoney??>${allMoney?string("0.00")}<#else>0.00</#if></div>
            </dd>
            <dd>
                <span>银行卡: </span>
                <select style="-webkit-appearance:none;">
                    <#if all_pay_type??>
                        <#list all_pay_type as item>
                            <option value="${item.title!''}">${item.title!''}</option>
                        </#list>                        
                    </#if>
                </select>
            </dd>
            <dd>
                <span>持卡人 : </span>
                <input type="text" name="cardName" id="cardName" />
            </dd>
            <dd>
                <span>卡号 : </span>
                <div>
                    <input onkeydown="return GetInput()" type="text" name="cardNumber" id="cardNumber" />  
                    <span></span>              
                </div>
            </dd>
            <dd>
                <span>金额 : </span>
                <input readonly="true" type="text" name="cardMoney" id="cardMoney" value="<#if income??>${income?string("0.00")}<#else>0.00</#if>" />
            </dd>
            <dd>
                <div>
                    <input type="text" name="" id="" value="验证码 " />
                    <a href="javascript:changeYzm();" style="background:none;margin:0px;"><img id="yzm_image" src="" /></a>
                </div>
                <span></span>
            </dd>
            <dd>
                <input type="" name="" id="" value="手机验证 " />
                <a>发送</a>
                <span></span>               
            </dd>
        </dl>
    </div>
    <#if allMoney??&&allMoney gt 0>
        <input class="bank_sure" style="border-radius:0px;" type="submit" value="确认">
    <#else>
        <input class="bank_sure" style="border-radius:0px;" type="submit" style="background-color:#999999;" value="确认">
    </#if>
    
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
