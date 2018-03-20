package book.flow.configuration;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 错误配置.
 * User: huang
 * Date: 17-7-22
 */
@Configuration
public class ErrorConfiguration {

    /**
     * 错误页面配置.
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            /*container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/401"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));*/
        });
    }
}
