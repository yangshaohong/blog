<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>个人博客系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
    <link rel="stylesheet" hreg="${pageContext.request.contextPath}/css/markdown.css">
    <style>
        a {
            color: #17A67B;
        }
        body {
            background-image: url("${pageContext.request.contextPath}/images/test.jpg");
            background-attachment: fixed;
            background-repeat: no-repeat;
            background-size: 100% 100%;
        }
        .item-1 {
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            box-shadow: gray 0px 1px 10px;
            position: relative;
            padding: 10px;
            //padding-bottom: 6px;
            //padding-top: 6px;
        }
        .item-1::before {
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            content: "";
            top: 0px;
            left: 0px;
            background-color: white;
            opacity: 0.6;
            position: absolute;
            height: 100%;
            width: 100%;
            z-index: -2;
        }
        .head-div {
            background-image: url('${pageContext.request.contextPath}/images/head.jpg');
            width: 100px;
            height: 100px;
            border-radius: 5px;
            box-shadow: black 0px 0px 10px;
        }
        #brief {
            position: relative;
            border-radius: 5px;
        }
        #brief::before {
            position: absolute;
            content: "";
            top: 0px;
            left: 0px;
            border-radius: 5px;
            width: 100%;
            height: 100%;
            background-color: white;
            opacity: 0.85;
            z-index: -1;
            //-webkit-filter: blur(3px);
        }
        #tick-text {
            -webkit-transform:rotate(5deg);
            position: absolute;
            top: 90px;
            left: 0px;
            padding: 40px;
            font-size: 17px;
            font-family: KaiTi;
            font-weight: 600;
        }
        .item-2 {
            width: 100%;
            padding: 5px;
            position: relative;
            border-radius: 5px;
            margin-left: 50px;
            opacity: 0;
            margin-bottom: 30px;
        }
        .item-2::before {
            content: "";
            position: absolute;
            left: 0px;
            top: 0px;
            background-color: white;
            border-radius: 5px;
            opacity: 0.85;
            height: 100%;
            width: 100%;
            z-index: -1;
        }
        img {
            max-width: 100%;
        }
        .category {
            padding: 3px 3px 3px 3px;
            color: white;
            background-color: gray;
            position: relative;
        }
        .category::before {
            content: "";
            position: absolute;
            top: 0px;
            left: -3px;
            height: 0px;
            width: 0px;
            border-left: 10px solid transparent;
            border-right: 10px solid gray;
        }
    </style>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/markdown.js"></script>
    <script>
        var categorys;
        function getCategory() {
            $.ajax({
                url: "getcategory",
                success: function(res){
                    categorys = res;
                    console.log(categorys);
                }
            });
        }
        function login() {
            var account = $("#account").val();
            var password = $("#password").val();
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
        function showLoginModal() {
            $("#login-modal").show();
            $("#login-modal-back").show();
        }
        function closeLoginModal() {
            $("#login-modal").hide();
            $("#login-modal-back").hide();
        }
        function showItems() {
            var items = $(".item-2");
            var j = 1;
            var delayTime = 100;
            items.eq(0).animate({marginLeft: "0px", opacity: 1});
            for (var i = 1; i < items.length; i++) {
                setTimeout(function() {
                    console.log(j);
                    items.eq(j++).animate({marginLeft: "0px", opacity: 1});
                }, delayTime+=100);
            }
        }
        function createItems() {
            var itemBorder = $("#item-border");
            var data;
            $.ajax({
                url: "list",
                success: function(res) {
                	console.log(typeof res);
                	res = JSON.parse(res);
                    console.log(res);
                    var itemBorder = $("#item-border");
                    for (var i = res.length-1; i >= 0; i--) {
                        console.log(res[i].abstract_);
				     	var md = parse(res[i].abstract_, true);
                        var html = "<h4><a href='show?blogid=" + res[i].blogid +  "'>" + res[i].title + "</a></h4>";
                        html += "<p style='padding-left: 10px; margin: 0px; color: gray; font-size: 10px;'>" + res[i].pdate + "</p>";
                        html = "<div class='text item-2'>" + html + md + "</div>";
                        itemBorder.append(html);
                    }
                    showItems();
                }
            })
        }
        $(function() {
            getCategory();
            createItems();
        })
    </script>
</head>
<body style="margin: 0px; padding: 0px;">
<div style="text-align: center; padding: 5px; padding-bottom: 10px; background-color: black;">
    <a href="#" style="text-decoration: none;" id="index-title" onclick="showLoginModal()">
        <h1 style="font-family: Arial; color: #E0E0E0; font-weight: 900; margin: 0px;">JSP'S BLOG</h1>
    </a>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-3" style="">
            <div class="item-1" style="">
                <font class="category">默认</font>
            </div>
        </div>
        <div class="col-xs-6" style="">
            <div id="item-border" class="item-1" style="">
            </div>
        </div>
        <div class="col-xs-3" style="">
            <div class="item-1" style="">
                <div style="text-align: center;">
                    <div class="head-div" style="margin: auto;"></div>
                    <div id="brief" style="padding: 10px; margin-top: 10px;">
                        <h3 style="margin: 0px; margin-top: 10px;">JAVA WEB课程小组</h3>
							<br /><br />组员：王震之、陆鸿鑫、杨绍红、周晨约、陈家乐
                    </div>
                </div>
                <div style="position: relative">
                    <img src="${pageContext.request.contextPath}/images/tick.png" style="width: 100%; position: absolute; top: 0px; left: 0px;"/>
                    <div id="tick-text">
                        学习是为了啥，还不是为了吃喝玩乐？
                        <div style="text-align: right">
                            —— CRAZY_MAD
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="padding-top: 10px; text-align: center; background-color: #383733; height: 80px; width: 100%; margin-top: 100px;">
    <p style="color: #BAB7AA; font-weight: 600;">
    Copyright &copy; 2016-2017 by crazymad<br/>
    mail: crazy_mad01@163.com or 2116913961@qq.com<br/>
    NBUT CS-154
    </p>
</div>
<div id="login-modal" class="c_modal">
    <h2 style="text-align: center; font-weight: bold; color: #38B2B2; text-shadow: 0px 0px 1px #38B2B2;">登录</h2>
    <hr style="margin-top: 10px; margin-bottom: 10px; background-color: #464646;"/>
    <div style="padding: 10px 30px 10px 30px; margin-top: 50px;" class="input-group">
        <span class="input-group-addon">账号</span>
        <input type="text" class="form-control" id="account"/>
    </div>
    <div style="padding: 10px 30px 10px 30px;" class="input-group">
        <span class="input-group-addon">密码</span>
        <input type="password" class="form-control" id="password"/>
    </div>
    <div style="padding: 10px 30px 10px 30px; float: right;" class="input-group">
        <input type="submit" class="btn btn-primary" value="提交" onclick="login()">&nbsp;
        <input type="submit" class="btn btn-default" value="取消" onclick="closeLoginModal()">
    </div>
    <div style="clear: both;"></div>
    <a target="blank" href="register" style="margin-left: 30px;">没有账号，可以注册一个试试</a>
</div>
<div id="login-modal-back" class="c_modal_back" onclick="closeLoginModal()">
</div>
</body>
</html>
