/** 显示申请内容. */
function showApply(id, flag) {
    console.log('flag: ' + flag);
    $.post('getFlowApplyById',
        {
            flowApplyId : id
        },
        function(data) {
            var height = $(document).height();
            $('#out_bg').css('height', height);
            $('#out_bg').show();
            $('#applyId').val(id);
            $('#applyText').html(data.applyUser.userName);
            $('#applyForBook').html(data.book.bookName + '(' + data.book.bookId + ')');
            var status;
            if (data.status == 0) {
                status = '未处理';
            }
            if (data.status == 1) {
                status = '同意';
            }
            if (data.status == 2) {
                status = '拒绝';
            }
            if (data.status == 3) {
                status = '处理中'
            }
            $('#applyStatus').html(status);
            $('#applySay').html(data.wantSay);
            $('#applyBtn').html("");
            if (data.status == 0) {
                $('#applyBtn').append('<br/><button onclick="applyOk()" id="okBtn" class="btn btn-info">同意</button>&nbsp;' +
                    '<button onclick="refuse()" class="btn btn-info">拒绝</button>');
            }
            if (data.status == 3 && flag == 1) {
                $('#applyBtn').append('<span>双方传递图书，完成传阅</span>&nbsp;<a target="_blank" href="../tourist/user/' + data.applyUser.userId + '">联系对方</a>&nbsp;' +
                    '<button onclick="okApply()" class="btn btn-info">确认图书到达</button> ');
            }
            if (data.status == 3 && flag == 0) {
                $('#applyBtn').append('<span>请将图书传阅至对方,等待对方确认，完成传阅</span>&nbsp;<a target="_blank" href="../tourist/user/' + data.applyUser.userId + '">联系对方</a>');
            }
            $('#reason').html("");
            $('#show_apply').show();
            console.log(data);
        });
}

function showApplyMy(id, flag) {
    console.log('flag: ' + flag);
    $.post('getFlowApplyById',
        {
            flowApplyId : id
        },
        function(data) {
            var height = $(document).height();
            $('#out_bg').css('height', height);
            $('#out_bg').show();
            $('#applyId').val(id);
            $('#applyText').html(data.applyUser.userName);
            $('#applyForBook').html(data.book.bookName + '(' + data.book.bookId + ')');
            var status;
            if (data.status == 0) {
                status = '未处理';
            }
            if (data.status == 1) {
                status = '同意';
            }
            if (data.status == 2) {
                status = '拒绝';
            }
            if (data.status == 3) {
                status = '处理中'
            }
            $('#applyStatus').html(status);
            $('#applySay').html(data.wantSay);
            $('#applyBtn').html("");
            if (data.status == 0) {
                /*$('#applyBtn').append('<br/><button onclick="applyOk()" id="okBtn" class="btn btn-info">同意</button>&nbsp;' +
                    '<button onclick="refuse()" class="btn btn-info">拒绝</button>');*/
            }
            if (data.status == 3 && flag == 1) {
                $('#applyBtn').append('<span>双方传递图书，完成传阅</span>&nbsp;<a target="_blank" href="../tourist/user/' + data.applyUser.userId + '">联系对方</a>&nbsp;' +
                    '<button onclick="okApply()" class="btn btn-info">确认图书到达</button> ');
            }
            if (data.status == 3 && flag == 0) {
                $('#applyBtn').append('<span>请将图书传阅至对方,等待对方确认，完成传阅</span>&nbsp;<a target="_blank" href="../tourist/user/' + data.applyUser.userId + '">联系对方</a>');
            }
            $('#reason').html("");
            $('#show_apply').show();
            console.log(data);
        });
}

/** 同意申请. */
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

/** 拒绝申请. */
function refuse() {
    $('#applyBtn').html(' ');
    $('#applyBtn').append('<label for="refuseReason">理由：</label>' +
        '<textarea style="width: 500px; height: 90px; resize: none;outline: none" id="refuseReason" ></textarea> ');
    $('#reason').html('');
    $('#reason').append('<button onclick="refuseFlowApply()" class="btn btn-info">确定</button>');
}

/** 拒绝申请提交. */
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

/** 确认图书传阅完成. */
function okApply() {
    if (window.confirm("请确认图书已到达您的手中!")) {
        var applyId = $('#applyId').val();
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

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
    $('#out_bg').hide();
}

$(document).ready(function () {

    $('#out_bg').click(function () {
        $('#show_apply').hide();
        $('#out_bg').hide();
    });

});