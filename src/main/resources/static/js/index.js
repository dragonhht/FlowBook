/**
 * Created by huang on 17-7-4.
 */

/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

/** 选择搜索范围. */
function selectSearchType(n) {
    var lis = $('#search_ul').children('li');
    for (var i = 0; i < lis.length; i++) {
        $(lis[i]).removeClass('active');
    }
    $(n).addClass('active');
    $('#search_flag').val($(n).children('a').html().trim());
}

/** 列表滚动. */
var doscroll = function() {
    var $parent = $('#run_list');
    var $first = $parent.find('li:first');
    var height = $first.height() + 20;
    $first.animate({
        marginTop: -height + 'px'
    }, 500, function () {
        $first.css('marginTop', 0).appendTo($parent);
    });
};

var id = setInterval(function(){doscroll()}, 3000);

/** 公告信息停止循环显示. */
function stopList() {
    clearInterval(id);
}

/** 开始公告显示循环. */
function startList() {
    id = setInterval(function(){doscroll()}, 3000);
}

/** 返回顶部. */
$(document).ready(function() {
    //首先将#back-to-top隐藏
    $("#back-to-top").hide();
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
    $(function() {
        $(window).scroll(function() {
            if ($(window).scrollTop() > 100) {
                $("#back-to-top").fadeIn(1500);
            } else {
                $("#back-to-top").fadeOut(1500);
            }
        });
        //当点击跳转链接后，回到页面顶部位置
        $("#back-to-top").click(function() {
            $('body,html').animate({
                    scrollTop: 0
                },
                500);
            return false;
        });
    });
});
