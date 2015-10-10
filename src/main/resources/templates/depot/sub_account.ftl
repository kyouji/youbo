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
<script>
    function deleteSubAccount(id){
        var check = confirm("确定删除该子账号？");
        if(check){
            window.location.href="/depot/myaccount/delSubAccount?id="+id;
        }
    }
</script>
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>子账户管理</p>
        <a onclick="javascript:history.back(-1);" class="a4"></a>
        <a href="/depot/myaccount/editSubAccount" class="a5">添加</a>
    </div>
    <!--头部结束-->

    <div class="subaccount">
        <!--开始-->
        <#--
        <#if subAccount_list??&&subAccount_list?size gt 0>
            <#list subAccount_list as item>
                <dl class="bankcard cancel_by_dx" style="padding-bottom: 20px;">
                    <dt style="padding:20px 20px 0 20px"><a href="/depot/myaccount/editSubAccount?id=${item.id?c}">账户：${item.username!''}</a></dt>
                    <dt style="padding:20px 20px 0 20px">
                        <a href="/depot/myaccount/editSubAccount?id=${item.id?c}">真实姓名：${item.realName!''}</a>
                    </dt>
                    <a class="cancel" >X</a>
                </dl>
            </#list>
        </#if>
        -->
        <#if subAccount_list??&&subAccount_list?size gt 0>
            <#list subAccount_list as item>
                <div class="list">
                    <a class="L_list" href="/depot/myaccount/editSubAccount?id=${item.id?c}">
                        <dl class="listbg">
                            <dt>${item.realName!'无'}</dt>
                            <dd>${item.username!''}</dd>
                        </dl>
                    </a>
                    <a class="R_list" href="javascript:deleteSubAccount(${item.id?c});">删 除</a>
                </div>
            </#list>
        </#if>
        
        <!--结束-->
        <div class="mb98"></div>
    </div>
    
    <!--底部开始-->
   <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2 sel" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
</body>
</html>
