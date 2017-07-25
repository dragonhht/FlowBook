/**
 * Created by huang on 17-7-13.
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

/** 选择信息标签页. */
function selectTab(n) {
   var index = $(n).attr('index');
   var links = $('#nav_tabs').children('li');
   for (var i = 0; i < links.length; i++) {
       $(links[i]).removeClass('active');
   }
    $(n).addClass('active');
    console.log(index);
    if (index == 1) {
        $('#contribution').hide();
        $('#notice').hide();
        $('#borrow').show();
    } else {
        if (index == 0) {
            $('#borrow').hide();
            $('#notice').hide();
            $('#contribution').show();
        } else {
            $('#borrow').hide();
            $('#contribution').hide();
            $('#notice').show();
        }
    }

}