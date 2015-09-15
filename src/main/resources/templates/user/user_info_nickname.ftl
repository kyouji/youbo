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

</head>
<script type="text/javascript">
$(document).ready(function(){
    searchTextClear(".btn","昵称","#999","#000");

});

    function submitNickname(){
        var nick = $("#nickname").val();
        if(10<nick.length){
            alert("昵称长度不能超过10位！");
            return;
        }
        if("昵称"==nick){
            return;
        }
        if(""==nick){
            return;
        }
        $("#nicknameForm").submit();
    }
    
    function cancel(){
        $("#nickname").val("");
    }
</script>

<body>

    <header class="header">
        <p>修改昵称</p>
        <a href="/user/center/info" class="a4"></a>
        <a class="main_add" href="javascript:submitNickname();">保存</a>
    </header>
    
    <form id="nicknameForm" action="/user/center/nickname/edit" method="post">
        <div class="main">
            <div class="personal_info_user">
                <input id="nickname" class="btn" type="text" name="nickname" value="${nickname!''}"/ >
                <p >昵称为1-10个字符，可由中英文、数字组成</p>
                <a href="javascript:cancel();">X</a>
            </div>
        </div>
    </form>
        <!--main END-->

</body>
</html>
