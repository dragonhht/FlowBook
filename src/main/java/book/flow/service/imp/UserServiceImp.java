package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.*;
import book.flow.repository.*;
import book.flow.service.FileService;
import book.flow.service.UserService;
import book.flow.utils.PasswordTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private ImgRepository imgRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private ChatRecordRepository chatRecordRepository;

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

    @Override
    public boolean addNotice(String text, int userId) {
        boolean ok = false;
        Notice notice = new Notice();
        notice.setNoticeDate(new Date());
        notice.setNoticeText(text);
        User user = userRepository.getUserById(userId);
        notice.setUser(user);
        Notice n = noticeRepository.save(notice);
        if (n != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    @Transactional
    public Book uploadBook(Book book, int userId) {
        User user = userRepository.getUserById(userId);
        book.setBookDate(new Date());
        book.setContributor(user);
        Book b = bookRepository.save(book);
        LoanRecord record = new LoanRecord();
        record.setBook(book);
        record.setOut(false);
        record.setRecordDate(new Date());
        record.setUser(user);
        recordRepository.save(record);
        return b;
    }

    @Override
    public void updateBookImg(String imgPath, int bookId) {
        bookRepository.updateBookImg(imgPath, bookId);
    }

    @Override
    public List<Apply> getAllAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getAllAppliesByUserId(userId);
        return applies;
    }

    @Override
    public List<Apply> getWaitAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getWaitAppliesByUserId(userId);
        return applies;
    }

    @Override
    public List<Apply> getPassAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getPassAppliesByUserId(userId);
        return applies;
    }

    @Override
    public boolean addApply(Apply apply) {
        boolean ok = false;
        Apply a = applyRepository.save(apply);
        return ok;
    }

    @Override
    public List<Book> getBookToApply(int userId) {
        List<Book> books = recordRepository.getBookToApplyByUser(userId);
        return books;
    }

    @Override
    public boolean applyBookOut(int bookId, int userId, MultipartFile[] imgs) {
        boolean ok = false;
        int i = 0;
        String path;
        Book book = bookRepository.getBookById(bookId);
        User user = userRepository.getUserById(userId);
        Apply apply = new Apply();
        apply.setBook(book);
        apply.setUser(user);
        apply.setApplyDate(new Date());
        apply.setStatus("待审批");
        String text = user.getUserName() + " 申请 " + book.getBookName() + " 退出系统";
        apply.setApplyText(text);
        apply.setApplyId(1000000);
        Apply apply1 = applyRepository.save(apply);
        for (MultipartFile img : imgs) {
            StringBuilder imgPath = new StringBuilder("bookCover/apply/");
            imgPath = imgPath.append(userId + "/");
            path = "";
            imgPath = imgPath.append(bookId + "_" + i + ".png");
            i++;
            path = fileService.store(img, String.valueOf(imgPath));
            path  = "http://localhost:8080/FlowBook/files/" + path;
            Img img1 = new Img();
            img1.setImgPath(path);
            img1.setApply(apply1);
            Img g = saveImg(img1);
        }
        if (apply1 != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public Img saveImg(Img img) {
        Img img1 = imgRepository.save(img);
        return img1;
    }

    @Override
    public boolean addFriend(int selfId, int friendId) {
        boolean ok = false;
        User self = userRepository.getUserById(selfId);
        User friend = userRepository.getUserById(friendId);
        Friends friends = new Friends();
        friends.setFriend(friend);
        friends.setSelf(self);
        Friends f = friendsRepository.save(friends);
        if (f != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean isFriendExist(int selfId, int friendId) {
        boolean ok = false;
        Friends friend = friendsRepository.isFriendExist(selfId, friendId);
        if (friend != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public List<User> getFriends(int selfId) {
        List<User> friends = null;
        friends = friendsRepository.getUserFriends(selfId);
        return friends;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        user = userRepository.getUserById(userId);
        return user;
    }

    @Override
    public boolean addChatRecord(ChatRecord record) {
        boolean ok = false;
        ChatRecord r = chatRecordRepository.save(record);
        if (r != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public long msgCount(int userId) {
        long count = 0;
        count = chatRecordRepository.haveMsg(userId);
        return count;
    }

    @Override
    public List<Integer> getSenderId(int userId) {
        List<Integer> idList = null;
        idList = chatRecordRepository.getSenderId(userId);
        return idList;
    }

    @Override
    public List<String> getFriendMsg(int selfId, int friendId) {
        List<String> messages = null;
        messages = chatRecordRepository.getFriendMsg(selfId, friendId);
        return messages;
    }
}
