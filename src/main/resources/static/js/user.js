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
/*function chatLoginTip() {

}*/

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
function addFriend() {
    var friendId = $('#friendId').val();
    console.log(friendId);
    $.post('/FlowBook/user/addFriend',
        {
            friend : friendId
        },
        function (data) {
            if (data) {
                $('.alert').html('添加好友成功').addClass('alert-success').show().delay(2000).fadeOut();
                $('#addFriend').hide();
            } else {
                $('.alert').html('添加好友失败').addClass('alert-danger').show().delay(2000).fadeOut();
            }
            $('#result').show();
        })
}

/** 判断是否登录. */
function loginTip() {
    var login = $('input[name="islogin"]').val();
    console.log(login);
    if (login == 'ok') {
        addFriend();
        return true;
    } else {
        if (window.confirm("请先登录!")) {
            window.location.replace("../../login");
            return false;
        } else {
            return false;
        }
    }

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
    var index = 0;
    // 保存表单数据
    var file = new FormData();
    $('#fileSelect').change(function () {
        if (index < 3) {
            var files = $('#fileSelect')[0].files[0];
            var url = window.URL.createObjectURL(files);
            $('#img_show').append('<img width="100px" height="130px" src=" ' + url + '" />');
            // 将图片数据保存
            file.append('imgs', files);
            index++;
        } else {
            alert('最多只能选择３张图片!');
        }
    });
    
    $('#submitBtn').click(function () {
        console.log("dflkdj");
        var informants = $('#beReportId').val().trim();
        var reason = $('#reason').val().trim();
        file.append('informants', informants);
        file.append('reportText', reason);
        $.ajax({
            url : '../../user/saveReport',
            type : 'post',
            data : file,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                window.location.replace("../../msg?flag=applySeccuss");
            },
            error: function (data) {

            }
        });
    });

});

