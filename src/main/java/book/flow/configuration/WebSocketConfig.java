package book.flow.configuration;

import book.flow.websocket.HttpSessionIdHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Description.
 * User: huang
 * Date: 17-8-8
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/webSocketHandler").addInterceptors(new HttpSessionIdHandshakeInterceptor());
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new book.flow.websocket.WebSocketHandler();
    }

}
