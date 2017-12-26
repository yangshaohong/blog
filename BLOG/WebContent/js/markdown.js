function parse(input, flag) {
	var stack = new Array();
	var output = "";
	if (!input) {
        var input = document.getElementById("text").innerHTML;		// 获取输入框里面的内容
	}
    var li = -1;                // -1当前没有列表，0表示一级列表，1表示2级列表，2表示3级列表
	input = input.split('\n');								// 将输入框内容按换行符割裂成数组

	for (var i = 0, len = input.length; i < len; i++) {
		var msg = oneLine(input[i], stack[stack.length-1], li);
		if (msg.stack) {
			if ((msg.stack=="</pre>" && stack[stack.length-1]=="<pre>") || msg.stack=="</ul>") {
				//delete stack[stack.length-1];
				stack.pop();
			} else {
				stack.push(msg.stack);
			}
		}
		li =  msg.li;
		output += msg.html;
	}
	if (flag == true) {
		return output;
	}
	var show = document.getElementById("text");
	show.innerHTML = output;
}

function parse_(text) {

}

function oneLine(input, flag, li) {				// 该函数负责检查行首语句，检查完之后检查行内语句
	var res= {
		stack : null,
		html : "",
        li: -1,
	};
	if (flag == "<pre>") {					// 1 检查代码块语句
		var reg = /^(```)$/g;	
		if (input.match(reg)) {
			res.stack = "</pre>";
			res.html = "</ol></pre></div>";
		} else {
			input = input.replace(/(\<)/g, '&lt;');
			input = input.replace(/(\>)/g, '&gt;');
			input = cpp_color(input);
			input = "<li>" + input + "&nbsp;</li>";
			//console.log(input);
			res.html = input;// + "\n";
		}
		if (input[0] == '\t') {
		    alert('table');
        }
		return res;
	} else {
		var reg = /^(```){1}[^`]*/g;		// '*'指匹配0次或者多次
		if (input.match(reg)) {
			res.stack = "<pre>";
			res.html = "<div class='div_pre'><pre style=''><ol type='1'>";
			return res;
		} 
	}
	
	var reg = /^[\#]+/g;					// 2 检查标题语句
	if (input.match(reg)) {
		var high = input.match(reg);
		var len = high[0].length;
		res.html += "<h"+len+">";
		res.html += inLine(input.substring(len, input.length));
		res.html += "</h" + len + ">";
		return res;
	}

	var reg = /^\t*[\*\-\+]{1}/g;			// 3 检查列表语句
	if (input.match(reg)) {
	    var t_num = 0;
        var li_begin = input.match(reg)[0].length;
        if ('\t' == input[0]) {
            t_num = input.match(/^(\t*)/g)[0].length;          // 获取行首制表符数量
        }
		if (flag == '<ul>' && t_num == li) {
			res.html += ("</li><li>" + inLine(input.substring(li_begin, input.length)) + "&nbsp;");
		} else if (t_num < li) {
		    for (var j = 0; j < li-t_num; j++)  res.html += "</ul>";
            res.html += ("</li><li>" + inLine(input.substring(li_begin, input.length)) + "&nbsp;");
        } else if (t_num > li) {
		    if (li >= 0) res.html += "</li><ul>";
            else res.html += "<div class='div_li'><ul class='ul_1'>";
            res.html += ("<li>" + inLine(input.substring(li_begin, input.length)) + "&nbsp;");
        }
		if (li == -1) {
            res.stack = "<ul>";
        }
		res.li = t_num;
        return res;
	}
	if (flag == '<ul>') {
	    res.html += "</li>";
	    for (var j = 0; j < li+1; j++) res.html += "</ul>";
	    res.html += "</div>";
		res.stack = "</ul>";
	}
	res.html += ("<p>" + inLine(input) + "</p>");
	return res;
}

function inLine(line) {							// 搜寻<img>,<a>和<code>标签
	var res = "";
	reg = /(\!*\[[^\]\[]+\]\([^\(\)]+\)|((```)[^`]+(```)))/g;
	var ret = line.match(reg);
	if (ret) {
		index = line.search(reg);
		res += line.substring(0, index);
	}
	if (ret && ret[0][0] == '!') {					//
		reg = /\[[^\]\[]+\]/g;
		var name = ret[0].match(reg);				// 生成连接文字
		name = name[0].substring(1, name[0].length-1);
		reg = /\([^\(\)]+\)/g;
		var src = ret[0].match(reg);				// 生成真实url连接
		src = src[0].substring(1, src[0].length-1);
		res += "<div class='div_img'><img src=\'" + src + "\'></div>";
		return res;
	}
	//reg = /(\[[^\]\[]+\]\([^\(\)]+\))/g;			// 搜寻<a>标签
	//var ret = line.match(reg);
	if (ret && ret[0][0] == '[') {
		reg = /\[[^\]\[]+\]/g;
		var link = ret[0].match(reg);				// 生成连接文字
		link = link[0].substring(1, link[0].length-1);
		reg = /\([^\(\)]+\)/g;
		var url = ret[0].match(reg);				// 生成真是url连接
		url = url[0].substring(1, url[0].length-1);
		res += "<a href=\'" + url + "\'>" + link + "</a>";
		return res;
	}
	if (ret && ret[0][0] == '`') {
		reg = /(```)[^`]+(```)/;
		var code = ret[0].match(reg);
		code = code[0].substring(3, code[0].length-3);
		res += "<code style='background-color:#FFDF9B; padding: 1px 5px 1px spx;'>" + code + "</code>";
		return res;
	}
	return line;
}

function cpp_color(line) {
	var reg = /[W_]{0}(int)[W_]{0}|(void)|(uint32_t)|(uint64_t)|(double)|(long long)|(float)|(char)|(class)|(struct)|(if)|(while)|(else)|(for)|(witch)|(case)|(using)|(namespace)/g;
	line = line.replace(reg, '<font color="#00B9C2">$&</font>');
	
	var reg = /(\#include)/g;
	line = line.replace(reg, '<font colot="#A1A8B0">$&</font>');

	var reg = /([\/]{2,}[^]*)|((\/\*)[^]*(\*\/))/g;
	line = line.replace(reg, '<font color="#20B228">$&</font>');

	var reg = /[\{\}]/g
    line = line.replace(reg, '<font style="font-weight: 600; padding: 0px 3px 0px 3px;" color="#FF7414">$&</font>');

    var reg = /[\(\)]/g
    line = line.replace(reg, '<font style="font-weight: 600; padding: 0px 3px 0px 3px;" color="#C718B8">$&</font>');

    line = line.replace(/\t/g, '    ');

	return line;
}
