/**
 * Created by huang on 17-7-16.
 */
/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

/** 星评. */
function startLight(n) {
    var index = parseInt($(n).attr('index')) + 1;
    var a = $('#startComment').children('a');
    for (var i = 0; i < index; i++) {
        $($(a[i]).children('img')).attr('src', "../img/light_start.png");
    }
    for (var j = i; j < 5; j++) {
        $($(a[j]).children('img')).attr('src', "../img/dark_start.png");
    }
    $('#bookStart').val(index);
}