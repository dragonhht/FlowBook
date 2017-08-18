/**
 * Created by huang on 17-7-13.
 */
/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

/** 选择搜索范围. */
function selectSearchType(n) {
    var lis = $('#search_ul').children('li');
    for (var i = 0; i < lis.length; i++) {
        $(lis[i]).removeClass('active');
    }
    $(n).addClass('active');
    $('#search_flag').val($(n).children('a').attr('index').trim());
}

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

/** 显示举报填写框. */
function showReportDiv() {
    $('#report').show();
}

/** 隐藏举报填写框. */
function hideReportDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
}

$(document).ready(function () {

    // 显示图片选择
    $('#addImg').click(function () {
        $('#update_img').show();
    });

    // 上传图片
    var index = 0;
    $('#updateImgBtn').click(function () {
        var file = new FormData($('#imgForm')[0]);
        var reportedId = $('#reportedId').val();
        var userId = $('#reportId').val();
        if (index <= 2) {
            $.ajax({
                url : '../../user/uploadReportImg',
                type : 'post',
                data : file,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data != 'error') {
                        $('#update_img').hide();
                        $('#addImg').before('<span class="book_img_div">' +
                            '<img style="width: 150px;height: 200px;"' +
                            'src="http://localhost:8080/FlowBook/files/report_img/' + userId + '/' + reportedId + '_' + index + '.png"/>' +
                            '<input hidden="hidden" type="text" name="imgs" value="' + data + '" />' +
                            '</span>');
                        index++;
                        $('#imgIndex').val(index);
                        if (index >= 3) {
                            $('#addImg').remove();
                        }
                    } else {
                        $('#imgResult').html(data);
                    }
                },
                error: function (data) {
                    $('#imgResult').html(data);
                }
            });
        }
    });

    // 提交举报
    $('#reportBtn').click(function () {
        var formData = new FormData($('#reportForm')[0]);
        $.ajax({
            url : '../../user/saveReport',
            type : 'post',
            data : formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(data);
                if (data) {
                    $('#report').hide();
                    $('#message').html('举报完成, 等待结果');
                    $('#result').show();
                }
            },
            error: function (data) {
                $('#reportResult').html('失败');
            }
        });
    });
});