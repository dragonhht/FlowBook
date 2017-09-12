package book.flow.websocket;

import book.flow.enity.ChatRecord;
import book.flow.enity.User;
import book.flow.repository.UserRepository;
import book.flow.service.UserFriendService;
import book.flow.service.UserService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description.
 * User: huang
 * Date: 17-8-8
 */
@Service
public class WebSocketHandler extends TextWebSocketHandler{

    @Autowired
    private UserService userService;
    @Autowired
    private UserFriendService userFriendService;

    //在线用户列表
    private static Map<Integer, WebSocketSession> users = new HashMap<>();
    //用户标识
    private static final String CLIENT_ID = "userId";


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        Integer userId = getClientId(session);
        if (userId != null) {
            users.put(userId, session);
            // session.sendMessage(new TextMessage("成功建立socket连接"));
            System.out.println(userId);
            System.out.println(session);
        }
    }

    public void addChatRecord(Integer selfId, Integer clientId, TextMessage message) {
        User sender = userService.getUserById(selfId);
        User receiver = userService.getUserById(clientId);

        ChatRecord record = new ChatRecord();
        record.setMessage(message.getPayload());
        record.setReceiver(receiver);
        record.setSender(sender);
        record.setSendDate(new Date());
        userFriendService.addChatRecord(record);
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(Integer selfId, Integer clientId, TextMessage message) {
        if (users.get(clientId) == null) {

            addChatRecord(selfId, clientId, message);

            return false;
        }
        addChatRecord(selfId, clientId, message);
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;
        try {
            message = new TextMessage(selfId + "/[-=^*]" + message.getPayload());
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());

        WebSocketMessage message1 = new TextMessage("server:"+message);
        /*try {
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }
}
