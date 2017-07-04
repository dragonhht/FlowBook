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