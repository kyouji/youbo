<div id="xs"> 
    <#if xs_list??> 
        <#list xs_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">线上支付</p>
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
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                    <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                </dd>
                <dd>
                    <span>泊车员：无</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>

<div id="xj"> 
    <#if xj_list??> 
        <#list xj_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">现金支付</p>
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
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                    <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                </dd>
                <dd>
                    <span>泊车员：${item.operator!''}</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>

<div id="md"> 
    <#if md_list??> 
        <#list md_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">免除费用</p>
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
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                    <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                </dd>
                <dd>
                    <span>泊车员：${item.operator}</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>

<div id="yk"> 
    <#if yk_list??> 
        <#list yk_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">月卡用户</p>
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
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                    <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                </dd>
                <dd>
                    <span>泊车员：无</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>

<div id="wy"> 
    <#if wy_list??> 
        <#list wy_list as item>  
            <dl class="detail_month_01">
                <dt>
                    <span>${item.carCode!''}</span>
                    <p style="color:red">违约</p>
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
                <dd>
                    <img src="/depot/images/detail_month_02.png" />
                    <#if item.finishTime??>
                        <span>${item.finishTime?string("yyyy-MM-dd HH:mm")}</span>
                    </#if>
                    <span style="float:right;"><a href="/depot/myaccount/detail?orderId=${item.id?c}" style="color:#999;">详情</a></span>
                </dd>
                <dd>
                    <span>泊车员：无</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>
    
 <div class="clear40"></div>