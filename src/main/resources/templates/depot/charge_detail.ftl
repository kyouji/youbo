<script type="text/javascript">
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
            window.location.href="/depot/charge/cashOrFree?id="+id+"&type="+type;
        }
    }
</script>
<div id="unpayed"> 
    <#if unpayed_list??> 
        <#list unpayed_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <#if item.statusId??>
                        <#switch item.statusId>
                            <#case 1>
                                <p style="color:red">未支付定金</p>
                            <#break>
                            <#case 2>
                                <p style="color:red">已支付定金</p>
                            <#break>
                            <#case 3>
                                <p style="color:red">预约成功</p>
                            <#break>
                            <#case 4>
                                <p style="color:red">正在停车</p>
                            <#break>
                            <#case 5>
                                <p style="color:red">办理出库</p>
                            <#break>
                        </#switch>
                    </#if>
                </dt>
                <dd>
                    <img src="/depot/images/detail_month_01.png" />
                    <span>
                        <#if item.orderTime??>
                            ${item.orderTime?string("yyyy-MM-dd HH:mm")}
                        </#if>
                    </span>
                    <span style="float:right;color:red;">
                        <#if item.totalPrice??&&item.totalPrice gt 0>停车费用：
                            ${item.totalPrice?string("0.00")}
                        </#if>
                    </span>
                </dd>
                <dd><img src="/depot/images/detail_month_02.png" /><span>未完成</span></dd>
            </dl>
            <div class="bespeak_list_btn">
                <#if 5==item.statusId>
                    <#if item.firstPay??&&item.firstPay gt 0>
                        <input type="button" value="现金支付" style="-webkit-appearance:none;background-color:#999999;width:50%;float:left;border-radius:0;">
                    <#else>
                        <input type="button" value="现金支付" onclick="orderFreeOrCash(${item.id?c},1);" style="-webkit-appearance:none;background-color:#00aaef;width:50%;float:left;border-radius:0;">
                    </#if>
                <#else>
                    <input type="button" value="现金支付" style="-webkit-appearance:none;background-color:#999999;width:50%;float:left;border-right:1px #ffffff solid;border-radius:0;">
                </#if>
                <#if 5==item.statusId>
                    <input type="button" value="免除费用" onclick="orderFreeOrCash(${item.id?c},0);" style="-webkit-appearance:none;background-color:#ef0000;width:50%;float:left;border-radius:0;">
                <#else>
                    <input type="button" value="免除费用" style="-webkit-appearance:none;background-color:#999999;width:50%;float:left;border-radius:0;">
                </#if>
            </div>
        </#list>
    </#if>
    </div>
    
    <div id="payed">    
     <#if payed_list??> 
        <#list payed_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">交易完成</p>
                </dt>
                <dd>
                    <img src="/depot/images/detail_month_01.png" />
                    <span>
                        <#if item.orderTime??>
                            ${item.orderTime?string("yyyy-MM-dd HH:mm")}
                        </#if>
                    </span>
                </dd>
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                </dd>
            </dl>
            <div class="bespeak_list_btn">
                <input type="button" value="现金支付" style="-webkit-appearance:none;background-color:#999999;width:50%;float:left;border-right:1px #ffffff solid;border-radius:0;">
                <input type="button" value="免除费用" style="-webkit-appearance:none;background-color:#999999;width:50%;float:left;border-radius:0;">
            </div>
        </#list>
    </#if>
</div>
 <div class="clear40"></div>