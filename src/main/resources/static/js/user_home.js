/**
 * Created by huang on 17-7-15.
 */
/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

/** 选择信息标签页. */
function selectTab(n) {
    var index = $(n).attr('index');
    var links = $('#nav_tabs').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).removeClass('active');
    }
    $(n).addClass('active');
    if (index == 0) {
        $('#book_now_have').hide();
        $('#book_borrow').hide();
        $('#notice').hide();
        $('#book_contribution').show();
        return;
    }
    if (index == 1) {
        $('#book_contribution').hide();
        $('#book_borrow').hide();
        $('#notice').hide();
        $('#book_now_have').show();
        return;
    }
    if (index == 2) {
        $('#book_contribution').hide();
        $('#book_now_have').hide();
        $('#notice').hide();
        $('#book_borrow').show();
        return;
    }
    if (index == 3) {
        $('#book_contribution').hide();
        $('#book_now_have').hide();
        $('#book_borrow').hide();
        $('#notice').show();
    }
}

/** 显示修改按钮. */
function showBtn(n) {
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('img');
    $(img[0]).show();
}

/** 隐藏修改按钮. */
function hideBtn(n) {
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('img');
    $(img[0]).hide();
}

/** 显示修改框. */
function showUpdateDiv(n) {
    var index = $(n).attr('index');
    if (index == 'name') {
        $('#update_name').show();
        return;
    }
    if (index == 'phone') {
        $('#update_phone').show();
        return;
    }
    if (index == 'email') {
        $('#update_email').show();
    }
}

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
}