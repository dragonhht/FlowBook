package book.flow.controller;

import book.flow.service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
