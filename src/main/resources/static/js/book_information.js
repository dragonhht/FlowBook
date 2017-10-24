$(document).ready(function () {

    /** 借阅申请. */
    $('#applyBtn').click(function () {
        var height = $(document).height();
        var width = $(document).width();
        var divWidth = $('#wantLook').width();
        $('#out_bg').css('height', height);
        $('#wantLook').css('left', (width - divWidth)/2 + "px");
        $('#out_bg').show();
        $('#wantLook').show();
    });

    $('#out_bg').click(function () {
        $('#wantLook').hide();
        $('#out_bg').hide();
    });

    /** 关闭聊天框. */
    $('#close').click(function () {
        var parent = $('#close').parents('#wantLook');
        $(parent).hide();
        $('#out_bg').hide();
    });

    /** 提交图书传阅申请. */
    $('#updateImgBtn').click(function () {
        var bookId = $('#bookId').val().trim();
        var toUserId = $('#toUserId').val().trim();
        var wantSay = $('#wantSay').val().trim();
        var credit = $('#ownerCredit').val().trim();
        if (credit > 3) {
            $.post('../../user/wantBook',
                {
                    bookId : bookId,
                    toUserId : toUserId,
                    wantSay : wantSay
                },
                function (data) {
                    if (data == 'ok') {
                        $('.alert').html('提交成功').addClass('alert-success').show().delay(2000).fadeOut();
                        $('#wantLook').hide();
                        $('#out_bg').hide();
                    } else {
                        $('.alert').html('提交失败').addClass('alert-danger').show().delay(2000).fadeOut();
                        $('#wantLook').hide();
                        $('#out_bg').hide();
                    }
                })
        } else {
            $('.alert').html('信用过低，无法申请').addClass('alert-danger').show().delay(2000).fadeOut();
        }

    });

});