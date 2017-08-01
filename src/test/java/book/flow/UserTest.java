package book.flow;

import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.Notice;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.NoticeRepository;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.utils.PasswordTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        LoanRecord record = new LoanRecord();
        record.setBook(book);
        record.setOut(true);
        record.setRecordDate(new Date());
        record.setUser(user);
        recordRepository.save(record);
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
        List<LoanRecord> records = recordRepository.getRecodeByUserId(1000009);
        for (LoanRecord record : records) {
            System.out.println(record);
        }
    }
}
