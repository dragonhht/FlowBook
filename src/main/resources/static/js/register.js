/** 切换验证码. */
function changImg() {
    $('#codeImg').attr('src','http://localhost:8080/FlowBook/tourist/captcha_image');
}

$(document).ready(function () {

    /** 判断学号是否已经存在. */
    var idexit = true;
    $('#stuId').blur(function () {
        var numVal = $('#stuId').val().trim();
        $.post('tourist/test_userid',
            {
                userId : numVal
            },
            function (data) {
            idexit = data;
                if (!data) {
                    $('.alert').html('该学号已注册').addClass('alert-danger').show().delay(2000).fadeOut();
                    $('#idDiv').addClass('has-error');
                } else {
                    $('#idDiv').removeClass('has-error');
                }
            })
    });

    /** 学号校验下一步. */
    $('#stuIdNext').click(function () {
        // 判断学号是否为空
        var num = false;
        var numVal = $('#stuId').val().trim();
        if (numVal != null && numVal != undefined && numVal != '') {
            // 判断是否为数字或字母
            var reg = /^[0-9]+$/;
            if(!reg.test(numVal)){
                $('.alert').html('学号不正确').addClass('alert-danger').show().delay(2000).fadeOut();
                return;
            } else {
                num = true;
            }
        } else {
            $('.alert').html('学号不正确').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }
        // 判断姓名是否为空
        var name = false;
        var nameVal = $('#stuName').val().trim();
        if (nameVal != null && nameVal != undefined && nameVal != '') {
            name = true;
        } else {
            $('.alert').html('姓名不正确').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }
        // 判断验证码是否为空
        var code = false;
        var codeVal = $('input[name="verificationCode"]').val().trim();
        if (codeVal != null && codeVal != undefined && codeVal != '') {
            code = true;
        } else {
            $('.alert').html('验证码不能为空').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }

        if (name && name && code && idexit) {

            $.post('tourist/putuserid',
                {
                    userId : numVal,
                    stuName : nameVal,
                    code : codeVal
                },
                function (data) {
                    if (data) {
                        $('#title_0').removeClass('active');
                        $('#title_1').addClass('active');
                        $('#check_stuId').hide();
                        $('#account_information').show();
                    } else {
                        $('.alert').html('验证码错误').addClass('alert-danger').show().delay(2000).fadeOut();
                    }
                });


        }

        var height = $(document).height();
        $('#out_bg').css('height', height);

    });

    /** 用户基本信息下一步. */
    // 用户名是否存在
    var userName = false;
    $('#userMessageNext').click(function () {
        var username = $('#username').val().trim();
        if (!(username != null && username != undefined && username != '')) {
            $('.alert').html('用户名不能为空').addClass('alert-danger').show().delay(2000).fadeOut();
            userName = false;
            return;
        }
        var password = false;
        var passwordVal = $('#password').val().trim();
        if (passwordVal != null && passwordVal != undefined && passwordVal != '') {
            password = true;
        } else {
            $('.alert').html('密码不能为空').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }

        var repassword = false;
        var repasswordVal = $('#repassword').val().trim();
        if (repasswordVal != null && repasswordVal != undefined && repasswordVal != '') {
            if (passwordVal == repasswordVal) {
                repassword = true;
            } else {
                $('.alert').html('两次密码不一致').addClass('alert-danger').show().delay(2000).fadeOut();
                return;
            }
        } else {
            $('.alert').html('两次密码不一致').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }

        if (password && repassword && userName) {
            $.post('tourist/setusername',
                {
                    userName : username,
                    possword : passwordVal,
                    repossword : repasswordVal
                },
                function (data) {
                    if (data) {
                        $('#title_1').removeClass('active');
                        $('#title_2').addClass('active');
                        $('#account_information').hide();
                        $('#check_phone').show();
                    } else {
                        $('.alert').html('两次密码不一致').addClass('alert-danger').show().delay(2000).fadeOut();
                    }
                });
        }
    });

    // 判断用户名是否存在
    $('#username').blur(function () {
        var username = $('#username').val().trim();
        $.post('tourist/test_username',
            {
                userName : username
            },
            function (data) {
                userName = data;
                if (!data) {
                    $('.alert').html('用户名已存在').addClass('alert-danger').show().delay(2000).fadeOut();
                    $('#userNameDiv').addClass('has-error');
                } else {
                    $('#userNameDiv').removeClass('has-error');
                }
            });
    });

    /** 绑定手机下一步. */
    $('#checkPhoneNext').click(function () {

        var phone = false;
        var phoneVal = $('#phone').val().trim();
        var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
        if(!reg.test(phoneVal)){
            $('.alert').html('请输入正确的手机号').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        } else {
            phone = true;
        }

        var code = false;
        var vodeVal = $('#phoneNum').val().trim();
        if (vodeVal != null && vodeVal != undefined && vodeVal != '') {
            code = true;
        } else {
            $('.alert').html('请输入验证码').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }

        $.post('tourist/setphone',
            {
                phone : phoneVal,
                code : vodeVal
            },
            function (data) {
                if (data) {
                    $('#title_2').removeClass('active');
                    $('#title_3').addClass('active');
                    $('#check_phone').hide();
                    $('#check_email').show();
                } else {
                    $('.alert').html('验证码错误').addClass('alert-danger').show().delay(2000).fadeOut();
                }
            });


    });

    /** 获取手机验证码. */
    $('#phoneCodeBtn').click(function () {
        var phone = false;
        var phoneVal = $('#phone').val().trim();
        var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
        if(!reg.test(phoneVal)){
            $('.alert').html('请输入正确的手机号').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        } else {
            phone = true;
        }
        if (phone) {
            $.post('tourist/sendSMS',
                {
                    recipient : phoneVal
                },
                function (data) {
                    // TODO 手机校验码发送后
                });
        }
        step = 0;
        id = '#phoneCodeBtn';
        codeIntervalId = setInterval(setCodeStep, 1000);
    });

    /** 绑定邮箱下一步. */
    $('#checkEmailNext').click(function () {

        var email = false;
        var emailVal = $('#email').val().trim();
        var reg = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g ;
        if(!reg.test(emailVal)){
            $('.alert').html('请输入可用的电子邮箱').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        } else {
            email = true;
        }

        var code = false;
        var vodeVal = $('#emailNum').val().trim();
        if (vodeVal != null && vodeVal != undefined && vodeVal != '') {
            code = true;
        } else {
            $('.alert').html('请输入验证码').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        }

        $.post('tourist/setemail',
            {
                email : emailVal,
                code : vodeVal
            },
            function (data) {
                if (data) {
                    $('#title_3').removeClass('active');
                    $('#title_4').addClass('active');
                    $('#check_email').hide();
                    $('#register_success').show();
                } else {
                    $('.alert').html('验证码错误').addClass('alert-danger').show().delay(2000).fadeOut();
                }
            });
    });

    $('#emailCodeBtn').click(function () {
        var email = false;
        var emailVal = $('#email').val().trim();
        var reg = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g ;
        if(!reg.test(emailVal)){
            $('.alert').html('请输入可用的电子邮箱').addClass('alert-danger').show().delay(2000).fadeOut();
            return;
        } else {
            $.post('tourist/testemail',
                {
                    email : emailVal
                },
                function (data) {
                    //　TODO 发送邮件后
                });
        }
        step = 0;
        id = '#emailCodeBtn';
        codeIntervalId = setInterval(setCodeStep, 1000);
    });

    $('#okBtn').click(function () {
        $('#out_bg').hide();
        $('#update_phone').hide();
    });

    /** 协议倒计时. */
    var step = 0;
    var intervalId = setInterval(showBtn, 1000);
    function showBtn() {
        step++;
        $('#okBtn').html('同意协议(' + step +'S)');
        if (step >= 6) {
            clearInterval(intervalId);
            $('#okBtn').html('同意协议');
            $('#okBtn').attr('disabled', false);
        }
    }

    /** 验证码等待提示. */
    var codeIntervalId;
    var id;
    function setCodeStep() {
        step++;
        if (step == 1) {
            $(id).attr('disabled', true);
        }
        console.log("ID:" + codeIntervalId);
        $(id).html('获取验证码(' + step + 'S)');
        if (step >= 30) {
            clearInterval(codeIntervalId);
            $(id).html('获取验证码');
            $(id).attr('disabled', false);
        }
        console.log(step);
    }
});

