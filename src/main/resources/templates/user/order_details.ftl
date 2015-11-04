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
<link href="/user/css/common.css" rel="stylesheet" type="text/css" />
<link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
<link href="/user/css/style.css" rel="stylesheet" type="text/css" />
<script src="/user/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script>
function clearing(id,type){
    if(null == id||null == type){
        alert("参数错误，操作失败！");
        return;
    }
    $.post("/user/order/clearing",{"id":id,"type":type},function(res){
        alert(res.message);
        window.location.reload();
    });
}

function cancelOrder(id){
    if(null == id){
        alert("参数错误，操作失败！");
        return;
    }
    $.post("/user/order/detailCancel",{"id":id},function(res){
        alert(res.message);
        window.location.reload();
    });
}
</script>
</head>
    
<body>

    <!--头部开始-->
    <header class="header">
        <p>停车场详情</p>
        <a href="<#if reload??>/user?reload=true<#else>javascript:history.back(-1);</#if>" class="a4"></a>
    </header>
    <!--头部结束-->
    
    <div class="ybtx">
    <!--停车场信息开始-->
        <#if order??>
            <div class="park">
                <#if order.carCodePhoto??>
                    <dl class="park_min">
                        <dt><img src="images/park/park_img.png" /></dt>
                    </dl>
                </#if>
                
                <dl class="park_last">
                    <dd><span>会员账户</span></dd>
                    <dt><p>${user.username!''}</p></dt>
                </dl>        
                <dl class="park_last">
                    <dd><span>会员昵称</span></dd>
                    <dt><p>${user.nickname!''}</p></dt>
                </dl>
                <dl class="park_last">
                    <dd><span>车库名称</span></dd>
                    <dt><p>${order.diyTitle!''}</p></dt>
                </dl>
                <dl class="park_last">
                    <dd><span>订单编号</span></dd>
                    <dt>
                        <#if order??>
                            <p>${order.orderNumber!''}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>车牌号</span></dd>
                    <dt><p>${order.carCode!''}</p></dt>
                </dl>
                <dl class="park_last">
                    <dd><span>下单时间</span></dd>
                    <dt>
                        <#if order.orderTime??>
                            <p>${order.orderTime?string("yyyy-MM-dd HH:mm")}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>订单状态</span></dd>
                    <dt>
                        <#if order.statusId??>
                            <#switch order.statusId>
                                <#case 1>
                                    <p>未支付定金</p>
                                <#break>
                                <#case 2>
                                    <p>已支付定金</p>
                                <#break>
                                <#case 3>
                                    <p>预约成功</p>
                                <#break>
                                <#case 4>
                                    <p>正在停车</p>
                                <#break>
                                <#case 5>
                                    <p>办理出库</p>
                                <#break>
                                <#case 6>
                                    <p>交易完成</p>
                                <#break>
                                <#case 7>
                                    <p>等待审核</p>
                                <#break>
                                <#case 8>
                                    <p>审核未通过</p>
                                <#break>
                                <#case 9>
                                    <p>订单取消</p>
                                <#break>
                            </#switch>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>预付定金</span></dd>
                    <dt>
                        <#if order??&&order.firstPay??&&order.firstPay  gt 0>
                            <p>${order.firstPay?string("0.00")}元</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>停车费用</span></dd>
                    <dt>
                        <#if order??&&order.totalPrice??&&order.totalPrice gt 0>
                            <p>${order.totalPrice?string("0.00")}元</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>完成时间</span></dd>
                    <dt>
                        <#if order.finishTime??>
                            <p>${order.finishTime?string("yyyy-MM-dd HH:mm")}</p>
                        </#if>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>支付方式</span></dd>
                    <dt>
                        <p>
                            <#if order.thePayType??>
                                <#switch order.thePayType>
                                    <#case 1>线上支付<#break>
                                    <#case 1>现金支付<#break>
                                    <#case 1>免除费用<#break>
                                    <#case 1>月卡用户<#break>
                                </#switch>
                            </#if>
                        </p>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>取消原因</span></dd>
                    <dt>
                        <p>${order.cancelReason!''}</p>
                    </dt>
                </dl>
                <dl class="park_last">
                    <dd><span>订单备注</span></dd>
                    <dt><p>${order.remarkInfo!''}</p></dt>
                </dl>
            </div>
        <!--停车场信息结束-->
        </#if>
    <div class="mb98"></div>
    <footer class="profoot">
        <table>
            <tr>
                <#switch order.statusId>
                    <#case 1>
                        <td><input type="submit" onclick="clearing(${order.id?c},1);" value="支付定金：￥${firstPay?string("0.00")}" /></td>
                    <#break>
                    <#case 4>
                        <td><input type="submit" onclick="clearing(${order.id?c},0);" value="结算订单：￥${order.totalPrice?string("0.00")!'0.00'}" /></td>
                    <#break>
                    <#default>
                        <td><input type="submit" style="background:#999999;" value="结算订单" /></td>
                    <#break>
                </#switch>
                <td>&nbsp;</td>
                <#switch order.statusId>
                    <#case 1>
                        <td><input type="button" onclick="cancelOrder(${order.id?c});" value="取消订单" /></td>
                    <#break>
                    <#case 2>
                        <td><input type="button" onclick="cancelOrder(${order.id?c});" value="取消订单" /></td>
                    <#break>
                    <#case 3>
                        <td><input type="button" onclick="cancelOrder(${order.id?c});" value="取消订单" /></td>
                    <#break>
                    <#default>
                        <td><input type="submit" style="background:#999999;" value="取消订单" /></td>
                    <#break>
                </#switch>
            </tr>
        </table>
    </footer>
    
    </div>
</body>
</html>
