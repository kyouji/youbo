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
                </dd>
                <dd><img src="/depot/images/detail_month_02.png" /><span>未完成</span></dd>
            </dl>
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
        </#list>
    </#if>
</div>