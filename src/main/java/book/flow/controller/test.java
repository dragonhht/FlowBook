package book.flow.controller;

import book.flow.enity.User;
import book.flow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Describe.
 * User: huang
 * Date: 17-7-3
 */
@Controller
public class test {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public void add(User user) {
        userRepository.save(user);
    }
}
