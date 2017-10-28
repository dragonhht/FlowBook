$(document).ready(function () {

    $('.change_btn').hide();

    /** 显示图片上传按钮. */
    $('.img_div').mouseover(function () {
        $('.change_btn').show();
    });

    /** 隐藏图片上传按钮. */
    $('.img_div').mouseout(function () {
        $('.change_btn').hide();
    });

    /** 预览图片. */
    $('#selectImg').change(function () {
        var files = $('#selectImg')[0].files[0];
        var url = window.URL.createObjectURL(files);
        $('#userImg').attr('src', url);
        $('#userImg').show();
    });

    /** 添加标签. */
    $('#addTypeBtn').click(function () {
        var height = $(document).height();
        $('#out_bg').css('height', height);
        $('#out_bg').show();
        var width = $(document).width();
        var divwidth = $('#types').width();
        $('#types').css('left', (width - divwidth) / 2);
        $('#types').show();
    });

    /** 关闭标签选择. */
    $('#out_bg').click(function () {
        $('#out_bg').hide();
        $('#types').hide();
    });

    $('#userImg').hide();

    /** 校验ISBN. */
    $('#ISBN').blur(function () {
        var isbn = $('#ISBN').val().trim();
        var len = isbn.length;
        var sum = 0, index = 1, step = 0;
        var result = 0, num = isbn[len - 1];
        /** 13位ISBN校验. */
        if (len == 17) {
            for (var i=0; i < 15; i++) {
                if (isbn[i] == '-') {
                    continue;
                }
                step++;
                if (step % 2 == 0) {
                    index = 3;
                } else {
                    index = 1;
                }
                sum = sum + index * isbn[i];
            }
            var m = sum % 10;
            result = 10 - m;
            if (result == 10) {
                result = 0;
            }
            if (result != num) {
                $('.alert').html('ISBN错误').addClass('alert-danger').show().delay(2000).fadeOut();
                $('#isbnDiv').addClass('has-error');
            } else {
                $('#isbnDiv').removeClass('has-error');
            }
        }

    });

});

/** 选择标签. */
var size =　1;
function selectType(n) {
    var index = $(n).attr('index');
    if (index == '0') {
        if (size < 3) {
            $(n).attr('index', 1);
            $(n).css('border', '1px solid gray');
            size++;
            $('#addTypeBtn').before('<span id="type_' + $(n).attr('val') + '" class="type_select">\n' +
                        '<a style="color: #333333;" class="type_name"> ' + $(n).children('a').html() + '</a>\n' +
                        '<a class="del_type">X</a>\n' +
                        '<input name="type" hidden="hidden" type="text" value="' + $(n).attr('val') + '" />\n' +
                        '</span>');
        } else {
            alert('最多只能选３个类型的标签');
        }
    } else {
        if (size > 1) {
            $(n).attr('index', 0);
            $(n).css('border', '1px solid #EEEEEE');
            size--;
            var val = $(n).attr('val');
            $('#type_' + val).remove();
        } else {
            alert('至少需要选择一个标签');
        }

    }
}

/** 星评. */
function startLight(n) {
    var index = parseInt($(n).attr('index')) + 1;
    var a = $('#startComment').children('a');
    for (var i = 0; i < index; i++) {
        $($(a[i]).children('i')).removeClass('glyphicon-star-empty');
        $($(a[i]).children('i')).addClass('glyphicon-star');
    }
    for (var j = i; j <= 5; j++) {
        $($(a[j]).children('i')).removeClass('glyphicon-star');
        $($(a[j]).children('i')).addClass('glyphicon-star-empty');
    }
    $('#bookStart').val(index);
}