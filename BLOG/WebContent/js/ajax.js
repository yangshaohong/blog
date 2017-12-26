/**
 * Created by crazy_mad on 2017/9/2.
 */

function Ajax(json, TYPE, url, func) {
    var ajax;
    if (window.XMLHttpRequest) {
        ajax = new XMLHttpRequest();
    } else {
        ajax = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var jsonstr = JSON.stringify(json);
    ajax.onreadystatechange = function() {
        if (4 == ajax.readyState && 200 == ajax.status) {
            console.log(ajax.responseText);
            func(ajax.responseText);
        }
    }
    ajax.open(TYPE, url, true);
    ajax.setRequestHeader("Content-type","application/json");
    ajax.send(jsonstr);
}
var upload_file = function(file, url, func) {
    var ajax;
    if (window.XMLHttpRequest) {
        ajax = new XMLHttpRequest();
    } else {
        ajax = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var formData = new FormData();
    console.log(file);
    formData.append('file', file);
    ajax.onreadystatechange = function() {
        if (4 == ajax.readyState && 200 == ajax.status) {
            console.log(ajax.responseText);
            func(ajax.responseText);
        }
    }
    ajax.open("POST", url, true);
    ajax.send(formData);
}
