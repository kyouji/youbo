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
<script>
    function orderFreeOrCash(id,type){
        if(null==id||null==type){
            alert("参数错误，操作失败！");
            return;
        }
        var message = null;
        if(1==type){
            message = '确认现金支付？';
        }
        if(0==type){
            message = '确认免除费用？'; 
        }
        
        var check = confirm(message);
        if(check){
            window.location.href="/depot/charge/cashOrFree?id="+id+"&type="+type+"?re=true";
        }
    }
</script>
</head>

<body>

    <!--头部开始-->
    <header class="header">
        <p>订单详情</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
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
                    <dt>
                        <#if user??>
                            <p>${user.username!''}</p>
                        </#if>
                    </dt>
                </dl>        
                <dl class="park_last">
                    <dd><span>会员昵称</span></dd>
                    <dt>
                        <#if user??>
                            <p>${user.nickname!''}</p>
                        </#if>
                    </dt>
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
                                    <#case 0>线上支付<#break>
                                    <#case 1>现金支付<#break>
                                    <#case 2>免除费用<#break>
                                    <#case 3>月卡用户<#break>
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
                    <dd><span>审核状态</span></dd>
                    <dt><p>${order.checkStatus!''}</p></dt>
                </dl>
                <dl class="park_last">
                    <dd><span>订单备注</span></dd>
                    <dt><p>${order.remarkInfo!''}</p></dt>
                </dl>
            </div>
        <!--停车场信息结束-->
        </#if>
        <div style='height:30px;' class='clear60'></div>
        <div class="mb98"></div>
        <footer class="profoot">
            <table>
                <tr>
                    <#switch order.statusId>
                        <#case 4>
                            <td><input type="submit" onclick="orderFreeOrCash(${order.id?c},1);" value="现金支付" /></td>
                        <#break>
                        <#default>
                            <td><input type="submit" style="background:#999999;" value="现金支付" /></td>
                        <#break>
                    </#switch>
                    <td>&nbsp;</td>
                    <#switch order.statusId>
                        <#case 4>
                            <td><input type="button" style="background-color:#ef0000;" onclick="orderFreeOrCash(${order.id?c},0);" value="免除费用" /></td>
                        <#break>
                        <#default>
                            <td><input type="submit" style="background:#999999;" value="免除费用" /></td>
                        <#break>
                    </#switch>
                </tr>
            </table>
        </footer>
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
