package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.*;
import book.flow.model.MsgModel;
import book.flow.repository.*;
import book.flow.service.FileService;
import book.flow.service.UserService;
import book.flow.utils.MailUtils;
import book.flow.utils.PasswordTool;
import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.BinaryClient;

import java.util.*;

/**
 * 用户基本服务层实现类.
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
    @Autowired
    private ChatRecordRepository chatRecordRepository;
    @Autowired
    private ReportImgRepository reportImgRepository;


    @Override
    public User login(String text, String password, int role) {
        User u = null;
        password = PasswordTool.encryptionMD5(password);

        u = userRepository.loginById(text, password, role);

        if (u == null) {
            u = userRepository.loginByName(text, password, role);
        }
        if (u == null) {
            u = userRepository.loginByPhone(text, password, role);
        }
        if (u == null) {
            u = userRepository.loginByEmail(text, password, role);
        }
        return u;
    }


    @Override
    public boolean addComment(String text, String userId, int bookId) {
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

    @Override
    public ReportImg saveImg(ReportImg img) {
        ReportImg img1 = reportImgRepository.save(img);
        return img1;
    }

    @Override
    public User getUserById(String userId) {
        User user = null;
        user = userRepository.getUserById(userId);
        return user;
    }

    @Override
    public long msgCount(String userId) {
        long count = 0;
        count = chatRecordRepository.haveMsg(userId);
        return count;
    }

    @Override
    public boolean isOkToFlow(int bookId) {
        boolean ok = false;
        LoanRecord record = recordRepository.getNowRecordByBook(bookId);
        Date date = record.getRecordDate();
        Calendar calendar_0 = Calendar.getInstance();
        calendar_0.setTime(date);
        long time_0 = calendar_0.getTimeInMillis();
        calendar_0.setTime(new Date());
        long time_1 = calendar_0.getTimeInMillis();
        long day = (time_1-time_0)/(1000*3600*24);
        if (day > 30) {
            ok = true;
        }
        return ok;
    }

}
