/**
 * Created by crazy_mad on 2017/9/6.
 */
function $(arg) {
    console.log(arg[0]);
    if ('#' == arg[0]) {
        arg = arg.slice(1, arg.length);
        console.log(arg);
        return document.getElementById(arg);
    } else {
        console.log(arg);
        return document.getElementsByClassName(arg);
    }
}
