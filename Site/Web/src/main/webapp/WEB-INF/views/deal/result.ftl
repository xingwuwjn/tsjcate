<#import "macro/paging.ftl" as pagingmacro>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/result.css">
    <title>搜索结果</title>
</head>
<body>
<#if categoryList??>
<div class="search_banner">
    <div class="comwidth">
        <!-- 导航栏右侧以及具体分类 -->
        <div class="shopclass result_shopclass">
            <h3>全部分类</h3>
            <#list categoryList as category>
                <div class="shopClass_show result_show">
                    <div>
                        <span class="oldspan"><a href="${ctx}/category/${category.urlName}">${category.name}</a></span>
                        <i></i>
                    </div>
                    <#--详情信息-->
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
</div>
</#if>
<#--搜索结果-->
<div class="search_result">
    <div class="comwidth">
     <#if dealCategory??>
        <div class="menu" id="menu">
            <div>
                <p>${dealCategory.name}</p><!--p标签为段落标签-->
                <#if dealCategory.children??>
                        <ul style="display:block">
                            <#list dealCategory.children as categorychild>
                            <li>${categorychild.name}</li>
                            </#list>
                        </ul>

                </#if>
            </div>
        </div>
        <#if deals??>
            <div class="result_deal">
                <#if deals.rows??>
                <#list deals.rows as deal>
                    <div class="specialty_goods">
                        <div class="goods_iwrap">
                            <div class="goods_image"><a href="#"><img src="${helper.getindexDealimagelist(deal)}"></a></div>
                            <div class="goods_thumb">
                                <div class="proThumb-wrap">
                                    <p><b></b><b></b><b></b></p>
                                </div>
                            </div>
                            <p class="goods_price">
                                <em><b>￥</b>${deal.dealPrice}</em>
                            </p>
                            <p class="goods_title">
                                <a>${deal.dealTitle}
                                </a>
                            </p>
                            <div class="goods_shop"><a>${deal.merchantId}</a></div>
                            <p class="goods_status">
                                <span>月成交<em>334笔</em></span>
                                <span>评价<a>200</a></span>
                            </p>
                        </div>
                    </div>
                </#list>
                </#if>
            </div>
        </#if>
         <#if deals??>
         <@pagingmacro.paging pagingList=deals url="${ctx}/category/${dealCategory.urlName}"/>
         </#if>
     </#if>
    </div>
</div>
<script type="text/javascript" src="${ctx}/js/result.js"></script>
</body>
</html>