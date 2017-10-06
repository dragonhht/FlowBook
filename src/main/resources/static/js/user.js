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

/** 联系. */
function chatLoginTip() {
    var height = $(document).height();
    var width = $(window).width();
    var divWidth = $('#user_chat_div').width();
    $('#out_bg').css('height', height);
    $('#user_chat_div').css('left', (width - divWidth)/2 + "px");
    $('#out_bg').show();
    $('#user_chat_div').show();
}

/** 举报. */
function showReportDiv() {
    var height = $(document).height();
    var width = $(document).width();
    var divWidth = $('#report_div').width();
    $('#out_bg').css('height', height);
    $('#report_div').css('left', (width - divWidth)/2 + "px");
    $('#out_bg').show();
    $('#report_div').show();
}

/** 添加好友. */
function loginTip() {
    $('.alert').html('操作成功').addClass('alert-success').show().delay(2000).fadeOut();
}

$(document).ready(function () {

    /** 点击聊天框外部，隐藏聊天框. */
    $('#out_bg').click(function () {
        $('#user_chat_div').hide();
        $('#out_bg').hide();
        $('#report_div').hide();
    });

    /** 关闭聊天框. */
    $('#close').click(function () {
        var parent = $('#close').parents('#user_chat_div');
        $(parent).hide();
        $('#out_bg').hide();
    });

    /** 关闭举报框. */
    $('#close_report').click(function () {
        var parent = $('#close_report').parents('#report_div');
        $(parent).hide();
        $('#out_bg').hide();
    });

    /** 图片. */
    $('#file-select').fileinput({
        language: 'zh',
        uploadUrl: '#',
        allowedFileExtensions : ['jpg', 'png','gif'],
        showUpload : false,
        showCaption: false,
        dropZoneEnabled: false,
        maxFileCount : 3,
        'elErrorContainer': '#errorBlock'
    });

});

