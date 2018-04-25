package book.flow.configuration;

import book.flow.interceptors.AdminInterceptor;
import book.flow.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置.
 * User: huang
 * Date: 17-8-19
 */
@Configuration
public class WebInterceptor extends WebMvcConfigurerAdapter {

    /**
     * 添加拦截器
     * <p>This implementation is empty.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截用户模块下的所有请求
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/user/{userId}/**", "/user/flowToNextPhone");
        // 拦截管理员操作
        registry.addInterceptor(getAdminInterceptor()).addPathPatterns("/admin/**");
    }

    /**
     * 获取用户登录拦截器.
     * @return 拦截器
     */
    private LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 获取管理员拦截器.
     * @return 拦截器
     */
    private AdminInterceptor getAdminInterceptor() {
        return new AdminInterceptor();
    }
}
