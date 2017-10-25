/** 选择页面. */
function selectPage(num) {
    var searchtext = $('#searchText').val().trim();
    var target = $('#target').val().trim();
    window.location.replace('search?searchText=' + searchtext + '&target=' + target + '&pageNum=' + num)
}