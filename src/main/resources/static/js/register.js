/** 切换验证码. */
function changImg() {
    $('#codeImg').attr('src','http://localhost:8080/FlowBook/tourist/captcha_image');
}

$(document).ready(function () {

    /** 学号校验下一步. */
    $('#stuIdNext').click(function () {
        $('#title_0').removeClass('active');
        $('#title_1').addClass('active');
        $('#check_stuId').hide();
        $('#account_information').show();
    });

    /** 用户基本信息下一步. */
    $('#userMessageNext').click(function () {
        $('#title_1').removeClass('active');
        $('#title_2').addClass('active');
        $('#account_information').hide();
        $('#check_phone').show();
    });

    /** 绑定手机下一步. */
    $('#checkPhoneNext').click(function () {
        $('#title_2').removeClass('active');
        $('#title_3').addClass('active');
        $('#check_phone').hide();
        $('#check_email').show();
    });

    /** 绑定邮箱下一步. */
    $('#checkEmailNext').click(function () {
        $('#title_3').removeClass('active');
        $('#title_4').addClass('active');
        $('#check_email').hide();
        $('#register_success').show();
    });

});

