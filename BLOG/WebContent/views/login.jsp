<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>登录</title>
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script>
      window.onload = function() {
          document.getElementById("submit").onclick = function() {
              var account = document.getElementById("account").value;
              var password = document.getElementById("password").value;
              $.ajax({
                  url: "Login",
                  type: "post",
                  data : JSON.stringify({
                      account : account,
                      password : password
                  }),
                  contentType: "application/json; charset=utf-8",
                  success: function(res) {
                      if ("success" == res) {
                          alert("登录成功");
                          closeLoginModal();
                      } else if ("islogin" == res) {
                          alert("当前账户已登录");
                          closeLoginModal();
                      } else {
                          alert("登录失败，账号或密码错误");
                      }
                  }
              });
          }
      }
  </script>
</head>
<body>
<div>
  Account: <input id="account" type="text" /> <br />
  Password: <input id="password" type="password" /> <br />
  <input id="submit" type="submit" value="Login" />
</div>
</body>
</html>
