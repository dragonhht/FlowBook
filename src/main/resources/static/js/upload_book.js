/**
 * Created by huang on 17-7-16.
 */
/** 导航选择. */
function selectNav(n) {
    var links = $('#top_ul').children('li');
    for (var i = 0; i < links.length; i++) {
        $(links[i]).children('a').css('color', 'black');
    }
    $(n).css('color', 'gray');
}

/** 星评. */
function startLight(n) {
    var index = parseInt($(n).attr('index')) + 1;
    var a = $('#startComment').children('a');
    for (var i = 0; i < index; i++) {
        $($(a[i]).children('img')).attr('src', "../img/light_start.png");
    }
    for (var j = i; j < 5; j++) {
        $($(a[j]).children('img')).attr('src', "../img/dark_start.png");
    }
    $('#bookStart').val(index);
}

/** 选择图书类型. */
var selectCount = 1;
function selectType(n) {
    var index = $(n).attr('index');
    var text = $($(n).children('a')).html().trim();
    if (index == 0) {  // 未选择
        var val = $(n).attr('val');
        if (selectCount > 2) {
            alert("所选标签数不得超过3个！")
        } else {
            selectCount++;
            $(n).css('border', '1px solid gray');
            $(n).attr('index', 1);
            $('#addTypeBtn').before('<span class="type_select">' +
                '<a style="color: #333333;" class="type_name">' + text + '</a>' +
                '<a class="del_type">X</a>' +
                '<input name="type" hidden="hidden" type="text" value=" ' + val + '" />' +
                '</span>')
        }
    } else {
        $(n).attr('index', 0);
        selectCount--;
        $(n).css('border', '1px solid #DDDDDD');
        var s = $('#inputType').children('.type_select');
        for (var i = 0; i < s.length; i++) {
            var t = $($(s[i]).children('.type_name')).html().trim();
            if (t == text) {
                $(s[i]).remove();
            }
        }
    }

}

$(document).ready(function () {

    /** 当鼠标在添加类型的图片上. */
    $('#addTypeBtn').mouseover(function () {
        $($('#addTypeBtn').children('img')).attr('src', "../img/add_type_1.png");
        console.log("进入");
    });

    /** 当鼠标离开添加类型的图片上. */
    $('#addTypeBtn').mouseout(function () {
        $($('#addTypeBtn').children('img')).attr('src', '../img/add_type.png');
        console.log("离开");
    });

    /** 关闭标签选择. */
    $('#closeTypeSelect').click(function () {
        $('#types').hide();
    });

    /** 显示标签选择. */
    $('#addTypeBtn').click(function () {
        $('#types').show();
    });

});