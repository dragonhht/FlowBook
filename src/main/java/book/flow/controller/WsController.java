package book.flow.controller;

import book.flow.enity.ChatRecord;
import book.flow.enity.User;
import book.flow.model.Message;
import book.flow.service.UserService;
import book.flow.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 聊天控制器.
 * User: huang
 * Date: 17-8-7
 */
@Controller
@EnableWebSocket // 不开启则无法访问 @MessageMapping
public class WsController {

    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private UserService userService;

    /**
     * 聊天.
     * @param message 聊天内容
     * @param session session
     * @return
     */
    @PostMapping("/message")
    @ResponseBody
    public String sendMessage(Message message, HttpSession session) {
        System.out.println(message);
        User user = (User)session.getAttribute("user");
        if (user != null) {
            int userId = user.getUserId();
            int toUser = message.getUserId();
            boolean hasSend = webSocketHandler.sendMessageToUser(userId, toUser, new TextMessage(message.getMessage()));
        }
        return "message";
    }

    @PostMapping("/chatWith")
    @ResponseBody
    public List<String> getFriendMsg(int friendId, HttpSession session) {
        List<String> messages = null;
        List<ChatRecord> myMsg = null;
        User user = (User)  session.getAttribute("user");
        if (user != null) {
            int selfId = user.getUserId();
            messages = userService.getFriendMsg(selfId, friendId);
        }
        return messages;
    }

}
