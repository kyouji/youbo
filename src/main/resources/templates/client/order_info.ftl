<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>车有同盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/common.js"></script>
<script src="/client/js/ljs-v1.01.js"></script>
<script src="/client/js/cart.js"></script>
<script src="/client/js/order_info.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>
<script src="/client/js/jquery.cookie.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>

<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/cytm.css" rel="stylesheet" type="text/css" />
<link href="/client/style/cartoon.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />
<link href="/client/style/bankLogo.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
  $(document).ready(function(){
    menuDownList("top_phone","#top_phonelist",".a1","sel");
    phoneListMore();//单独下拉
    menuDownList("top_order","#top_orderlist",".a4","sel");//顶部下拉
    searchTextClear(".toptext","请输入品牌或商品名称","#999","#666");
    searchTextClear(".bottext","查看所有门店","#fff","#fff");
    checkNowHover("shopping_down","shopping_sel");
    navDownList("navdown","li",".nav_showbox");
    menuDownList("mainnavdown","#navdown",".a2","sel");
    
    $("#form1").Validform({
        tiptype: 1
    });
    
    $("#address").citySelect({
        nodata:"none",
        <#if address?? && address.province??>prov: "${address.province!''}",</#if>
        <#if address?? && address.city??>city: "${address.city!''}",</#if>
        <#if address?? && address.disctrict??>dist: "${address.disctrict!''}",</#if>
        required:false
    }); 
});
</script>
</head>
<body>
<#include "/client/common_header.ftl" />

<div class="main">
    <menu class="car_top">
        <p class="sel" style="z-index:10; width:34%;">我的购物车<i></i></p>
        <p class="sel" style="z-index:8;">我的订单信息<i></i></p>
        <p>支付成功</p>
        <div class="clear"></div>
    </menu>
    
    <div class="clear h30"></div>
    
    <form id="form1" name="form1" action="/order/submit" method="post">
        <#--
        <table class="address_tab">
            <tr>
                <th width="140">
                    <p>收货地址</p>
                    <a class="red" href="javascript:toggleNewAddress();">新增收货地址</a>
                </th>
                <td>
                    <input id="input-address-id" type="hidden" name="addressId" value="" datatype="n" nullmsg="请选择收货地址!"/>
                    <#if user.shippingAddressList?? && user.shippingAddressList?size gt 0>
                        <#list user.shippingAddressList as address>
                            <a href="javascript:;" onclick="javascript:selectAddress(this, ${address.id});">
                                <p>收货人：${address.receiverName!''}</p>
                                <p class="p1">收货地址：${address.province!''}${address.city!''}${address.disctrict!''}${address.detailAddress!''}</p>
                                <p>电话：${address.receiverMobile!''}</p>
                            </a>
                        </#list>
                    </#if>
                    <div class="clear"></div>
                </td>
            </tr>
        </table>
        <div id="addressForm" class="hide">
            <table class="mymember_address">
                  <tbody>
                  <tr>
                    <th>收货人*：</th>
                    <td>
                        <input class="mytext" id="receiverName" value="" type="text">
                        <span>收货人姓名</span>
                    </td>
                  </tr>
                  <tr>
                    <th>地区*：</th>
                    <td>
                      <div id="address">
                      <select id="prov" class="prov" style="width: 100px;"></select>
                      <select id="city" class="city" style="width: 100px;"></select>
                      <select id="dist" class="dist" style="width: 100px;"></select>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th>详细地址*：</th>
                    <td>
                        <input class="mytext" id="detailAdd" value="" type="text">
                    </td>
                  </tr>
                  <tr>
                    <th>邮政编码*：</th>
                    <td>
                        <input class="mytext" id="postcode" value="" type="text">
                    </td>
                  </tr>
                  <tr>
                    <th>联系电话*：</th>
                    <td>
                        <input class="mytext" id="mobile" value="" type="text">
                    </td>
                  </tr>
                  <tr>
                    <th></th>
                    <td><input onclick="javascript:submitAddress();" class="mysub" type="button" value="保存"></td>
                  </tr>
                </tbody>
                </table>
            </div>
        -->
        
        <div class="clear h20"></div>
        
        <section class="order_check fl">
            <h3>选择线下同盟店</h3>
            <select name="shopId" datatype="n" nullmsg="请选择同盟店" errormsg="请选择同盟店">
                <option>请选择</option>
                <#if shop_list??>
                    <#list shop_list as item>
                        <option value="${item.id}">${item.title!''}</option>
                    </#list>
                </#if>
            </select>
        </section>
        <section class="order_check fr" style="width:40%;">
            <h3>选择预约安装时间</h3>
            <input name="appointmentTime" type="text" value="" datatype="*" class="text input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" errormsg="请选择预约安装时间" nullmsg="请选择预约安装时间">
            <a href="javascript:;"><img src="/client/images/content/rl.png" /></a>
        </section>
        <div class="clear h20"></div>        
        <#include "/client/paybox_common.ftl" />
        <div class="clear h10"></div>
        <div class="car_pay">
            <span class="mr10 inblock" style="width:100px;">优惠券</span>
            <select id="couponSelect" name="couponId" onchange="couponChange();">
                <#if coupon_list??>
                    <option value="" fee="0">不使用优惠券</option>
                    <#list coupon_list as item>
                        <option value="${item.id}">${item.typeTitle!''}</option>
                    </#list>
                </#if>
            </select>&nbsp;&nbsp;
            <span class="red">抵用金额：￥10.00</span>
        </div>
        
        <div class="clear h20"></div>
        
        <div class="car_pay">
            <span class="mr10 inblock" style="width:100px;">使用粮草</span>
            <input type="text" name="pointUse" value="0" onchange="pointChange(this, $(this).val(), ${total_point_limit!'0'});" style="width:94px; text-align:right;"/>&nbsp;&nbsp;
            <span class="red">可用粮草：${total_point_limit!'0'}</span>
        </div>
        
        <div class="clear h20"></div>
        
        <h3 class="car_tit">给商城留言：</h3>
        <div class="car_pay">
            <input class="text" name="userMessage" type="text" value="" />
        </div>
        
        <div class="clear h20"></div>
        
        <#assign totalQuantity=0>
        <#assign totalPrice=0>
        <#if selected_goods_list??>
            <#list selected_goods_list as sg>
                <#assign totalQuantity=totalQuantity+sg.quantity>
                <#assign totalPrice=totalPrice+(sg.price*sg.quantity)>
            </#list>
        </#if>
      
        <div class="car_btn">
            <a class="ml20 fc" href="javascript:window.history.back();">返回上一页</a>
            <span>应付总额：商品价格（¥<b id="goodsFee">${totalPrice?string("0.00")}</b>）
                + 支付手续费（¥<b id="payTypeFee">0.00</b>）
                - 粮草抵扣（￥<b id="pointFee">0.00</b>）
                - 优惠券（￥<b id="couponFee">0.00</b>）
                = ￥<b class="red fs18" id="totalFee">${totalPrice?string('0.00')}</b></span>
            <input class="sub" type="submit" value="提交订单" />
        </div>
      
        <div class="clear"></div>
    </form>
</div>

<#include "/client/common_footer.ftl" />

</body>
</html>
