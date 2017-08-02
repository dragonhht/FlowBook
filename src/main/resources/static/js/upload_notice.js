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

$(document).ready(function () {
    // 提交期望
    $('#upload_btn').click(function () {
        console.log("测试");
        var text = $('#noticeText').val().trim();
        $.post("saveNotice",
            {
                text : text
            },
            function (data) {
                $('#resultMessage').html(data);
            }
        );
    });
})