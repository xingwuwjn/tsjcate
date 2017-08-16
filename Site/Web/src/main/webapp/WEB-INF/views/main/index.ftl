<#import "macro/common.ftl" as common>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特色街</title>
</head>
<body>
<#if categoryList??>
<div class="search_banner">
    <div class="comwidth">
        <!-- 导航栏右侧以及具体分类 -->

        <div class="shopclass">
            <h3>全部分类</h3>
            <#list categoryList as category>
                <div class="shopClass_show">
                    <div>
                        <span class="oldspan"><a href="${ctx}/category/${category.urlName}">${category.name}</a></span>
                        <i></i>
                    </div>
                    <!-- 家乡味道分类详情 -->
                    <#if category.children??>
                        <div class="shop_detail">
                            <#list category.children as child>
                                <div class="detail_i">
                                    <ul>
                                        <li class="detaili_tittle"><p>${child.name}</p><img src=""></li>
                                        <#if child.children??>
                                            <li><#list child.children as grandson><p>${grandson.name}</p></#list></li>
                                        </#if>
                                    </ul>
                                </div>
                            </#list>
                        </div>
                    </#if>
                </div>
            </#list>

        </div>


        <!-- 导航栏顶部 -->
        <ul class="shopclass_right">
            <li><a>城市大全</a></li>
            <li><a href="about_us.jsp">关于我们</a></li>
            <li><a>加入我们</a></li>
            <li></li>
        </ul>
    </div>
<#--幻灯片部分-->
</div>
</#if>
<!-- 幻灯片部分 -->
<div class="comwidth">
    <div class="banner">
        <i class="leftpoint"></i>
        <i class="rightpoint"></i>
        <div id="banner_img">
            <div class="banner_i" id="banner_{{index}}">
                <img class="banner_img" src="${ctx}/images/{{index}}.jpg">
            </div>
        </div>
        <div class="ctrl" id="ctrl">
            <a class="ctrl_i" id="ctrl_{{index}}">
                <img src="${ctx}/images/{{index}}.jpg">
            </a>
        </div>
    </div>
</div>
<!--首页商品展示模块-->
<#if dealcategorydtos??>
    <div class="deal_warp">
        <div class="comwidth">
            <#list dealcategorydtos as dto>
            <div class="deal">
                <div class="small_banner"><img src="#" style="display:block;width:100%;height:100%;"></div>
                <#if dto.dealCategory??>
                <div class="deal_right">
                    <div class="deal_tittle"><span>${dto.dealCategory.name}</span><span style="float:right;">更多></span></div>
                    <#if dto.first??>
                    <#list dto.first as main_deal>
                    <div class="main_goods">
                        <div class="deal_iwrap">
                            <div class="goods_image"><a href="#"><img src="${helper.getindexDealimagelist(main_deal)}"></a></div>
                            <div class="goods_thumb">
                                <div class="proThumb-wrap">
                                    <p><b></b><b></b><b></b></p>
                                </div>
                            </div>
                            <p class="goods_price">
                                <em><b>￥</b>${main_deal.dealPrice}</em>
                            </p>
                            <p class="goods_title">
                                <a>${main_deal.dealTitle}
                                </a>
                            </p>
                            <div class="goods_shop"><a>${main_deal.merchantId}</a></div>
                            <p class="goods_status">
                                <span>折扣<em>${main_deal.discount}</em></span>
                                <span>评价<a>200</a></span>
                            </p>
                        </div>
                    </div>
                    </#list>
                    </#if>
                </div>
                </#if>
            </div>
            </#list>
        </div>
    </div>

</#if>
</body>
</html>