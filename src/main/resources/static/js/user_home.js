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
    // console.log("显示");
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('img');
    $(img).show();
}

/** 隐藏修改按钮. */
function hideBtn(n) {
    // console.log("隐藏");
    var btn = $(n).children('.update_btn');
    var img = $(btn).children('img');
    $(img).hide();
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


/** 更新邮箱. */
function updateEmail() {
    var oldEmail = $('#oldEmail').val().trim();
    var newEmail = $('#newEmail').val().trim();
    var updateEmailCode = $('#updateEmailCode').val().trim();
    $.post('updateEmail',
        {
            oldEmail : oldEmail,
            newEmail : newEmail,
            code : updateEmailCode
        },
    function (data) {
        if (data == 'ok') {
            location.reload(true);
        } else {
            $('#emailResult').html(data);
        }
    })
}

/** 校验邮箱. */
function checkEmail() {
    var email = $('#newEmail').val().trim();
    $.post('checkEmail',
        {
            email : email
        });
}

function checkAddEmail() {
    var email = $('#addEmail').val().trim();
    $.post('checkEmail',
        {
            email : email
        });
}

/** 添加邮箱. */
function addEmail() {
    var email = $('#addEmail').val().trim();
    var code = $('#addEmailCode').val().trim();
    $.post('addEmail',
        {
            email : email,
            code : code
        },
    function (data) {
        if (data == 'ok') {
            location.reload(true);
        } else {
            $('#addEmailResult').html(data);
        }
    });
}

$(document).ready(function () {

    // 显示头像修改框
    $('#userImg').click(function () {
        console.log('点击');
        $('#update_img').show();
    });

    // 上传图书
    $('#updateImgBtn').click(function () {
        var file = new FormData($('#imgForm')[0]);
        $.ajax({
            url : 'updateUserImg',
            type : 'post',
            data : file,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data == 'ok') {
                    location.reload(true);
                } else {
                    $('#imgResult').html(data);
                }
            },
            error: function (data) {
                $('#imgResult').html(data);
            }
        });
    });

});