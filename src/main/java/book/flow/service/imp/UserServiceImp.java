package book.flow.service.imp;

import book.flow.enity.User;
import book.flow.repository.UserRepository;
import book.flow.service.UserService;
import book.flow.utils.PasswordTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务层实现类.
 * User: huang
 * Date: 17-7-21
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(User user) {
        int id = user.getUserId();
        String password = user.getPassword();
        password = PasswordTool.encryptionMD5(password);
        User u = userRepository.login(id, password);
        return u;
    }
}
