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
    // console.log("显示");
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('span');
    $(img).show();
}

/** 隐藏修改按钮. */
function hideBtn(n) {
    // console.log("隐藏");
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('span');
    $(img).hide();
}

/** 显示修改框. */
function showUpdateDiv(n) {

    var height = $(document).height();
    $('#out_bg').css('height', height);
    $('#out_bg').show();

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
    $('#out_bg').hide();
    $('#update_img').hide();
}

$(document).ready(function () {

    /** 点击弹出框外部，隐藏弹出框. */
    $('#out_bg').click(function () {
        $('.update_div').hide();
        $('#out_bg').hide();
    });

    /** 显示修改头像. */
    $('.img_div').mouseover(function () {
        $('#chengeImg').show();
    });

    /** 隐藏修改图片. */
    $('.img_div').mouseout(function () {
        $('#chengeImg').hide();
    });

    $('#selectImg').change(function () {
        var files = $('#selectImg')[0].files[0];
        var url = window.URL.createObjectURL(files);
        $('#userImg').attr('src', url);
        $('.change_btn').removeClass('change_btn');
        $('.ok_btn').css('display', 'block');
    });

    $('#chengeImg').hide();

});