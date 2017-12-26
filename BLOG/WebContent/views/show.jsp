<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="top.crazymad.entity.Article" %>
<%
	Article article = (Article) request.getAttribute("article");
%>
<!DOCTYPE html>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%=article.getTitle() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/markdown.css" />
    <style>
      #comment_back {
        position: fixed;
        background-color: gray;
        top: 0px;
        left: 0px;
        right: 0px;
        width: 100%;
        height: 100%;
        -webkit-filter: opacity(0.7);
        z-index: 4;
        visibility: hidden;
      }
      #comment_modal {
        z-index: 5;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        margin: 0 auto;
        visibility: hidden;
        width: 60%;
        height: 100%;
        background-color: white;
        padding: 60px 50px 10px 50px;
      }
      #comment_close {
        background-image: url("${pageContext.request.contextPath}/images/close.png");
        position: fixed;
        top: 10px;
        right: 22%;
        width: 32px;
        height: 32px;
      }
      #comment_close:hover {
        cursor: pointer;
      }
      .comment_item {
        width: 100%;
        //border: 1px solid gray;
      }
      .comment_content_out_div {
        border: 1px solid gray;
        padding: 10px;
        border-radius: 5px;
        position: relative;
      }
      .comment_content {
        position: relative;
      }
      .comment_content_out_div::before, .comment_content_out_div::after {
        content: "";
        position: absolute;
        width: 0;
        height: 0;
      }
      .comment_content_out_div::before {
        top: 10px;
        left: -20px;
        border-left: 20px solid transparent;
        border-top: 20px solid black;
      }
      .comment_content_out_div::after {
        top: 11px;
        left: -18px;
        border-top: 18px solid white;
        border-left: 18px solid transparent;
      }
      .main_div {
        box-shadow: 5px 5px 50px #888888;
        -webkit-box-shadow: 0px 0px 15px #8C8C8C;
        padding: 10px 30px 50px 30px;
      }
      #list {
        border: 1px solid gray;
        padding: 10px 10px 10px 20px;
        border-radius: 10px;
        background-color: white;
      }
      .a_link {
        position: relative;
      }
      /*.a_link::before {
        content: "";
        position: absolute;
        top: -10px;
        left: 0px;
        height: 5px;
        width: 100%;
        background-color: red;
      }*/
      /*.a_link:hover::after {
        content: "";
        position: absolute;
        top: 50px;
        left: 0px;
        height: 5px;
        width: 100%;
        background-color: red;
      }*/
      .a_link_row {
        cursor: pointer;
        position: relative;
      }
      .a_link_row::before {
        content: url("${pageContext.request.contextPath}/images/san.png");
        position: absolute;
        top: 1px;
        left: -20px;
        //height: 100%;
        //background-color: blue;
        transition: left 0.5s;
        -moz-transition: left 0.2s; /* Firefox 4 */
        -webkit-transition: left 0.2s; /* Safari 和 Chrome */
        -o-transition: left 0.2s; /* Opera */
        vertical-align:middle;
        display:inline-block;
      }
      .a_link_row:hover::before {
        left: -15px;
      }
      .main_div {
        position: relative;
      }
      .main_div::before {
        content: "";
        left: 0px;
        top: 0px;
        height: 100%;
        width: 100%;
        background-color: white;
        position: absolute;
        z-index: -10;
        opacity: 0.9;
      }
      body {
        font-weight: bold;
      }
    </style>
    <script src="${pageContext.request.contextPath}/js/markdown.js"></script>
    <script src="${pageContext.request.contextPath}/js/ajax.js"></script>
    <script src="${pageContext.request.contextPath}/js/action.js"></script>
    <script>
      var blogid = ${article.id };
      function show_l(event, obj) {
          var child_node = obj.node.getElementsByClassName("menu_item");
          for (var i = 0; i < child_node.length; i++) {
              child_node[i].style.display = "block";
          }
          var opa = parseFloat(obj.node.style.opacity);
          obj.behavior = "show";
          (function() {
              if (opa >= 1) {
                  obj.behavior = "";
                  return;
              } else if ("hide" == obj.behavior) {
                  return;
              } else {
                  opa += 0.05;
                  obj.node.style.opacity = opa;
                  setTimeout(arguments.callee, 20);
              }
          }());
      }
      function hide_low(event, obj) {
          var opa = parseFloat(obj.node.style.opacity);
          obj.behavior = "hide";
          (function() {
              if (opa <= 0.5) {
                  obj.behavior = "";
                  return;
              } else if ("show" == obj.behavior) {
                  return;
              } else {
                  opa -= 0.1;
                  obj.node.style.opacity = opa;
                  setTimeout(arguments.callee, 50);
              }
          }());
      }
      window.onload = function() {
          var menu_back = document.getElementById("menu_back");
          var menu = document.getElementById("show_menu");
          var list = document.getElementById("list");
          var div_list = document.getElementById("div_list");
          var showAction = new ShowMove(list, 1, 50, 0.05, 10);
          var isShow = false;       // 0:无动作,1:show,-1:hide
          menu.addEventListener("mouseover", function() {
              /*if (isShow == 0) {
                  isShow = 1;
                  showAction.show(function() {
                      isShow = 0;
                  });
              }*/
              showAction.show(function() {
                  isShow = 0;
              });
          });
          list.addEventListener("mouseleave", function() {
              /*if (isShow == 0) {
                  isShow = -1;
                  showAction.hide(function() {
                      isShow = 0;
                  });
              }*/
              showAction.hide(function() {
                  isShow = 0;
              });
          });
          parse();
          if (!document.getElementById("delete")) return;         // 确定有删除编辑按钮选项渲染出来
          document.getElementById("delete").onclick = function() {
              var res = confirm("确认删除该文章？");
              if (res == false) return;
              var json = {
                  blogid: blogid,
              };
              Ajax(json, "POST", "edit/delete", function(res) {
                  if ("success" == res) {
                      alert("删除成功");
                  }
              });
          }
      }
      var show_comment = function() {             // 点击“文章评论”，打开评论窗
          var back_comment = document.getElementById("comment_back");
          var modal_comment = document.getElementById("comment_modal");
          show_slow(modal_comment, 1);
          show_slow(back_comment, 0.7);
          var head_div = document.getElementsByClassName("head_div")[0];
          var comment_div = document.getElementsByClassName("comment_content_out_div")[0];
          console.log(head_div);
          var head_action = new ShowMove(head_div, 1, 50, 0.05, 14);
          var comment_action = new ShowMove(comment_div, 0, 100, 0.05, 7);
          head_action.show(function(){});
          comment_action.show(function(){});
      }
      var hide_comment = function() {
          var back_comment = document.getElementById("comment_back");
          var modal_comment = document.getElementById("comment_modal");
          hide_slow(modal_comment);
          hide_slow(back_comment);
      }
    </script>
  </head>
<body style="background-image: url('${pageContext.request.contextPath}/images/test_1.png'); background-attachment:fixed; background-repeat:no-repeat; background-size:100% 100%;">
  <div style="margin: 20px 200px 20px 200px;" class="main_div" >
    <!-- <a href="/index"><img src="/images/title.png" style="width: 500px;"></a> -->
    <div style="text-align: center">
      <h1>${article.title }</h1>
      <font style="font-size:13px;" color="gray">发布时间：${article.PDate }&nbsp;</font>
      <font style="font-size:13px;" color="gray">修改时间：${article.MDate }</font>
    </div>
    <div class="text" id="text" style="margin-top: 50px; font-size: 15px;">${article.md }</div>
    <div style="text-align: right; margin-top: 100px;">
      <%
      	if (session.getAttribute("islogin") != null) {
      		if (((String)session.getAttribute("islogin")).equals("true") == true) {
      			out.println("<a class='a_bt' id='delete' style='text-decoration: none'>删除</a>");
          		out.println("<a class='a_bt' style='text-decoration: none' href='edit?blogid="+request.getParameter("blogid")+"'>编辑</a>");
      		}
      	}	
      %>
    </div>
    <hr/>
    <div style="text-align: center">
      Copyright &copy; 2016-2017 by crazy_mad<br>
      mail: crazy_mad01@163.com or 2116913961@qq.com<br>
    </div>
  </div>
  <div id="menu_back" style="position: fixed; top: 100px; right: 100px; width: 200px; height: 100px;">
    <div id="show_menu" style="z-index: 100; background-image: url('${pageContext.request.contextPath}/images/shi.png')">
    </div>
    <div style="z-index:99; position: absolute; top: 60px; left: 25px; opacity: 0.3; visibility: hidden;" id="list">
      <ul style="list-style: none; padding: 0px; margin: 0px;">
        <li><a href="${pageContext.request.contextPath}" class="a_link_row">返回首页</a></li>
        <li><a href="${pageContext.request.contextPath}/login" class="a_link_row">登录账号</a></li>
        <li><a href="${pageContext.request.contextPath}/register" class="a_link_row">注册账号</a></li>
        <li><a class="a_link_row">设置账号</a></li>
        <li><a class="a_link_row">文章评论</a></li>
      </ul>
    </div>
  </div>
  <div id="comment_back" style="" onclick="hide_comment()">
  </div>
  <div id="comment_modal">
    <div id="comment_close" onclick="hide_comment()"></div>
    <div class="comment_item">
      <div class="row2">
        <div class="head_div" style="opacity: 0.3; margin-top: 0px; float: left; height: 100%; text-align: center; margin-left: 10px;">
            <img src="/images/head/default.png" />
        </div>
      </div>
      <div class="row8">
        <div style="float: left; margin-left: 120px; margin-top: 0px; opacity: 0.3" class="comment_content_out_div">
            <font style="font-size: 15px; color: gray;"><a class="a_link">crazy_mad</a>&nbsp;发表于2017-9-25 23:12</font>
            <div class="comment_content">
                原理什么的不多讲，我也不是很熟，这里就简单写一些实际应用要怎么写，当然也是很简单的demo。
            </div>
          </div>
        </div>
      <div style="clear: both;"></div>
    </div>
  </div>
  <script>
    window.onscroll = function() {
        var t = document.documentElement.scrollTop || document.body.scrollTop;
    }
  </script>
</body>
</html>

