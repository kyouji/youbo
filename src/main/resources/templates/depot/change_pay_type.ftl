<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Language" content="zh-CN">
    <title>优泊天下-计时模式</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="copyright" content="" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <!--css-->
    <link href="/depot/css/base.css" rel="stylesheet" type="text/css" />
    <link href="/depot/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        function submitPrice(){
            <#if site??&&site.isCamera>
            
            </#if>
                
            <#if site??&&!site.isCamera>
                var radio = document.getElementById("noCamera_time_pay");
                console.debug(radio)
                if(radio.checked){
                    var dayBaseTime = document.getElementById("noCamera_dayBaseTime").value;
                    var dayBasePrice = document.getElementById("noCamera_dayBasePrice").value;
                    var dayHourPrice = document.getElementById("noCamera_dayHourPrice").value;
                }else{
                    var dayOncePrice = document.getElementById("noCamera_dayOncePrice").value;
                }
            </#if>
        }
    </script>
</head>

<body>
    <!--头部开始-->
    <div class="header">
        <p>计时模式</p>
        <a href="javascript:history.go(-1);" class="a4"></a>
    </div>
    <!--头部结束-->
    
    <div class="ybtx">
        <!--计时模式开始-->
        <div class="watch_mode">
            <#if site??&&site.isCamera>
                <ul>
                    <li class="li_1">
                        <p class="night">白天</p>
                        <p class="times"><span>08:00</span>-<span>20:00</span></p>
                    </li>
                    <li class="li_2">
                        <p>
                            <b class="b1"><input type="radio" />&nbsp;计时</b>
                            <b>前</b>
                            <b><input /></b>
                            <b>小时</b>
                            <b><input /></b>
                            <b>元</b>
                        </p>
                        <p>
                            <b class="b1 b1_1"><input type="radio" />&nbsp;计时</b>
                            <b>后</b>
                            <b><input /></b>
                            <b>元/小时</b>
                        </p>
                        <p class="p2">
                            <b class="b1"><input type="radio" />&nbsp;计次</b>
                            <b class="b1_1">前</b>
                            <b><input /></b>
                            <b>元/次</b>
                        </p>
                    </li>
                </ul>
            <ul>
                <li class="li_1">
                    <p class="night">夜间</p>
                    <p class="times"><span>20:00</span>-<span>08:00</span></p>
                </li>
                <li class="li_2">
                    <p>
                        <b class="b1"><input type="radio" />&nbsp;计时</b>
                        <b>前</b>
                        <b><input /></b>
                        <b>小时</b>
                        <b><input /></b>
                        <b>元</b>
                    </p>
                    <p>
                        <b class="b1 b1_1"><input type="radio" />&nbsp;计时</b>
                        <b>后</b>
                        <b><input /></b>
                        <b>元/小时</b>
                    </p>
                    <p class="p2">
                        <b class="b1"><input type="radio" />&nbsp;计次</b>
                        <b class="b1_1">前</b>
                        <b><input /></b>
                        <b>元/次</b>
                    </p>
                </li>
            </ul>
        </#if>
        
        <#if site??&&!site.isCamera>
            <ul class="ul3">
                <li class="li_2">
                    <p>
                        <b class="b1"><input id="noCamera_time_pay" name="noCamera_pay_type" <#if site.dayType==0>checked=""</#if> type="radio" />&nbsp;计时</b>
                        <b>前</b>
                        <b><input id="noCamera_dayBaseTime" <#if site.dayType==0>value="${site.dayBaseTime!''}"</#if>/></b>
                        <b>小时</b>
                        <b><input id="noCamera_dayBasePrice" <#if site.dayType==0&&site.dayBasePrice??>value="${site.dayBasePrice?string("0.00")}"</#if> /></b>
                        <b>元</b>
                    </p>
                    <p>
                        <b class="b1 b1_1"><input type="radio" />&nbsp;计时</b>
                        <b>后</b>
                        <b><input id="noCamera_dayHourPrice" <#if site.dayType==0&&site.dayHourPrice??>value="${site.dayHourPrice?string("0.00")}"</#if>/></b>
                        <b>元/小时</b>
                    </p>
                    <p class="p2">
                        <b class="b1"><input id="noCamera_count_pay" name="noCamera_pay_type" <#if site.dayType==1>checked=""</#if> type="radio" />&nbsp;计次</b>
                        <b class="b1_1">前</b>
                        <b><input id="noCamera_dayOncePrice" <#if site.dayType==1&&site.dayOncePrice??>value="${site.dayOncePrice?string("0.00")}"</#if>/></b>
                        <b>元/次</b>
                    </p>
                </li>
            </ul>
        </#if>
        
        <ol>
        <li><input type="button" onClick="submitPrice();" style="-webkit-appearance:none;" value="确 定" /></li>
        </ol>
        </div>
        <!--计时模式结束-->
        <div class="mb98"></div>
    </div>
    
    <!--底部开始-->
    <div class="footer">
        <a class="a1" href="/depot">主页</a>
        <a class="a2" href="/depot/myaccount">账户</a>
        <a class="a3" href="/depot/myaccount/chargeManage">收费记录</a>
        <a class="a4" href="/depot/info">车场信息</a>
        <a class="a5 sel" href="/depot/site">设置</a>
    </div>
    <!--底部结束-->
</body>
</html>
