package book.flow.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启Druid监控功能.
 * <p>
 * User : Dragon_hht
 * Date : 17-4-13
 * Time : 下午5:11
 */
@Configuration
public class DruidConfiguration {
    @Bean
    public ServletRegistrationBean statViewServle() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //Ip白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单(同时存在，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.111.111.111");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "123");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*gif,*jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
