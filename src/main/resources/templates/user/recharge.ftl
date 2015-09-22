<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />


<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/user/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#rechargeForm").Validform({
            tiptype: 3,
        });
    });
</script>
</head>

<body>

    <div class="header">
        <p>账户充值</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>

    <dl style="margin-bottom:5%;" class="pay_min_01">
        <form id="rechargeForm">
            <input style="height:35px;line-height:35px;width:90%;padding:0 5%;font-size:1em;margin-top:10px;margin-bottom:10px;border-radius:2px;" type="text" datatype="n" id="rechargeMoney" placeholder="充值金额"><br />
            <span class="Validform_checktip Validform_wrong" style="color:red;"></span>      
        </form>
    </dl>

    <div class="pay_way">请选择充值方式</div>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon01.png" /><a>支付宝</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon02.png" /><a>微信</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
        <a>
            <dl class="pay_last">
                <dd><img src="/user/images/pay_icon03.png" /><a>银行卡</a></dd>
                <dt><a><img src="/user/images/aboutus_right.png" /></a></dt>
            </dl>
        </a>
    </div><!--main END-->

</body>
</html>
