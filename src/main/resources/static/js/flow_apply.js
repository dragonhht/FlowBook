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
    $('#applyResult').html(' ');
    $.post('getFlowApplyById',
        {
            flowApplyId : applyId
        },
    function (data) {
        console.log(data);
        $('#applyId').val(data.id);
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
        if (data.status == 3) {
            status = '处理中';
        }
        $('#applyStatus').html(status);
        $('#applySay').html(data.wantSay);
        $('#applyBtn').html(" ");
        if (data.status == 0) {
            $('#applyBtn').append('<button onclick="applyOk()" class="btn btn-info">同意</button>&nbsp;&nbsp;&nbsp;' +
                '<button onclick="refuse()" id="refuse" class="btn btn-info">拒绝</button>');
        }
        if (data.status == 2) {
            $('#applyBtn').html(' ');
            $('#applyBtn').append('<label for="applyRefuse">拒绝理由：</label>' +
                '<div id="applyRefuse" style="border: 1px solid #DDDDDD; width: 500px;height: 100px;">' + data.refuse + '</div>')
        }
        if (data.status == 3) {
            $('#applyBtn').append('<span>双方传递图书， 等待对方确认，完成传阅</span>&nbsp;<a target="_blank" href="../tourist/user/' + data.applyUser.userId + '">联系对方</a>');
        }
        $('#show_apply').show();
    })
}

function showApplyOk(applyId) {
    $.post('getFlowApplyById',
        {
            flowApplyId : applyId
        },
        function (data) {
            console.log(data);
            $('#applyOkId').val(data.id);
            $('#applyOkText').html(data.applyUser.userName);
            $('#applyOkText').attr('href', '../tourist/user/' + data.applyUser.userId);
            $('#applyOkForBook').html(data.book.bookName);
            $('#applyOkForBook').attr('href', '../tourist/bookMessage/' + data.book.bookId);
            var status = '未决定';
            if (data.status == 1) {
                status = '同意';
            }
            if (data.status == 2) {
                status = '拒绝';
            }
            if (data.status == 3) {
                status = '处理中';
            }
            $('#applyOkStatus').html(status);
            $('#applyOkSay').html(data.wantSay);
            $('#apply_ok').show();
        })
}

/** 确认图书传阅完成. */
function okApply() {
    if (window.confirm("请确认图书已到达您的手中!")) {

        var applyId = $('#applyOkId').val();
        console.log(applyId);

        $.post('flowToNext',
            {
                applyId : applyId
            },
        function (data) {
            if (data) {
                location.reload(true);
            } else {
                $('#applyOkResult').html("失败");
            }
        });

        return true;
    } else {
        return false;
    }
}

$(document).ready(function () {

    

});

// 拒绝申请
function refuse() {
    $('#applyBtn').html(' ');
    $('#applyBtn').append('<label for="refuseReason">理由：</label>' +
        '<textarea style="width: 455px; height: 90px; resize: none;" id="refuseReason" ></textarea> ');
    $('#reason').html(' ');
    $('#reason').append('<button onclick="refuseFlowApply()" class="btn btn-info">确定</button>');
}

function refuseFlowApply() {
    var refuse = $('#refuseReason').val().trim();
    var applyId = $('#applyId').val();
    if (refuse != '') {
        $.post('refuseFlowApply',
            {
                refuse : refuse,
                applyId : applyId
            },
        function (data) {
            if (data) {
                location.reload(true);
            } else {
                $('#applyResult').html('失败');
            }
        })
    } else {
        alert('理由不能为空');
    }
}

/** 提交申请处理. */
function applyOk() {
    var applyId = $('#applyId').val();
    $.post('applyOk',
        {
            applyId : applyId
        },
    function (data) {
        if (data) {
            location.reload(true);
        } else {
            $('#applyResult').html('失败');
        }
    });
}