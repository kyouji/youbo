<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站名称</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<script type="text/javascript" src="/user/js/jquery-1.9.1.min.js"></script>
<script src="/user/js/Validform_v5.3.2_min.js"></script>

<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    $(document).ready(function(){
        
        //初始化表单验证
        $("#form1").Validform({
            tiptype: 3,
            ajaxPost:true,
            callback: function(data) {
                if (data.code==0)
                {
                    alert("提交成功");
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

<header class="maintop">
    <a href="/user/center" class="main_guide"><img src="/user/images/aboutus_left.png"></a>
    <p>意见反馈</p>
</header>
<div class="main">
    <form action="/user/center/comment" id="form1" method="post">
        <span class="Validform_checktip input1" ></span>
        <textarea class="feedback_btn01 normal" style="color:red;" datatype="*1-255" sucmsg=" "name="content" cols="" rows=""></textarea>
        
        <input class="feedback_btn02" type="submit" value="提交" />
    </form>
</div><!--main END-->








</body>
</html>
