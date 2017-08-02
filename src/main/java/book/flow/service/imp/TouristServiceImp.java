package book.flow.service.imp;

import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.Notice;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.NoticeRepository;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.utils.PasswordTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import book.flow.service.TouristService;

import java.util.Date;
import java.util.List;

/**
 * 游客操作服务层实现.
 * User: huang
 * Date: 17-7-20
 */
@Service
public class TouristServiceImp implements TouristService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private RecordRepository recordRepository;

    /** 分页， 每页显示的最大数量. */
    private static final int PAGE_SIZE = 20;
    /** 热门图书数量. */
    private static final int HOT_SIZE = 10;

    @Override
    //@Cacheable(value = "search_book_name")
    public Page<Book> searchBookByBookName(String name, int pageNum) {
        name = "%" + name + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookRepository.searchBookByBookName(name, pageable);
        return books;
    }

    @Override
    // @Cacheable(value = "search_book_author")
    public Page<Book> searchBookByBookAuthor(String author, int pageNum) {
        author = "%" + author + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookRepository.searchBookByBookAuthor(author, pageable);
        return books;
    }

    @Override
    // @Cacheable(value = "search_book_publish")
    public Page<Book> searchBookByBookPublish(String publish, int pageNum) {
        publish = "%" + publish + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookRepository.searchBookByBookPublish(publish, pageable);
        return books;
    }

    @Override
    // @Cacheable(value = "search_user_name")
    public Page<User> searchUserByName(String name, int pageNum) {
        name = "%" + name + "%";
        Page<User> users = null;
        // 按编号排序
        Sort sort = new Sort(Sort.Direction.ASC, "userId");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        users = userRepository.searchUserByName(name, pageable);
        return users;
    }

    @Override
    public User register(User user) {
        User u = null;
        user.setUserDate(new Date());
        String password = user.getPassword();
        password = PasswordTool.encryptionMD5(password);
        user.setPassword(password);
        u = userRepository.save(user);
        return u;
    }

    @Override
    public Page<Notice> getNotice(int pageNum) {
        Page<Notice> notices = null;
        notices = notice(pageNum, PAGE_SIZE);
        return notices;
    }

    @Override
    public Page<Notice> getIndexNotice() {
        Page<Notice> notices = null;
        notices = notice(0, HOT_SIZE);
        return notices;
    }

    /**
     * 获取公告.
     * @param pageNum 显示的页数
     * @return 公告信息
     */
    private Page<Notice> notice(int pageNum, int pageSize) {
        Page<Notice> notices = null;
        Sort sort = new Sort(Sort.Direction.DESC, "noticeDate");
        Pageable pageable = new PageRequest(pageNum, pageSize, sort);
        notices = noticeRepository.getNotice(pageable);
        return notices;
    }


    @Override
    // @Cacheable(value = "hot_boot")
    public Page<Book> getHotBook() {
        Page<Book> books = null;
        Pageable pageable = new PageRequest(0, HOT_SIZE);
        books = bookRepository.getHotBooks(pageable);
        return books;
    }

    @Override
    // @Cacheable(value = "get_book_id")
    public Book getBookById(int id) {
        System.out.println("运行");
        Book book = null;
        book = bookRepository.getBookById(id);
        return book;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        user = userRepository.getUserById(id);
        return user;
    }

    @Override
    public boolean isUserExist(String userName) {
        boolean ok = false;
        User user = userRepository.getUserByUserName(userName);
        if (user != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public User getNowOwner(int bookId) {
        User user = null;
        user = recordRepository.getNowOwnerByBookId(bookId);
        return user;
    }

    @Override
    public List<LoanRecord> getRecordByBookId(int bookId) {
        List<LoanRecord> records;
        records = recordRepository.getRecordByBookId(bookId);
        return records;
    }
}
