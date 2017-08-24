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

/** 显示图书退出申请内容. */
function showApply(applyId) {
    console.log('显示申请');
    $.post('../user/getApplyById',
        {
            applyId : applyId
        },
        function (data) {
            console.log(data);
            $('#applyId').val(data.applyId);
            $('#applyText').html(data.applyText);
            $($('#applyForBook').children('a')).html(data.book.bookId);
            $($('#applyForBook').children('a')).attr('href', '../tourist/bookMessage/' + data.book.bookId);
            $('#applyForImg').html(" ");
            $('#applyForImg').append('<span id="imgLast" hidden="hidden"></span>');
            for (var i= 0; i < data.imgs.length; i++) {
                $('#imgLast').before('<img style="width: 150px;height: 200px;" src="' +data.imgs[i].path + '" />');
            }
            $('#applyForStatus').html(data.status);
            $('#dealBookOut').html(" ");
            if (data.status == '待审批') {
                $('#dealBookOut').append('<button onclick="delBook()" class="btn btn-info">通过</button>&nbsp;&nbsp;' +
                    '<button onclick="refuseApply()" class="btn btn-info">否决</button>');
            }
            $('#show_apply').show();
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
            if (data) {
                location.reload(true);
            }
        })
}

function showReport(reportId) {

    $('#reportResult').html(' ');

    $.post('getReportById',
        {
            reportId : reportId
        },
    function (data) {
        console.log(data);
        $('#reportId').val(data.reportId);
        $('#beReportName').html(data.beReport.userName);
        $('#beReportName').attr('href', '../tourist/user/' + data.beReport.userId);
        $('#reportText').val(data.reportText);
        $('#ReportImgs').html(' ');
        for (var i = 0; i < data.img.length; i++) {
            $('#ReportImgs').append('<span class="book_img_div">' +
                '<img style="width: 150px;height: 200px;" src=" ' + data.img[i].path +'" />' +
                '</span>');
        }
        $('#reportBtn').html(' ');
        if (data.status == 0) {
            $('#reportBtn').append('<button onclick="punishReport()" class="btn btn-info">惩罚</button>&nbsp;&nbsp;' +
                '<button onclick="notPunishReport()" class="btn btn-info">不惩罚</button>')
        }
        $('#report').show();
    })
}

/** 惩罚用户. */
function punishReport() {
    var reportId = $('#reportId').val();
    $.post('punishReport',
        {
            reportId : reportId
        },
    function (data) {
        if (data) {
            location.reload(true);
        } else {
            $('#reportResult').html('失败');
        }
    });
}

function notPunishReport() {
    var reportId = $('#reportId').val();
    console.log(reportId);
    $.post('notPunishReport',
        {
            reportId : reportId
        },
        function (data) {
            if (data) {
                location.reload(true);
            } else {
                $('#reportResult').html('失败');
            }
        });
}
