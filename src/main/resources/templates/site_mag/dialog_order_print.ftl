<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>打印订单窗口</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<script type="text/javascript">
    //窗口API
    var api = frameElement.api, W = api.opener;
    api.button({
        name: '确认打印',
        focus: true,
        callback: function () {
            printWin();
        }
    }, {
        name: '取消'
    });
    //打印方法
    function printWin() {
        var oWin = window.open("", "_blank");
        oWin.document.write(document.getElementById("content").innerHTML);
        oWin.focus();
        oWin.document.close();
        oWin.print()
        oWin.close()
    }
</script>
</head>
<body style="margin: 0;">
    <form name="form1" method="post" action="dialog_print.aspx?order_no=B15041121411832" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
</div>

<div>

    <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="86EF5C69">
</div>
    <div id="content">
        <table width="800" border="0" align="center" cellpadding="3" cellspacing="0" style="font-size: 12px; font-family: '微软雅黑'; background: #fff;">
            <tbody><tr>
                <td width="346" height="50" style="font-size: 20px;">优泊天下</td>
                <td width="216">订单号：${order.orderNumber!''}<br>日&nbsp;&nbsp; 期：${order.orderTime?string("yyyy-MM-dd")}</td>
                <td width="220">操&nbsp;作&nbsp;人：${manager!''}<br>打印时间：${now?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            <tr>
                <td colspan="3" style="padding: 10px 0; border-top: 1px solid #000;">
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="5" style="font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody>
                            <tr>
                                <td align="left" style="background: #ccc;">车库编号</td>
                                <td width="25%" align="left" style="background: #ccc;">车库名称</td>
                                <td width="20%" align="left" style="background: #ccc;">营业时间</td>
                                <td width="20%" align="left" style="background: #ccc;">详细地址</td>
                                <td width="20%" align="left" style="background: #ccc;">客服电话</td>
                            </tr>
                            <#if diySite??>
                                <tr>
                                    <td>${diySite.id}</td>
                                    <td>${diySite.title}</td>
                                    <td>${diySite.openTimeSpan}</td>
                                    <td>${diySite.address}</td>
                                    <td>${diySite.serviceTele}</td>
                                    <td></td>
                                </tr>
                            </#if>
                        </tbody>
                    </table>
                        
                </td>
            </tr>
            <tr>
                <td colspan="3" style="border-top: 1px solid #000;">
                    <#if user??>
                    <table width="100%" border="0" cellspacing="0" cellpadding="5" style="margin: 5px auto; font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody>
                            <tr>
                                <td width="44%">会员账户：
                                    ${user.username}
                                </td>
                              <#--  <td width="56%">客户姓名：<#if user??>${user.realName!''}</#if><br>          -->                
                                </td>
                                <td width="56%">昵称：${user.nickname!''}<br>                          
                                </td>
                            </tr>
                            <#--
                            <tr>
                                <td valign="top">支付方式：${order.payTypeTitle!''}</td>
                                <td>送货地址：
                                    ${order.shippingAddress!''}<br>
                                </td>
                            </tr>
                            -->
                            <tr>
                                <#--<td valign="top">用户留言：${order.userRemarkInfo!''}</td>-->
                                <td valign="top">电话：${order.shippingPhone!''}</td>
                                <td>车牌：${user.carCode!''}</td>
                            </tr>
                            <#--
                            <tr>
                                <td>车型：${order.carType!''}</td>
                                <td>车牌：${order.carCode!''}</td>
                            </tr>
                            <tr>
                                <td valign="top">备注信息：${order.remarkInfo!''}</td>
                                <td valign="top">发票抬头：${order.invoiceTitle!''}</td>
                            </tr>
                            -->
                        </tbody>
                    </table>
                    </#if>
                    <table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" style="border-top: 1px solid #000; font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody>
                            <tr>
                                <#switch order.statusId>
                                    <#case 1>
                                        <td align="left">订单状态：定金未支付</td> 
                                    <#break>
                                    <#case 2>
                                        <td align="left">订单状态：定金已支付</td> 
                                    <#break>
                                    <#case 3>
                                        <td align="left">订单状态：预约成功</td> 
                                    <#break>
                                    <#case 4>
                                        <td align="left">订单状态：正在停车</td> 
                                    <#break>
                                    <#case 5>
                                        <td align="left">订单状态：办理出库</td> 
                                    <#break>
                                    <#case 6>
                                        <td align="left">订单状态：交易完成</td> 
                                    <#break>
                                    <#case 7>
                                        <td align="left">订单状态：等待审核</td> 
                                    <#break>
                                    <#case 8>
                                        <td align="left">订单状态：审核未通过</td> 
                                    <#break>
                                    <#case 9>
                                        <td align="left">订单状态：交易取消</td> 
                                    <#break>
                                </#switch>
                            </tr>
                            <tr>
                                <#if order.firstPay??&&order.firstPay gt 0>
                                    <td align="left">预付定金：￥${order.firstPay?string("0.00")}</td>
                                <#else>
                                    <td align="left">预付定金：未支付定金</td>
                                </#if>
                                <#if order.totalPrice??&&order.totalPrice gt 0>
                                    <td align="left">停车费用：￥${order.totalPrice?string("0.00")}</td>
                                <#else>
                                    <td align="left">停车费用：暂无</td>
                                </#if>
                            </tr>
                            <tr>
                                <td align="left">下单时间：${order.orderTime?string("yyyy-MM-dd HH:mm")}</td>
                                <#if order.finishTime??>
                                    <td align="left">完成时间：￥${order.finishTime?string("yyyy-MM-dd HH:mm")}</td>
                                <#else>
                                    <td align="left">完成时间：还未完成</td>
                                </#if>
                            </tr>
                            <tr>
                                <td align="left">申请退款：<#if order.isReturn??&&order.isReturn>是<#else>否</#if></td>
                                <td align="left">退款原因：<#if order.cancelReason??>${order.cancelReason!''}</#if></td>
                            </tr>
                            <tr>
                                <td align="left">审核状态：${order.checkStatus!''}</td>
                            </tr>
                            <tr>
                                <td align="left">备注信息：${order.remarkInfo!''}</td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody></table>
    </div>
    </form>


</body></html>