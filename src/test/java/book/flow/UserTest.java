package book.flow;

import book.flow.enity.*;
import book.flow.repository.*;
import book.flow.utils.PasswordTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ApplyRepository applyRepository;

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
            user.setUserPhone("15180133043");
            user.setUserSex("男");
            user.setUserImg("头像");
            userRepository.save(user);
        }
    }

    @Test
    public void testLogin() {
        User user = userRepository.loginById(1000009, PasswordTool.encryptionMD5("用户9"));
        Book book = bookRepository.getBookById(1000000);
        userRepository.save(user);
    }

    @Test
    public void addNotice() {
        User user = new User();
        user.setUserId(1000000);
        for (int i = 0; i < 100; i++) {
            Notice notice = new Notice();
            notice.setNoticeDate(new Date());
            notice.setNoticeText("内容：：：：" + i);
            notice.setUser(user);
            noticeRepository.save(notice);
        }
    }
    @Test
    public void testRecode() {
        User user = userRepository.getUserById(1000000);
        Book book = bookRepository.getBookById(1000000);
        LoanRecord record = new LoanRecord();
        record.setUser(user);
        record.setRecordDate(new Date());
        record.setOut(false);
        record.setBook(book);
        recordRepository.save(record);
    }

    @Test
    public void addComment() throws InterruptedException {
        User user = userRepository.getUserById(1000002);
        Book book = bookRepository.getBookById(1000002);
        for (int i = 0; i < 100; i++) {
            Comment comment = new Comment();
            comment.setBook(book);
            comment.setUser(user);
            comment.setCommentDate(new Date());
            comment.setCommentText("评论内容：：： " + i);
            commentRepository.save(comment);
            Thread.sleep(1000);
        }
    }

    @Test
    public void addApply() throws InterruptedException {
        User user = userRepository.getUserById(1000000);
        for (int i = 0; i < 50; i++) {
            Apply apply = new Apply();
            if (i % 3 == 0) {
                apply.setStatus("待审批");
            }
            if (i % 5 == 0) {
                apply.setStatus("已通过");
            }
            apply.setApplyDate(new Date());
            apply.setApplyText("请求: " + i);
            apply.setUser(user);
            applyRepository.save(apply);
            Thread.sleep(1000);
        }
    }

    @Test
    public void setApplyBook() {
        /*Book book = bookRepository.getBookById(1000000);
        List<Apply> applies = applyRepository.findAll();
        applies.get(0).setBook(book);
        applyRepository.save(applies.get(0));*/
        List<Book> books = recordRepository.getBookToApplyByUser(1000000);
        for (Book book : books) {
            System.out.println(book.getBookId());
        }
    }
}
