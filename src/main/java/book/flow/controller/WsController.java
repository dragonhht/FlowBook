package book.flow.controller;

import book.flow.model.Message;
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

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Message chat(Message message) {
        System.out.println(message);
        webSocketHandler.sendMessageToUser(1000000, new TextMessage("你好"));
        return message;
    }

    @PostMapping("/message")
    @ResponseBody
    public String sendMessage(Message message) {
        System.out.println("信息" + message);
        boolean hasSend = webSocketHandler.sendMessageToUser(1000000, new TextMessage(message.getMessage()));
        System.out.println(hasSend);
        return "message";
    }

}
