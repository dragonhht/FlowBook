package book.flow;

import book.flow.enity.User;
import book.flow.repository.UserRepository;
import book.flow.utils.PasswordTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户相关操作测试.
 * User: huang
 * Date: 17-7-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setPassword(PasswordTool.encryptionMD5("用户" + i));
            user.setUserAddress("用户家" + i);
            user.setUserAge(18);
            user.setUserDate(new Date());
            user.setUserEmail("dragon_hht@163.com");
            user.setUserName("用户名" + i);
            user.setUserPhone("12345678932");
            user.setUserSex("男");
            user.setUserImg("头像");
            userRepository.save(user);
        }
    }

    @Test
    public void testLogin() {
        User user = userRepository.login(10, PasswordTool.encryptionMD5("用户9"));
        System.out.println(user);
    }

}