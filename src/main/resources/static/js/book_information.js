/**
 * Created by huang on 17-7-11.
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

    // 显示图书传阅申请
    $('#wantLookLink').click(function () {
        $('#wantLook').show();
    });

    // 提交图书传阅申请
    $('#updateImgBtn').click(function () {
        var bookId = $('#bookId').val().trim();
        var toUserId = $('#toUserId').val().trim();
        var wantSay = $('#wantSay').val().trim();
        $.post('../../user/wantBook',
            {
                bookId : bookId,
                toUserId : toUserId,
                wantSay : wantSay
            },
        function (data) {
            if (data == 'ok') {
                $('#wantLook').hide();
            } else {
                $('#result').html(data);
            }
        })
    });
});

/** 选择信息标签页. */
function selectTab(n) {
    var index = $(n).attr('index');
    var links = $('#nav_tabs').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).removeClass('active');
    }
    $(n).addClass('active');
    if (index == 0) {
        $('#route').hide();
        $('#book_comment').show();
    } else {
        $('#book_comment').hide();
        $('#route').show();
    }

}

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
}