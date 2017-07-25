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
    $('#search_flag').val($(n).children('a').attr('index').trim());
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
                // $("#back-to-top").fadeIn(1500);
                $("#back-to-top").addClass('back_top');
                $("#back-to-top").show();
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


/** 中部活动轮播图. */
$(function () {
    var container = $('#center_activity');
    var list = $('#center_activity_list');
    var buttons = $('#activity_buttons span');
    var prev = $('#activity_prev');
    var next = $('#activity_next');
    var index = 1;
    var len = 2;
    var interval = 4000;
    var timer;

    function animate (offset) {
        var left = parseInt(list.css('left')) + offset;
        if (offset > 0) {
            offset = '+=' + offset;
        }
    else {
            offset = '-=' + Math.abs(offset);
        }
        list.animate({'left': offset}, 300, function () {
            if(left > -680){
                list.css('left', -660 * len);
            }
            if(left < (-680 * len)) {
                list.css('left', -681);
            }
        });
    }

    function showButton() {
        buttons.eq(index-1).addClass('on').siblings().removeClass('on');
    }

    function play() {
        timer = setTimeout(function () {
            next.trigger('click');
            play();
        }, interval);
    }
    function stop() {
        clearTimeout(timer);
    }

    next.bind('click', function () {
        if (list.is(':animated')) {
            return;
        }
        if (index == 2) {
            index = 1;
        }
        else {
            index += 1;
        }
        animate(-640);
        showButton();
    });

    prev.bind('click', function () {
        if (list.is(':animated')) {
            return;
        }
        if (index == 1) {
            index = 2;
        }
        else {
            index -= 1;
        }
        animate(640);
        showButton();
    });

    buttons.each(function () {
        $(this).bind('click', function () {
            if (list.is(':animated') || $(this).attr('class') == 'on') {
                return;
            }
            var myIndex = parseInt($(this).attr('index'));
            var offset = -640 * (myIndex - index);

            animate(offset);
            index = myIndex;
            showButton();
        })
    });

    container.hover(stop, play);

    play();

});

/** 推荐轮播图. */
$(function () {
    var container = $('#container');
    var list = $('#list');
    var buttons = $('#buttons span');
    var prev = $('#prev');
    var next = $('#next');
    var index = 1;
    var len = 5;
    var interval = 3000;
    var timer;

    function animate (offset) {
        var left = parseInt(list.css('left')) + offset;
        if (offset>0) {
            offset = '+=' + offset;
        }
        else {
            offset = '-=' + Math.abs(offset);
        }
        list.animate({'left': offset}, 300, function () {
            if(left > -185){
                list.css('left', -185 * len);
            }
            if(left < (-185 * len)) {
                list.css('left', -185);
            }
        });
    }

    function showButton() {
        buttons.eq(index-1).addClass('on').siblings().removeClass('on');
    }

    function play() {
        timer = setTimeout(function () {
            next.trigger('click');
            play();
        }, interval);
    }
    function stop() {
        clearTimeout(timer);
    }

    next.bind('click', function () {
        if (list.is(':animated')) {
            return;
        }
        if (index == 5) {
            index = 1;
        }
        else {
            index += 1;
        }
        animate(-185);
        showButton();
    });

    prev.bind('click', function () {
        if (list.is(':animated')) {
            return;
        }
        if (index == 1) {
            index = 5;
        }
        else {
            index -= 1;
        }
        animate(185);
        showButton();
    });

    buttons.each(function () {
        $(this).bind('click', function () {
            if (list.is(':animated') || $(this).attr('class')=='on') {
                return;
            }
            var myIndex = parseInt($(this).attr('index'));
            var offset = -185 * (myIndex - index);

            animate(offset);
            index = myIndex;
            showButton();
        })
    });

    container.hover(stop, play);
    play();
});