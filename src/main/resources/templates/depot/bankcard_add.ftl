<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-银行卡</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
<script src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
  $(document).ready(function(){
      $("#form1").Validform({
            tiptype: 3,
            ajaxPost:true,
            callback: function(data) {
                if (data.code==0)
                {
                    window.location.reload();
                }
                else
                {
                    alert(data.message);
                }
            }
        });
        $("#Validform_msg").hide();
});
</script>
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>银行卡</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--银行卡开始-->
    <div class="bank">
    <form id="form1" action="/depot/myaccount/bankcard/save" method="post">
        <select>
            <option value="account">开户银行</option>
            <#if paytape_list??>
            <#list paytape_list as item>
            <option value="${item.code!''}">${item.title!''}</option>
            </#list>
            </#if>
        </select>
        <p class="bank_mes Validform_checktip">请填写以下信息用于验证身份</p>
            <input class="bank_02"  name="name" placeholder="持卡人" datatype="*"  nullmsg="持卡人姓名不能为空" />
            <input class="bank_03"  name="idNumber" placeholder="身份证号码" nullmsg="身份证号不能为空" errormsg="请输入正确的身份证号" />
            <input class="bank_05"  name="cardNumber" placeholder="银行卡号" nullmsg="银行卡号不能为空" />
            <input class="bank_06"  name="mobile" placeholder="银行预留手机号" nullmsg="银行预留手机号不能为空" />
        
        
        
        <dl class="bank_04">
            <dt><input class="bank_07" type="" placeholder="验证码" /></dt>
            <dd><input type="submit" value="获取验证码" /></dd>
        </dl>
        <input class="bank_sure" type="submit" value="确认绑定">
    </form>
    </div>
    <!--银行卡结束-->

    <div class="mb98"></div>
    
    </div>
    
    <!--底部开始-->
    <div class="footer">
    <a class="a1" href="/depot">主页</a>
    <a class="a2 sel" href="/depot/myaccount">账户<span></span></a>
    <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
    <a class="a4" href="/depot/info">车场信息<span></span></a>
    <a class="a5" href="/depot/site">设置<span></span></a>
</div>
    <!--底部结束-->
    
</body>
</html>
