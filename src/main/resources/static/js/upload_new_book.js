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
                        '<input name="type" hidden="hidden" type="text" value=" ' + $(n).attr('val') + ' + " />\n' +
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