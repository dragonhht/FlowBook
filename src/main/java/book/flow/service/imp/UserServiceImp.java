package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.Book;
import book.flow.enity.Comment;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.CommentRepository;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserService;
import book.flow.utils.PasswordTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

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

    @Override
    public boolean addComment(String text, int userId, int bookId) {
        boolean ok = false;
        User user = userRepository.getUserById(userId);
        Book book = bookRepository.getBookById(bookId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBook(book);
        comment.setCommentText(text);
        comment.setCommentDate(new Date());
        Comment comment1 = commentRepository.save(comment);
        if (comment1 != null) {
            ok = true;
        }
        return ok;
    }
}
