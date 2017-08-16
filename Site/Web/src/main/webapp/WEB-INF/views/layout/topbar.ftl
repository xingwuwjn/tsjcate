<div class="topbar">
    <div class="comwidth">
        <div class="welcom">特色街欢迎你!</div>
        <#if username??>
            <div class="gotologin"><a href="">${username}</a></div>
            <div class="quit"><a href="">退出</a></div>
        <#else>
            <div class="gotologin"><a href="">登陆</a></div>
            <div class="gotoregister"><a href="">注册</a></div>
        </#if>
        <div class="myorder"><a>我的订单</a></div>
        <div class="mycenter">
            <div class="person"><a >个人中心</a></div>
            <div class="mycenter_detail">
                <ul>
                    <li><a>我的优惠券</a></li>
                    <li><a>我的实名认证</a></li>
                    <li><a>我收藏的商品</a></li>
                    <li><a>账户安全</a></li>
                    <li><a>我的积分</a></li>
                </ul>
            </div>
        </div>
        <div class="customer_service">
            <a>客服服务</a>
            <div class="service_detail">
                <ul>
                    <li><a>在线客服</a></li>
                    <li><a>客服机器人</a></li>
                    <li><a>帮助中心</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>