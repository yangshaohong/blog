<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
  <title>edit blog</title>
  <style>
    .a_bt:hover {
      color: gray;
      cursor:pointer;
    }
  </style>
  <script src="${pageContext.request.contextPath}/js/ajax.js"></script>
  <script src="${pageContext.request.contextPath}/js/edit.js"></script>
  <script>
    var blogid = ${article.id};
    function insert_pic(pic) {
        console.log("select:",pic.innerHTML);
        document.getElementById("text").value += "![images/blog/"+pic.innerHTML+"](images/blog/"+pic.innerHTML+")";
    }
    function refresh_list() {
        Ajax("", "GET", "imageList", function(res) {
            var list = JSON.parse(res);
            var html = "";
            for (var i = 0; i < list.length; i++) {
                html += "<a class='a_bt' onclick='insert_pic(this)'>"+ list[i].filename + "</a><br />"
            }
            document.getElementById("show_pic").innerHTML = html;
        })
    }
    window.onload = function() {
        document.getElementById("upload").onclick = function() {
            var file = document.getElementById("file").files[0];
            var json = {
                filename: file.name,
            };
            var get = "imageList?confirm=" + file.name;
            Ajax(null, "GET", get, function(text) {
                if ("yes" == text) {                  // 意即有同名文件
                    if (true == confirm("图片库中有同名文件，是否直接覆盖？")) {
                        upload_file(file, "upload", function(text) {
                            if ("success" == text) {
                                alert("上传成功");
                                refresh_list();
                            }
                        })
                    } 
                } else {            // 没有同名文件
                    upload_file(file, "upload", function(text) {
                        if ( "success" == text) {
                            alert("上传成功");
                            refresh_list();
                        }
                    })
                }
            });
            console.log(file);
        }
        document.getElementById("insert").onclick = refresh_list;
        document.getElementById("submit").onclick = function() {
          //alert("submit");
            var title = document.getElementById("title").value;
            var text = document.getElementById("text").value;
            var abstract_ = document.getElementById("abstract").value;
            console.log(title, text);
            var json = {
                title: title,
                text: text,
                blogid: ""+blogid,
                abstract_: abstract_
            };

            var res = Ajax(json, "POST", "edit", function(res) {
                if ("success" == res) {
                    alert("上传成功");
                } else if ("data illegal" == res) {
                    alert("缺少必要数据");
                } else if ("not login" == res) {
                    alert("尚未登录");
                }
            });
        }
    }
  </script>
</head>
<body>
<div style="margin: 10px 200px 0 200px">
  <font>title</font> <br/>
  <input value="${article.title }" type="text" id="title" style="width: 100%; border: 1px solid gray"/> <br />
  <font>text</font> <br/>
  <textarea id="text" style="width: 100%; height: 500px; padding: 0 0 0 0;">${article.md }</textarea> <br />
  <font>abstract</font> <br/>
  <textarea id="abstract" style="width: 100%; height: 100px; padding: 0 0 0 0;">${article.abstract_ }</textarea> <br />
  <input type="submit" id="submit" value="submit" style="width: 100%;"/> <br />
  <input type="file" id="file" style="width: 50%;"><input type="submit" id="upload" value="upload picture" style="width: 50%;"/>
  <input type="submit" id="insert" value="insert picture" style="width: 100%;">
  <div id="show_pic"></div>
</div>
</body>
</html>
