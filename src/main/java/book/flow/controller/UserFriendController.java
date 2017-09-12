package book.flow.controller;

import book.flow.enity.User;
import book.flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户好友模块控制器.
 * User: huang
 * Date: 17-9-12
 */
@Controller
@RequestMapping("/user")
public class UserFriendController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到我的好友页面.
     * @param model model
     * @param session session
     * @return 我的好友页面
     */
    @RequestMapping("/userFriend")
    public String userFriend(Model model, HttpSession session) {

        List<User> friends = null;
        List<Integer> idList = null;
        List<User> notFriend = null;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            friends = userService.getFriends(selfId);
            idList = userService.getSenderId(selfId);
            notFriend = userService.getNotFriend(selfId);
        }
        model.addAttribute("notFriend", notFriend);
        model.addAttribute("senderIds", idList);
        model.addAttribute("friends", friends);
        return "user_friend";
    }

    /**
     * 添加好友.
     * @param friend 要添加的好友编号
     * @param session session
     * @return 添加结果信息
     */
    @PostMapping("/addFriend")
    @ResponseBody
    public boolean addFriend(int friend, HttpSession session) {
        boolean ok = false;
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            ok = userService.addFriend(userId, friend);
        }
        return ok;
    }

    /**
     * 删除好友.
     * @param friendId 好友编号
     * @param session session
     */
    @PostMapping("/delFriend")
    @ResponseBody
    public void delFriend(int friendId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            userService.delFriend(selfId, friendId);
            userService.setChatReaded(selfId, friendId);
        }
    }

    /**
     * 将信息标为已读.
     * @param friendId 对发编号
     * @param session session
     */
    @PostMapping("/readedChat")
    @ResponseBody
    public void readedChat(int friendId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            userService.setChatReaded(selfId, friendId);
        }
    }
}
