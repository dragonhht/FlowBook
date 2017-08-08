package book.flow.controller;

import book.flow.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * 聊天控制器.
 * User: huang
 * Date: 17-8-7
 */
@Controller
@EnableWebSocket // 不开启则无法访问 @MessageMapping
public class WsController {

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Message chat(Message message) {
        System.out.println(message);
        return message;
    }
}
