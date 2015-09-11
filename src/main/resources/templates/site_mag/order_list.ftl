<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>待付款订单</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/order/list/${statusId!"0"}/${type!'0'}" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}

</script>

    <!--导航栏-->
    <div class="location">
        <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a><span>订单管理</span></a>
        <i class="arrow"></i>
            <#if statusId??>
                <#if 0==statusId>
                    <span>全部订单</span>
                <#elseif 1==statusId>
                    <span>定金未支付</span>
                <#elseif 2==statusId>
                    <span>定金已支付</span>
                <#elseif 3==statusId>
                    <span>预约成功</span>
                <#elseif 4==statusId>
                    <span>正在停车</span>
                <#elseif 5==statusId>
                    <span>办理出库</span>
                <#elseif 6==statusId>
                    <span>交易完成</span>
                <#elseif 7==statusId>
                    <span>等待审核</span>
                <#elseif 8==statusId>
                    <span>审核未通过</span>
                <#elseif 9==statusId>
                    <span>交易取消</span>
                </#if>
            </#if>
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
        <div id="floatHead" class="toolbar">
            <div class="l-list">
                <ul class="icon-list">
                    <li>
                        <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
                    </li>
                    <!--
                    <#if statusId?? && 1==statusId>
                    <li>
                        <a onclick="return ExePostBack('btnConfirm','确认后将进入待发货状态，是否继续？');" class="save" href="javascript:__doPostBack('btnConfirm','')"><i></i><span>确认订单</span></a>
                    </li>
                    <#elseif statusId?? && 7==statusId>
                    <li>
                        <a onclick="return ExePostBack('btnDelete','删除后订单将无法恢复，是否继续？');" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除订单</span></a>
                    </li>
                    </#if>
                    -->
                    <#if statusId??&&(6==statusId||5==statusId||0==statusId)>
                        <li>
                            <a class="all"><span>订单总额：￥${totalPrice!0.00}</span></a>
                        </li>
                    </#if>
                    <#if statusId??&&(6==statusId||0==statusId)>
                        <li>
                            <a class="all"><span>实际收入：￥${price!0.00}</span></a>
                        </li>
                    </#if>
                    <!--
                    <li>
                    	<a class="all" href="/Verwalter/order/list/${statusId!''}/1"><span>普通订单</span></a>
                    </li>                  
                    <li>
                    	<a class="all" href="/Verwalter/order/list/${statusId!''}/2"><span>组合购买</span></a>
                    </li>
                    <li>
                    	<a class="all" href="/Verwalter/order/list/${statusId!''}/3"><span>抢购</span></a>
                    </li>
                    <li>
                    	<a class="all" href="/Verwalter/order/list/${statusId!''}/4"><span>十人团</span></a>
                    </li>
                    <li>
                    	<a class="all" href="/Verwalter/order/list/${statusId!''}/5"><span>百人团</span></a>
                    </li>
                    -->
                    <li>
                    	<a class="all" href="javascript:__doPostBack('export','')"><span>导出本页</span></a>
                    
                    </li>
                </ul>
                
                    <div class="rule-single-select">
                        <select name="timeId" onchange="javascript:setTimeout(__doPostBack('btnTime',''), 0)">
                            <option value="0" <#if !time_id?? || time_id==0>selected="selected"</#if>>所有订单</option>
                            <option value="1" <#if time_id?? &&time_id==1>selected="selected"</#if>>今天</option>
                            <option value="2" <#if time_id?? &&time_id==2>selected="selected"</#if>>最近一周</option>
                            <option value="3" <#if time_id?? &&time_id==3>selected="selected"</#if>>最近一个月</option>
                            <option value="4" <#if time_id?? &&time_id==4>selected="selected"</#if>>最近三个月</option>
                            <option value="6" <#if time_id?? &&time_id==6>selected="selected"</#if>>最近半年</option>
                            <option value="12" <#if time_id?? &&time_id==12>selected="selected"</#if>>最近一年</option> 
                        </select>
                    </div>
            </div>
            <div class="r-list">
                <input name="keywords" type="text" class="keyword">
                <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
            </div>
        </div>
    </div>
    <!--/工具栏-->
    <!--列表-->
    
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th width="8%">选择</th>
        <th align="left">订单号</th>
        <th align="left" width="12%">会员账号</th>
        <th align="left" width="8%">支付方式</th>
        <th width="10%">订单状态</th>
        <th width="10%">支付定金</th>
        <th width="10%">总金额</th>
        <th width="11%">下单时间</th>
        <th width="11%">完成时间</th>
        <th width="8%">操作</th>
    </tr>

    <#if order_page??>
        <#list order_page.content as order>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${order_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${order.id?c}">
                </td>
                <td>
                    <a href="/Verwalter/order/edit?id=${order.id?c}&statusId=${statusId!'0'}">${order.orderNumber!""}</a></td>
                <td>${order.username!""}</td>
                <td>${order.payTypeTitle!""}</td>
                <td align="center">
                    <#if order.statusId??>
                        <#switch order.statusId>
                            <#case 1>
                                <span>未支付定金</span>
                            <#break>
                            <#case 2>
                                <span>已支付定金</span>
                            <#break>
                            <#case 3>
                                <span>预约成功</span>
                            <#break>
                            <#case 4>
                                <span>正在停车</span>
                            <#break>
                            <#case 5>
                                <span>办理出库</span>
                            <#break>
                            <#case 6>
                                <span>交易完成</span>
                            <#break>
                            <#case 7>
                                <span>等待审核</span>
                            <#break>
                            <#case 8>
                                <span>审核未通过</span>
                            <#break>
                            <#case 9>
                                <span>交易取消</span>
                            <#break>
                        </#switch>
                    </#if>
                </td>
                <#if order.firstPay??&&order.firstPay gt 0>
                    <td align="center">￥<font color="#C30000">${order.firstPay?string("0.00")}</font></td>
                <#else>
                    <td align="center">未支付定金</td>
                </#if>
                <#if order.totalPrice??&&order.totalPrice gt 0>
                    <td align="center" width="10%">￥<font color="#C30000">${order.totalPrice?string("0.00")}</font></td>
                <#else>
                    <td align="center" width="10%"><font color="#C30000"></font></td>
                </#if>
                <td align="center">${order.orderTime?string("yyyy-MM-dd HH:mm")}</td>
                <#if order.finishTime??>
                    <td align="center">${order.finishTime?string("yyyy-MM-dd HH:mm")}</td>
                <#else>
                    <td align="center">未完成</td>
                </#if>
                <td align="center">
                    <a href="/Verwalter/order/edit?id=${order.id?c}&statusId=${statusId!"0"}">详细</a>
                </td>
            </tr>
        </#list>
    </#if>
</tbody>
</table>
        
<!--/列表-->
<!--内容底部-->
<#assign PAGE_DATA=order_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>
