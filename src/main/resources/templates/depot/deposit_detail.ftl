<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN">
<title>优泊天下-订单详情</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!--css-->
<link href="/depot/css/common.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>

    <!--头部开始-->
    <header class="header">
        <p>提现详情</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </header>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--停车场信息开始-->
        <#if deposit??>
            <div class="park">
                <dl class="park_last">
                    <dd><span>提现单号</span></dd>
                    <dt><p>${deposit.num!''}</p></dt>
                </dl>        
                <dl class="park_last">
                    <dd><span>提现金额</span></dd>
                    <dt>
                        <#if deposit.money??>
                            <p>${deposit.money?string("0.00")}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>车库名称</span></dd>
                    <dt><p>${deposit.diyName!''}</p></dt>
                </dl>
                <dl class="park_last">
                    <dd><span>提交时间</span></dd>
                    <dt>
                        <#if deposit.depositDate??>
                            <p>${deposit.depositDate?string("yyyy-MM-dd HH:mm")}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>审核情况</span></dd>
                    <dt>
                        <#if deposit.isOperate??&&deposit.isOperate==true>
                            <p>通过</p>
                        <#elseif deposit.isOperate??&&deposit.isOperate==false>
                            <p>未通过</p>
                        <#else>
                            <p>还未审核</p>
                        </#if>
                    </dt>
                </dl>
                <#--
                <dl class="park_last">
                    <dd><span>审核时间</span></dd>
                    <dt>
                        <#if deposit.operateTime??>
                            <p>${deposit.operateTime?string("yyyy-MM-dd HH:mm")}</p>
                        </#if>
                    </dt>
                </dl>
                -->
                <dl class="park_last">
                    <dd><span>是否打款</span></dd>
                    <dt>
                        <#if deposit.isRemit??&&deposit.isRemit==true>
                            <p>是</p>
                        <#elseif deposit.isRemit??&&deposit.isRemit==false>
                            <p>否</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>打款时间</span></dd>
                    <dt>
                        <#if deposit.remitTime??>
                            <p>${deposit.remitTime?string("yyyy-MM-dd HH:mm")}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>订单备注</span></dd>
                    <dt><p>${deposit.remark!''}</p></dt>
                </dl>
            </div>
        <!--停车场信息结束-->
        </#if>
        <div style="width:100%;height:100px;"></div>
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
