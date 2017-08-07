/**
 * Created by huang on 17-8-7.
 */

/** 显示好友分组的好友. */
function selectGroup(n) {
    var groups = $('#friend_list').children('.friend_group');
    for (var i = 0; i < groups.length; i++) {
        var friends = $(groups[i]).children('ul');
        $(friends[0]).hide();
    }
    var parent = $(n).parent('.friend_group')[0];
    var list = $(parent).children('ul')[0];
    $(list).show();
}
