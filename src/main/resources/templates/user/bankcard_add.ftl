<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加银行卡</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<script src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/Validform_v5.3.2_min.js"></script>
<script src="/user/js/common.js"></script>
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
  $(document).ready(function(){
      $("#form1").Validform({
            tiptype: 3,
            ajaxPost:true,
            callback: function(data) {
                if (data.code==0)
                {
                    alert("添加成功");
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

<div class="header">
        <p>银行卡</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
</div>

<div class="main">
    <div class="bank">
        <form id="form1" action="/user/center/bankcard/add" method="post">
            <select name="bankCardType" class="bank_01">
            <option value="account">请选择开户银行</option>
            <#if paytape_list??>
            <#list paytape_list as item>
            <option name="bankName" value="${item.title!''}">${item.title!''}</option>
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
                <dd><input type="button" value="获取验证码" /></dd>
            </dl>
            <input class="bank_sure" type="submit" value="确认绑定" />
        </form>
    </div>
</div><!--main END-->







<script type="text/javascript">
$(function(){
    //$(".registerform").Validform();  //就这一行代码！;
        
    /**********************
    传入自定义datatype类型【方式一】;
    $.extend($.Datatype,{
        "z2-4" : /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,4}$/
    });
    **********************/
    
    $(".registerform").Validform({
        tiptype:2,
        datatype:{//传入自定义datatype类型【方式二】;
            "idcard":function(gets,obj,curform,datatype){
                //该方法由佚名网友提供;
            
                var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
                var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
            
                if (gets.length == 15) {   
                    return isValidityBrithBy15IdCard(gets);   
                }else if (gets.length == 18){   
                    var a_idCard = gets.split("");// 得到身份证数组   
                    if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
                        return true;   
                    }   
                    return false;
                }
                return false;
                
                function isTrueValidateCodeBy18IdCard(a_idCard) {   
                    var sum = 0; // 声明加权求和变量   
                    if (a_idCard[17].toLowerCase() == 'x') {   
                        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
                    }   
                    for ( var i = 0; i < 17; i++) {   
                        sum += Wi[i] * a_idCard[i];// 加权求和   
                    }   
                    valCodePosition = sum % 11;// 得到验证码所位置   
                    if (a_idCard[17] == ValideCode[valCodePosition]) {   
                        return true;   
                    }
                    return false;   
                }
                
                function isValidityBrithBy18IdCard(idCard18){   
                    var year = idCard18.substring(6,10);   
                    var month = idCard18.substring(10,12);   
                    var day = idCard18.substring(12,14);   
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
                    // 这里用getFullYear()获取年份，避免千年虫问题   
                    if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
                        return false;   
                    }
                    return true;   
                }
                
                function isValidityBrithBy15IdCard(idCard15){   
                    var year =  idCard15.substring(6,8);   
                    var month = idCard15.substring(8,10);   
                    var day = idCard15.substring(10,12);
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
                    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
                    if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
                        return false;   
                    }
                    return true;
                }
                
            }
            
        }
    });
})
</script>
</body>
</html>
