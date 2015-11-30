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
<script type="text/javascript">
    function send(){
        var content = $("#content").val();
        var title = $("#title").val();
        if(""==title||title.length < 4||title.length > 20){
            alert("请输入长度在4-25之间的标题！");
            return;
        }
        if(""==content){
            alert("请输入您的意见！");
            return;
        }
        window.location.href="/user/center/comment/save?title="+title+"&content="+content;
    }
</script>


<body ng-app="app">

<div class="header">
        <p>意见反馈</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="#" class="a5"></a>
</div>
<div class="main" ng-controller="ctrl">
    <form name="consultForm" ng-submit="submitForm()">
        <div class="consult-title"> 标题：<input type="text" id="title"></div>
        <textarea class="feedback_btn01 normal" name="content" id="content"></textarea>
        
        <input class="feedback_btn02" type="button" onclick="send();" value="提交" />
    </form>
</div><!--main END-->

</body>
</html>
