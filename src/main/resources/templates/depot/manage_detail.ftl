<div id="xs"> 
    <#if xs_list??> 
        <div class="money_today">
            <p>当日线上金额</p>
            <span>￥<#if xsAll??>${xsAll?string("0.00")}<#else>0.00</#if></span>
        </div>  
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
                        <#if item.totalPrice??&&item.totalPrice gt 0>￥${item.totalPrice?string("0.00")}</#if>
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
        <div class="money_today">
            <p>当日现金金额</p>
            <span>￥<#if xjAll??>${xjAll?string("0.00")}<#else>0.00</#if></span>
        </div>
        <#if diyUser_list??>
            <#list diyUser_list as item>
                <div class="rich05_people">
                    <p>${item.realName!''}</p>
                    <span>￥<#if item.allash??>${item.allash?string("0.00")}<#else>0.00</#if></span>
                </div>
            </#list>
        </#if>
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
                        <#if item.totalPrice??&&item.totalPrice gt 0>￥
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
                    <span>泊车员：${item.operator!'无'}</span>
                </dd>
            </dl>
        </#list>
    </#if>
</div>

<div id="md"> 
    <#if md_list??> 
        <div class="money_today">
            <p>当日免单</p>
            <span>${md_list?size!'0'}笔</span>
        </div>
        <#if diyUser_list??>
            <#list diyUser_list as item> 
                <div class="rich05_people">
                    <p>${item.realName!''}</p>
                    <span>${item.mdNum!'0'}笔</span>
                </div>
            </#list>
        </#if>
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
                        <#if item.totalPrice??&&item.totalPrice gt 0>￥
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
                    <span>泊车员：${item.operator!'无'}</span>
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
                        <#if item.totalPrice??&&item.totalPrice gt 0>￥
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
            <div class="money_today">
                <p>当日违约金额</p>
                <span>￥<#if wyAll??>${wyAll?string("0.00")}<#else>0.00</#if></span>
            </div>
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
                        <#if item.totalPrice??&&item.totalPrice gt 0>￥
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