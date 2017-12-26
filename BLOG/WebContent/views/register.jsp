<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="css/index.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>注册</title>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script>
	$(function() {
		$("#submit_").click(function() {
			var account = $("#account").val();
			var password = $("#password").val();
			var again = $("#again").val();
			var codestring = $("#codestring").val();
			if (account == "" || password == "" || again == "" || password != again) {
				alert("信息不全");
			} else if (password.match(/^[0-9a-b_]+$/g) == null) {
				alert("密码只能由英文数字下划线组成");
			} else {
				$.ajax({
	                  url: "Register",
	                  type: "post",
	                  data : JSON.stringify({
	                      account : account,
	                      password : password,
	                      code: codestring
	                  }),
	                  contentType: "application/json; charset=utf-8",
	                  success: function(res) {
	                      if ("success" == res) {
	                          alert("注册成功");
	                      } else if ("exist" == res) {
	                    	  alert("账号已存在");
	                      } else if ("code error" == res) {
	                    	  alert("验证码错误"); 
	                      } else {
	                    	  alert("注册失败");
	                      }
	                  }
	             });
			}
		});
	})
</script>
</head>
<body>
<header>
		<a href="${pageContext.request.contextPath}/images/head.jpg" class="logo"> <h1 style="font-family: Arial; color: #E0E0E0; font-weight: 900; margin: 0px;">BLOG</h1></a>
		<div class="desc">欢迎注册</div>
		</header>
		<section>
			<div id="form" onsubmit="retrun false">
				<div class="register-box">
					<label for="username" class="username_label">用 户 名
					<input id="account" maxlength="20" type="text" placeholder="您的用户名和登录名"/>
					</label>
					<div class="tips">
						
					</div>
				</div>
				<div class="register-box">
					<label for="username" class="other_label">设 置 密 码
					<input id="password" maxlength="20" type="password" placeholder="建议至少使用两种字符组合"/>
					</label>
					<div class="tips">
						
					</div>
				</div>
				<div class="register-box">
					<label for="username" class="other_label">确 认 密 码
					<input id="again" maxlength="20" type="password" placeholder="请再次输入密码"/>
					</label>
					<div class="tips">
						
					</div>
				</div>
				<div class="register-box">
					<label for="username" class="other_label">验 证 码
					<input id ="codestring" maxlength="20" type="text" placeholder="请输入验证码"/>
					</label>
					<img src="code" width="90" height="40" style="margin-top: 10 px" >
					<div class="tips">
					</div>
				</div>
				<div class="submit_btn">
					<button id="submit_">立 即 注 册</button>
				</div>
			</div>
		</section>

</body>
</html>