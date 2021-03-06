<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-设置</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
function pupopen(){
    window.location.href = "/depot/site/changePayType";
}
</script>
</head>

<body>

    <!--头部开始-->
    <div class="header">
        <p>设置</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--设置开始-->
    <div class="site">
        <dl class="about_us_min">
            <dt>
                <img src="images/logo.png"  />
                <p>优泊天下（停车场）</p>
            </dt>
        </dl>
        
        <div class="site_us">
            <a href="/depot/myaccount/change/password">
                <dl class="about_us_last">
                    <dd>修改密码</dd>
                    <dt class="arrow"><img src="/depot/images/advance.png" /></dt>
                    <dt><p></p></dt>
                </dl>
            </a>
            <#if diyUser??&&diyUser.roleId??>
                <#if diyUser.roleId==0>
                    <a href="/depot/myaccount/subAccount">
                        <dl class="about_us_last">
                            <dd>子账户管理</dd>
                            <dt><img src="/depot/images/advance.png" /></dt>
                        </dl>
                    </a>
                </#if>
            </#if>
            <a>
                <dl class="about_us_last" onclick="pupopen()">
                    <dd>切换收费模式</dd>
                    <dt class="arrow"><img src="/depot/images/advance.png" /></dt>
                    <dt><p>计时+收费</p></dt>
                </dl>
            </a>
            <a href="tel://${setting.telephone!''}">
                <dl class="about_us_last">
                    <dd>联系客服</dd>
                    <dt class="arrow"><img src="/depot/images/advance.png" /></dt>
                    <dt><p>${setting.telephone!''}</p></dt>
                </dl>
            </a>
        </div>
        <ul class="sign_out">
            <li><a href="/depot/site/exit">退出登录</a></li>
        </ul>
    </div>
    <!--设置结束-->

    <div class="mb98"></div>
        
    </div>
    
    <!--底部开始-->
    <div class="footer">
    <a class="a1" href="/depot">主页</a>
    <a class="a2" href="/depot/myaccount">账户</a>
    <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
    <a class="a4" href="/depot/info">车场信息</a>
    <a class="a5 sel" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
    
</body>
</html>
