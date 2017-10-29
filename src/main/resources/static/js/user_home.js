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

    /** 上传图片. */
    $('.ok_btn').click(function () {
        var image = new FormData();
        var file = $('#selectImg')[0].files[0];
        image.append('uploadImg', file);
        $.ajax({
            url : 'updateUserImg',
            type : 'post',
            data : image,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data == 'ok') {
                    location.reload(true);
                }
            },
            error: function (data) {

            }
        });
    });

    /** 校验邮箱. */
    $('#checkEmailBtn').click(function () {
        var newEmail = $('#newEmail').val().trim();
        $.post('checkEmail',
            {
                email : newEmail
            },
            function (data) {
                if (data) {
                    // TODO 校验处理
                }
            });
    });

    /** 修改邮箱. */
    $('#updateEmailBtn').click(function () {
        var oldEmail = $('#oldEmail').val().trim();
        var newEmail = $('#newEmail').val().trim();
        var code = $('#updateEmailCode').val().trim();
        $.post('updateEmail',
            {
                oldEmail : oldEmail,
                newEmail : newEmail,
                code : code
            },
            function (data) {
                if (data == 'ok') {
                    location.reload(true);
                }
            });
    });

    /** 申请管理员. */
    $('#applyBtn').click(function () {
        var text = $('#applyText').val().trim();
        $.post('applyAdmin',
            {
                text : text
            },
            function (data) {
                if (data) {
                    window.location.replace("../msg?flag=applySeccuss");
                } else {
                    $('.alert').html('上传失败，请稍后再试').addClass('alert-danger').show().delay(2000).fadeOut();
                }
            })
    })

});