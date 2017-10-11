package book.flow.configuration;

import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.websocket.server.ServerContainer;

/**
 * tomcat配置类.
 * User: huang
 * Date: 17-8-4
 */
@Configuration
public class TomcatConfiguration{

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        // 配置虚拟目录
        tomcat.addContextCustomizers(tomcatContextCustomizer());
        return tomcat;
    }

    @Bean
    public TomcatContextCustomizer tomcatContextCustomizer() {
        return (context -> {

            // 获取项目路径
            String path = System.getProperty("user.dir");
            System.out.println(path);
            context.setDocBase(path + "/file-dir");
            context.setPath("/FlowBook/");
            context.setReloadable(true);
        });
    }


}
