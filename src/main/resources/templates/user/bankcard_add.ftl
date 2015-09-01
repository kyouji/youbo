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
<script src="/user/js/common.js"></script>
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
  $(document).ready(function(){
   searchTextClear(".bank_02","持卡人","#999","#000");
   searchTextClear(".bank_03","身份证号码","#999","#000");
   searchTextClear(".bank_05","银行卡号","#999","#000");
   searchTextClear(".bank_06","银行预留手机号","#999","#000");
   searchTextClear(".bank_07","验证码","#999","#000");
});
</script>
</head>

<body>

<header class="maintop">
    <a onclick="javascript:history.back(-1);" class="main_guide"><img src="/user/images/aboutus_left.png"></a>
    <p>添加银行卡</p>

</header>
<div class="main">
    <div class="bank">
        <dl class="bank_01">
            <dt><a>开户银行</a></dt>
            <dd><a><img src="/user/images/bank_guide.png"/></a></dd>
        </dl>
        <p class="bank_mes">请填写以下信息用于验证身份</p>
        
        <input class="bank_02"  type="" placeholder="持卡人"/ >
        <input class="bank_03"  type="" placeholder="身份证号码"/ >
        <input class="bank_05"  type="" placeholder="银行卡号"/ >
        <input class="bank_06"  type="" placeholder="银行预留手机号"/ >
        
        <dl class="bank_04">
            <dt><input class="bank_07" type="" value="验证码" /></dt>
            <dd><input type="button" placeholder="获取验证码" /></dd>
        </dl>
        <a class="bank_sure">确认绑定</a>
    </div>
</div><!--main END-->








</body>
</html>
