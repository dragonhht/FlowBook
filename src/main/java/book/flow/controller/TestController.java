package book.flow.controller;

import book.flow.service.TouristService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Description.
 * User: huang
 * Date: 17-7-24
 */
@Controller
public class TestController {

    @Autowired
    private TouristService touristService;

    @RequestMapping("/test")
    public String ts() {
        touristService.getBookById(1000000);
        return "index";
    }

    @RequestMapping("/msg")
    public String msg() {
        return "msg";
    }

}
