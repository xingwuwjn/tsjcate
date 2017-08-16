<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link rel="stylesheet" type="text/css" href="${ctx}/css/login.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/normalize.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特色街登录</title>
</head>
<body>
<div class="logo_login">
    <a href="index.ftl"><img src="${ctx}images/tsj.png" alt="返回主页"></a>
    <h3>欢迎登录</h3>
</div>
<!-- 中间登录注册主体部分 -->
<div class="login_middle">
<!-- 广告位 -->
   <div class="adtm">
        <a href="#"></a>
    </div>
<!-- 登录，注册切换 -->
<div class="loginpart">
    <div class="login_register">
       <a href="javascript:void(0)" onclick="login()">账户登录</a>
       <a href="javascript:void(0)" onclick="register()">用户注册</a>
    </div>
<!-- 登录 -->
   <div class="login">
    
     <div class="username_div"><i></i><input type="text" placeholder="用户名/已验证手机" name="username"></div>
     <div class="password_div"><i></i><input type="password" placeholder="密码" name="password"></div>
     <div class="autologin_div">
        <input type="checkbox" class="auto_login"><label>自动登录</label>
        <a href="revise.jsp">忘记密码？</a>
     </div>
     <div class="loginerror_message"></div>
     <div class="login_buttondiv">
       <input type="button" class="login_button" value="登  录">
     </div>
    
     <div class="login_other">
       <label>第三方登录：</label>
       <a href="#"><img src="resource/images/login_image/QQ.png"></a>
       <a href="#"><img src="resource/images/login_image/16.png"></a>
       <a href="#"><img src="resource/images/login_image/wx.png"></a>
       
     </div>
     
   </div>
<!-- 注册 -->
   <div class="register">
     <div class="register_username"><label>用户名</label><input type="text" placeholder=""><p style="display:none"></p></div>
     <div class="register_firstpw"><label>密码</label><input type="password" placeholder=""><p style="display:none"></p></div>
     <div class="register_password"><label>确认密码</label><input type="password" placeholder=""><p style="display:none">密码不一致</p></div>
     <div class="phone_div"><label>手机号</label><input class="phone_number" type="text"><p style="display:none">手机号不正确</p></div>
     <div class="code"><label>手机验证码</label><input type="text"><input class="code_button" type="button" value="发送验证码"></input><p></p></div>
     <div class="register_checkbox"><input type="checkbox" class="register_message" checked="checked"><a href="#">《用户注册协议》</a><span style="display:none"></span></div>
     <div class="register_button"><input type="button" value="注册"></div>
   </div>
  </div>
</div>
<div class="redirectURL" style="display: none"><%=request.getParameter("redirectURL")%></div>
<div class="from" style="display: none"><%=request.getParameter("register")%></div>
<div class="copyright">
 <div class="copyright_1"><p> <a href="#">网站简介</a>|<a href="#">网站公告</a>| <a href="# ">招纳贤士</a>| <a href="#">联系我们</a>|客服热线：400-675-1234
	 </p>
   <p>Copyright © 2006 - 2014 网站版权所有   京ICP备09037834号   京ICP证B1034-8373号   某市公安局XX分局备案编号：123456789123</p>
  </div>
	<div class="copyright_2">
	     <ul class="copyright_ul">
	     <li><a href="#"><img src="${ctx}/images/7.gif"></a></li>
	     <li><a href="#"><img src="${ctx}/images/7.gif"></a></li>
	     <li><a href="#"><img src="${ctx}/images/7.gif"></a></li>
	     <li><a href="#"><img src="${ctx}/images/7.gif"></a></li>
	     </ul>
	</div>
</div>
<script type="text/javascript" src="${ctx}/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="${ctx}/js/login.js"></script>

</body>
</html>