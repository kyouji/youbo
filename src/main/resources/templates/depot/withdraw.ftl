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

<script type="text/javascript">
    function pupopen(){
             document.getElementById("bg").style.display="block";
                document.getElementById("popbox").style.display="block" ;
     }
    function pupclose(){
        document.getElementById("bg").style.display="none";
        document.getElementById("popbox").style.display="none" ;
    }
</script>
</head>

<body>

<!--弹窗-->
<div id="bg"></div>
<div id="popbox">
    <p class="fz_1">请输入支付密码，以验证身份</p>
    <p class="password"><input type="password" /></p>
    <p class="yes_no">
        <a href="#"  onclick="pupclose()">关闭</a>
        <a href="#" class="blue"  onclick="pupclose()">确定</a>
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
            <li>
                <p class="p1">储蓄卡</p>
                <p class="p2">
                    <select>
                        <option value="CCB">建设银行(0202)</option>
                        <option value="new">使用新卡提现</option>
                    </select>
                </p>
            </li>
            <li>
                <p class="p1">金额(元)</p>
                <p class="p2"><input type="text" placeholder="当前可提现余额3210000.00元" /></p>
            </li>
        </ul>
        <input class="bank_sure" type="button"  onclick="pupopen()" value="下一步">
    </form>
    </div>
    <!--我的账户结束-->

    <div class="mb98"></div>
    
    </div>
    
    <!--底部开始-->
    <div class="footer">
    <a class="a1" href="/depot">主页</a>
    <a class="a2 sel" href="/depot/myaccount">账户<span></span></a>
    <a class="a3" href="/depot/charge">收费记录<span></span></a>
    <a class="a4" href="/depot/info">车场信息<span></span></a>
    <a class="a5" href="/depot/site">设置<span></span></a>
    </div>
    <!--底部结束-->
    
</body>
</html>
