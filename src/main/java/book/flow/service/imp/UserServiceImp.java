package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserService;
import book.flow.utils.PasswordTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务层实现类.
 * User: huang
 * Date: 17-7-21
 */
@Service
public class UserServiceImp implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(BookFlowApplication.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public User login(String text, String password) {
        User u = null;
        password = PasswordTool.encryptionMD5(password);

        try {
            int id = Integer.parseInt(text);
            u = userRepository.loginById(id, password);
        } catch (Exception e) {
            logger.info("未使用编号登录");
        }
        if (u == null) {
            u = userRepository.loginByName(text, password);
        }
        if (u == null) {
            u = userRepository.loginByPhone(text, password);
        }
        return u;
    }

    @Override
    public List<LoanRecord> getAllRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getOutRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getOutRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getHaveRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getHaveRecodeByUserId(userId);
        return records;
    }
}
