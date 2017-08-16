<html>
  <head>
    <meta charset="utf-8">
    <link href="css/login.css" rel="stylesheet">
    <title>特色街登录</title>
    <link rel="icon" href="images/logo_icon.png" type="image/x-icon" />
    <link rel="shortcut icon" href="images/logo_icon.png" type="image/x-icon" />
    <link rel="bookmark" href="images/logo_icon.png" type="image/x-icon" />
  </head>
  <body style="background:url('./images/bg.jpg');">
    <div class="container">
      <form class="form-signin" role="form" method="post" action="${ctx}/main">
        <h2>特色街管理系统v1.0</h2>
        <input type="text" class="form-control" name="name" placeholder="用户名" required autofocus>
        <input type="password" class="form-control" name="password" placeholder="密码" required>
        <#--<label class="checkbox">-->
          <#--<input type="checkbox" value="remember-me"> Remember me-->
        <#--</label>-->
        <button class="btn" type="submit">登陆</button>
      </form>
    </div>
  </div>
  </body>
</html>