function showReport(reportId) {
    var height = $(document).height();
    $('#out_bg').css('height', height);

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
            $('#out_bg').show();
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

$(document).ready(function () {

    $('#out_bg').click(function () {
        $('#out_bg').hide();
        $('#show_apply').hide();
        $('#report').hide();
    });

});

/** 隐藏修改框. */
function hideUpdateDiv(n) {
    var parent = $(n).parent();
    $(parent).hide();
    $('#out_bg').hide();
}