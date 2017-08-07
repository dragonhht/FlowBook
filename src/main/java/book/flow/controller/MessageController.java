package book.flow.controller;

import book.flow.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * 聊天控制器.
 * User: huang
 * Date: 17-8-7
 */
@Controller
public class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Message chat(Message message) {
        System.out.println(message);
        return message;
    }
}
