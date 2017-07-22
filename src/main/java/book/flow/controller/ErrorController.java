package book.flow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误跳转控制器.
 * User: huang
 * Date: 17-7-22
 */
@Controller
public class ErrorController {

    /**
     * 跳转至404提示页面.
     * @return 404提示页面
     */
    @RequestMapping("/404")
    public String notFound() {
        return "errors/error_404";
    }

    /**
     * 跳转未授权页面.
     * @return 未授权页面
     */
    @RequestMapping("/401")
    public String unauthorized() {
        return "errors/error_401";
    }

    /**
     * 跳转服务器内部错误提示页面.
     * @return 相关提示页面
     */
    @RequestMapping("/500")
    public String serverError() {
        return "errors/error_500";
    }
}
