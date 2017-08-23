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

    $('#sendSMS').click(function () {
        var phone = $('#userPhone').val().trim();
        $.post('tourist/sendSMS',
            {
                recipient : phone
            },
            function (data) {
                console.log(data);
            }
        )
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

/** 地区选择. */
//这个方法用来给省份的select赋值
function onload(){
    //获取json格式之后转化为数组
    var json_array = eval(json);
    var Province = document.getElementById("Province");
    for(var i = 0;i<json_array.length;i++){
        var json_p = json_array[i].name;
        Province.add(new Option(json_p,json_p), null);
    }
};
//这个方法就是实现省市级联的核心方法，也是用来给市的select赋值
function changeCity() {
    var Province = document.getElementById("Province");
    var Provincevalue = Province.options[Province.selectedIndex].value;
    var city = document.getElementById("city");
    var optinsValue;
    //获取json格式之后转化为数组
    var json_array = eval(json);
    //获取当前选中的省份Province.value;
    city.options.length = 0; //清空城市
    switch(Provincevalue){
        case "北京市":
            optinsValue = json_array[0].北京市;
            break;
        case "天津市":
            optinsValue = json_array[1].天津市;
            break;
        case "河北省":
            optinsValue = json_array[2].河北省;
            break;
        case "山西省":
            optinsValue = json_array[3].山西省;
            break;
        case "内蒙古自治区":
            optinsValue = json_array[4].内蒙古自治区;
            break;
        case "辽宁省":
            optinsValue = json_array[5].辽宁省;
            break;
        case "吉林省":
            optinsValue = json_array[6].吉林省;
            break;
        case "黑龙江省":
            optinsValue = json_array[7].黑龙江省;
            break;
        case "上海市":
            optinsValue = json_array[8].上海市;
            break;
        case "江苏省":
            optinsValue = json_array[9].江苏省;
            break;
        case "浙江省":
            optinsValue = json_array[10].浙江省;
            break;
        case "安徽省":
            optinsValue = json_array[11].安徽省;
            break;
        case "福建省":
            optinsValue = json_array[12].福建省;
            break;
        case "江西省":
            optinsValue = json_array[13].江西省;
            break;
        case "山东省":
            optinsValue = json_array[14].山东省;
            break;
        case "河南省":
            optinsValue = json_array[15].河南省;
            break;
        case "湖北省":
            optinsValue = json_array[16].湖北省;
            break;
        case "湖南省":
            optinsValue = json_array[17].湖南省;
            break;
        case "广东省":
            optinsValue = json_array[18].广东省;
            break;
        case "广西壮族自治区":
            optinsValue = json_array[19].广西壮族自治区;
            break;
        case "海南省":
            optinsValue = json_array[20].海南省;
            break;
        case "重庆市":
            optinsValue = json_array[21].重庆市;
            break;
        case "四川省":
            optinsValue = json_array[22].四川省;
            break;
        case "贵州省":
            optinsValue = json_array[23].贵州省;
            break;
        case "云南省":
            optinsValue = json_array[24].云南省;
            break;
        case "西藏自治区":
            optinsValue = json_array[25].西藏自治区;
            break;
        case "陕西省":
            optinsValue = json_array[26].陕西省;
            break;
        case "甘肃省":
            optinsValue = json_array[27].甘肃省;
            break;
        case "青海省":
            optinsValue = json_array[28].青海省;
            break;
        case "宁夏回族自治区":
            optinsValue = json_array[29].宁夏回族自治区;
            break;
        case "新疆维吾尔自治区":
            optinsValue = json_array[30].新疆维吾尔自治区;
            break;
        case "香港特别行政区":
            optinsValue = json_array[31].香港特别行政区;
            break;
        case "澳门特别行政区":
            optinsValue = json_array[32].澳门特别行政区;
            break;
    }
    trim(optinsValue,city);
};
//转化为数据的形式为市select赋值，需要数据，以及select这个标签
function trim(obj,label){
    for(var i = 0;i<obj.length;i++){
        label.add(new Option(obj[i],obj[i]), null);
    }
};