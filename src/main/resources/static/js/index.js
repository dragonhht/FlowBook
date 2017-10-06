/** 列表滚动. */
var doscroll = function() {
    var $parent = $('#run_list');
    var $first = $parent.find('li:first');
    var height = $first.height() + 20;
    $first.animate({
        marginTop: -height + 'px'
    }, 500, function () {
        $first.css('marginTop', 0).appendTo($parent);
    });
};

var id = setInterval(function(){doscroll()}, 3000);

/** 公告信息停止循环显示. */
function stopList() {
    clearInterval(id);
}

/** 开始公告显示循环. */
function startList() {
    id = setInterval(function(){doscroll()}, 3000);
}