/**
 * Created by huang on 17-7-6.
 */
/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

var rightUserName = false;
var rightPassword = false;
var rightRepossword = false;
var rightPhone = false;

/** 校验用户名是否存在. */
$(document).ready(function(){
    // 用户名是否存在
    $('#userName').blur(function () {
        var name = $("#userName").val();
        $.post("tourist/test_username" ,
            {
                userName : name
            },
            function (data) {
                if (data) {
                    $('#userNameMessage').html('该用户名不可用');
                    rightUserName = false;
                } else {
                    $('#userNameMessage').html('&nbsp;');
                    rightUserName = true;
                }
            });
    });

    // 密码是否为空
    $('#password').blur(function () {
        var password = $('#password').val();
        if (password.length < 1) {
            $('#passwordMessage').html('密码不能为空');
            rightPassword = false;
        } else {
            $('#passwordMessage').html('&nbsp;');
            rightPassword = true;
        }
    });

    // 手机号码是否符合规则
    $("#userPhone").blur(function () {
        var phone = $('#userPhone').val();
        var ret = /^1[3|4|5|8][0-9]\d{4,8}$/;
        if (ret.test(phone)) {
            $('#phoneMessage').html('&nbsp;');
            rightPhone = true;
        } else {
            $('#phoneMessage').html('请填入正确的手机号码');
            rightPhone = false;
        }
    });

    // 校验两次密码是否一致
    $('#repassword').blur(function () {
        var password = $('#password').val();
        var repassword = $('#repassword').val();
        if (password != repassword) {
            $('#repasswordMessage').html('两次密码不一致');
            rightRepossword = false;
        } else {
            $('#repasswordMessage').html('&nbsp;');
            rightRepossword = true;
        }
    });

    // 判断信息是否填写正确是否
    $('body').mouseover(function () {
        var isClick = false;
        var isChecked=$('#checkbox').is(':checked');
        if (isChecked) {
            isClick = true;
        }
        if (rightRepossword && rightPassword && rightUserName && isClick && rightPhone) {
            $('#register_btn').removeClass('disabled')
        } else {
            $('#register_btn').addClass('disabled')
        }
    });

});

// 判断是否可点击注册
function registerPost() {
    var isClick = false;
    var isChecked=$('#checkbox').is(':checked');
    if (isChecked) {
        isClick = true;
    }
    if (rightRepossword && rightPassword && rightUserName && isClick && rightPhone) {
        console.log("正确");
        return true;
    } else {
        console.log("失败");
        return false;
    }
}

// 切换验证码
function changImg() {
    $('#codeImg').attr('src','tourist/captcha_image');
}