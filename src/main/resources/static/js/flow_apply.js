/**
 * Created by huang on 17-7-17.
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

/** 显示修改框. */
function showUpdateDiv(n) {
    var index = $(n).attr('index');
    if (index == 'name') {
        $('#update_name').show();
    }
}

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
}

/** 选择申请的书籍. */
function selectBook(id) {
    $('#bookId').val(id);
    $('#update_name').hide();
    $('#applyBookId').val(id);
}

/** 显示申请内容. */
function showApply(applyId) {
    console.log('显示申请');
    $.post('getFlowApplyById',
        {
            flowApplyId : applyId
        },
    function (data) {
        console.log(data);
        $('#applyText').html(data.applyUser.userName);
        $('#applyText').attr('href', '../tourist/user/' + data.applyUser.userId);
        $('#applyForBook').html(data.book.bookName);
        $('#applyForBook').attr('href', '../tourist/bookMessage/' + data.book.bookId);
        var status = '未决定';
        if (data.status == 1) {
            status = '同意';
        }
        if (data.status == 2) {
            status = '拒绝';
        }
        $('#applyStatus').html(status);
        $('#applySay').html(data.wantSay);
        if (data.status == 0) {
            $('#applyBtn').append('<button class="btn btn-info">同意</button>&nbsp;&nbsp;&nbsp;' +
                '<button class="btn btn-info">拒绝</button>');
        }
        $('#show_apply').show();
    })
}

$(document).ready(function () {

    // 显示图片选择
    $('#addImg').click(function () {
        var bookId = $('#applyBookId').val();
        if (bookId == 0) {
            alert("请先选择图书");
        } else {
            $('#update_img').show();
        }
    });

    // 上传图片
    var index = 0;
    $('#updateImgBtn').click(function () {
        var file = new FormData($('#imgForm')[0]);
        var userId = $('#userId').val();
        var bookId = $('#applyBookId').val();
        if (index <= 2) {
            $.ajax({
                url : 'uploadApplyImg',
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
                            'src="http://localhost:8080/FlowBook/files/apply_img/' + userId + '/' + bookId + '_' + index + '.png"/>' +
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

});