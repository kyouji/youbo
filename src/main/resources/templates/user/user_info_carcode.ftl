<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<script src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/common.js"></script>
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script>
    function submitCarcode(){
        var carcode=$("#carcode").val();
        if(!/^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(carcode)){
            alert("请输入正确的车牌号码！");
            return;
        }
        $("#carcodeForm").submit();
    }
</script>
</head>
<body>

    <header class="header">
        <p>修改昵称</p>
        <a href="/user/center/info" class="a4"></a>
        <a class="main_add" href="javascript:submitCarcode();">保存</a>
    </header>
    
    <form id="carcodeForm" action="/user/center/carcode/edit" method="post">
        <div class="main">
            <div class="personal_info_user">
                <input id="carcode" class="btn" type="text" name="carcode" value="${carcode!''}"/ >
                <p >请输入您的车牌号码（车牌号码不匹配会导致预定等功能不可用）</p>
                <a href="javascript:cancel();">X</a>
            </div>
        </div>
    </form>
        <!--main END-->

</body>
</html>
