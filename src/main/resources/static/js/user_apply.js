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

/** 显示申请内容. */
function showApply(id) {
    $.post('getApplyById',
        {
            applyId : id
        },
        function(data) {
            var height = $(document).height();
            $('#out_bg').css('height', height);
            $('#out_bg').show();
            $('#applyText').html(data.applyText);
            $('#applyBook').html(data.book.bookName + '(' + data.book.bookId + ')');
            $($('#applyForBook').children('a')).attr('href', '../tourist/bookMessage/' + data.book.bookId);
            $('#applyForImg').html(" ");
            $('#applyForImg').append('<span id="imgLast" hidden="hidden"></span>');
            for (var i= 0; i < data.imgs.length; i++) {
                $('#imgLast').before('<img style="width: 150px;height: 200px;margin-right: 10px;" src="' +data.imgs[i].path + '" />');
            }
            $('#applyForStatus').html(data.status);
            $('#show_apply').show();
        });
}

/** 关闭详细显示. */
function hideUpdateDiv(n) {
    $(n).parents('#show_apply').hide();
    $('#out_bg').hide();
}

/** 选择申请的书籍. */
function selectBook(id) {
    $('#bookId').val(id);
    $('#selectBook').hide();
    $('#out_bg').hide();
    $('#applyBookId').val(id);
}

$(document).ready(function(){

    $('#out_bg').click(function () {
        $('#show_apply').hide();
        $('#out_bg').hide();
        $('#selectBook').hide();
    });

    /** 选择图书. */
    $('#selectBookBtn').click(function () {
        var height = $(document).height();
        $('#out_bg').css('height', height);
        $('#out_bg').show();
        $('#selectBook').show();
    });

    var index = 0;
    // 保存表单数据
    var file = new FormData();
    $('#fileSelect').change(function () {
        if (index < 3) {
            var files = $('#fileSelect')[0].files[0];
            var url = window.URL.createObjectURL(files);
            $('#img_show').append('<img width="100px" height="130px" src=" ' + url + '" />');
            // 将图片数据保存
            file.append('fileSelect', files);
            index++;
        } else {
            alert('最多只能选择３张图片!');
        }
    });

    /** 提交申请. */
    $('#submitApply').click(function () {
        var bookId = $('#bookId').val().trim();
        file.append('bookId', bookId);
        $.ajax({
            url : 'applyOut',
            type : 'post',
            data : file,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {

            },
            error: function (data) {

            }
        });
    });

});