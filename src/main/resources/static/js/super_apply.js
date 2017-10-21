/** 显示图书退出申请内容. */
function showApply(applyId) {
    var height = $(document).height();
    $('#out_bg').css('height', height);

    $.post('../user/getApplyById',
        {
            applyId : applyId
        },
        function (data) {
            console.log(data);
            $('#applyId').val(data.applyId);
            $('#applyText').html(data.applyText);
            $($('#applyForBook').children('a')).html(data.book.bookId + ' ( ' + data.book.bookName + ')');
            $($('#applyForBook').children('a')).attr('href', '../tourist/bookMessage/' + data.book.bookId);
            $('#applyForImg').html(" ");
            $('#applyForImg').append('<span id="imgLast" hidden="hidden"></span>');
            for (var i= 0; i < data.imgs.length; i++) {
                $('#imgLast').before('<img style="width: 150px;height: 200px;margin-right: 10px;" src="' +data.imgs[i].path + '" />');
            }
            $('#applyForStatus').html(data.status);
            $('#dealBookOut').html(" ");
            if (data.status == '待审批') {
                $('#dealBookOut').append('<button onclick="delBook()" class="btn btn-info">通过</button>&nbsp;&nbsp;' +
                    '<button onclick="refuseApply()" class="btn btn-info">否决</button>');
            }
            $('#show_apply').show();
            $('#out_bg').show();
        })
}

/** 同意图书退出申请. */
function delBook() {
    var applyId = $('#applyId').val();
    $.post('delBook',
        {
            applyId : applyId
        },
        function (data) {
            if (data) {
                location.reload(true);
            }
        })
}

/** 拒绝申请. */
function refuseApply() {
    var applyId = $('#applyId').val();
    $.post('refuseApply',
        {
            applyId : applyId
        },
        function (data) {
        console.log(data);
            if (data) {
                location.reload(true);
            }
        })
}

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
    $('#out_bg').hide();
}

$(document).ready(function () {

    $('#out_bg').click(function () {
        $('#out_bg').hide();
        $('#show_apply').hide();
        $('#report').hide();
    });

});