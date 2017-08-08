package book.flow.controller;

import book.flow.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.security.Principal;

/**
 * 聊天控制器.
 * User: huang
 * Date: 17-8-7
 */
@Controller
@EnableWebSocket
public class WsController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/greetings")
    public Message chat(Message message, Principal principal) {

        System.out.println(principal);
        return message;
    }
}
