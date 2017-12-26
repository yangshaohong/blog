/**
 * @param obj
 * @param direction
 * @param distence
 * @param move_speed
 * @param show_speed
 * @constructor
 */
function ShowMove(obj, direction, distence, show_speed, move_speed) {
    //console.log(parseInt(obj.style.top.match(/[0-9]+/g)[0]));
    this.old_top = obj.style.top ? parseInt(obj.style.top.match(/\+?\-?[0-9]+/g)[0]) : parseInt(obj.style.marginTop.match(/\+?\-?[0-9]+/g)[0]);
    this.old_left = obj.style.left ? parseInt(obj.style.left.match(/\+?\-?[0-9]+/g)[0]) : parseInt(obj.style.marginLeft.match(/\+?\-?[0-9]+/g)[0]);
    this.old_opa = parseFloat(obj.style.opacity);
    this.timer_show = null;
    this.timer_hide = null;
    this.show = function(func) {
        var current_left = obj.style.left ? parseInt(obj.style.left.match(/\+?\-?[0-9]+/g)[0]) : parseInt(obj.style.marginLeft.match(/\+?\-?[0-9]+/g)[0]);
        var dis = distence - (current_left - this.old_left);
        obj.style.visibility = "visible";
        var timer_hide = this.timer_hide;
        var timer_show = setInterval(function() {
            clearInterval(timer_hide);
            if (this.action == "hide") {
                clearInterval(timer);
            }
            var opa = obj.style.opacity ? parseFloat(obj.style.opacity) : 0.7;
            var left = obj.style.left ? parseInt(obj.style.left.match(/\+?\-?[0-9]+/g)[0]) : parseInt(obj.style.marginLeft.match(/\+?\-?[0-9]+/g)[0]);
            var top = obj.style.top ? parseInt(obj.style.top.match(/\+?\-?[0-9]+/g)[0]) : parseInt(obj.style.marginTop.match(/\+?\-?[0-9]+/g)[0]);
            if (opa < 1) {
                obj.style.opacity = (opa+show_speed+"");
            }
            if (dis > 0) {
                dis -= 2;
                if (direction == 0) {
                    obj.style.left ? obj.style.left = left - 2 + "px" : obj.style.marginLeft = left - 2 + "px";
                } else if (direction == 1) {
                    obj.style.left ? obj.style.left = left + 2 + "px" : obj.style.marginLeft = left + 2 + "px";
                } else if (direction == 2) {
                    obj.style.top ? obj.style.top = top - 2 + "px" : obj.style.marginTop - 2 + "px";
                } else if (direction == 3) {
                    obj.style.top ? obj.style.top = top + 2 + "px" : obj.style.marginTop + 2 + "px";
                }
            }
            if (dis <= 0 && opa >= 1) {
                func();
                clearInterval(timer_show);
            }
        }, move_speed);
        this.timer_show = timer_show;
    };
    this.hide = function(func) {
        var old_opa = this.old_opa;
        var old_left = this.old_left;
        var old_top = this.old_top;
        var timer_show = this.timer_show;
        var timer_hide = setInterval(function() {
            clearInterval(timer_show);
            var opa = parseFloat(obj.style.opacity);
            var left = parseInt(obj.style.left.match(/\+?[0-9]+/g)[0]);
            var top = parseInt(obj.style.top.match(/\+?[0-9]+/g)[0]);
            if (opa > old_opa) {
                obj.style.opacity = (opa-show_speed*1.5+"");
        }
            if (direction == 0 && left < old_left) {
                obj.style.left = left + 2 + "px";
            } else if (direction == 1 && left > old_left) {
                obj.style.left = left - 2 + "px";
            } else if (direction == 2 && top < old_top) {
                obj.style.top = top + 2 + "px";
            } else if (direction == 3 && top > old_top) {
                obj.style.top = top - 2 + "px";
            }
            if (opa <= old_opa && top == old_top && left == old_left) {
                obj.style.visibility = "hidden";
                clearInterval(timer_hide);
            }
        }, move_speed);
        this.timer_hide = timer_hide;
    }
};

/**
 * @param obj
 * @constructor
 */
function ActionStatic(obj) {
    this.obj = obj;
    this.show = function() {
        var obj = this.obj;
        obj.style.visibility = "visible";
    }
    this.hide = function() {
        var obj = this.obj;
        obj.style.visibility = "hidden";
    }
    this.show_slow = function(func) {
        var obj = this.obj;
        console.log(obj);
        obj.style.visibility = "visible";
        obj.style.opacity = 0.3;
        var timer = setInterval(function() {
            var opa = parseFloat(obfunctionj.opacity);
            if (opa < 1) {
                opa += 0.1;
                obj.opacity = opa + "";
            } else {
                func();
                clearInterval(timer);
            }
        }, 30);
    }
    this.hide_slow = function(func) {
        var obj = this.obj;
        var timer = setInterval(function() {
            var opa = parseFloat(obj.opacity);
            if (opa > 0.3) {
                opa -= 0.1;
            } else {
                obj.style.visibility = "hidden";
                func();
                clearInterval(timer);
            }
        }, 30)
    }
}
function show_slow(obj, target_opa) {
    obj.style.visibility = "visible";
    obj.style.opacity = 0.3;
    var timer = setInterval(function() {
        var opa = parseFloat(obj.style.opacity);
        if (opa < target_opa) {
            opa += 0.1;
            obj.style.opacity = opa + "";
        } else {
            clearInterval(timer);
        }
    }, 30);
}
function hide_slow(obj) {
    var timer = setInterval(function() {
        var opa = parseFloat(obj.style.opacity);
        if (opa > 0.3) {
            opa -= 0.1;
            obj.style.opacity = opa + "";
        } else {
            obj.style.visibility = "hidden";
            clearInterval(timer);
        }
    }, 30);
}