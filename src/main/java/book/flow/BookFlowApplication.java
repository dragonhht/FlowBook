package book.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableWebSocketMessageBroker
public class BookFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookFlowApplication.class, args);
	}
}
