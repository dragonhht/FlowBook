package book.flow.service.imp;

import book.flow.enity.*;
import book.flow.repository.*;
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
    @Autowired
    private FriendsRepository friendsRepository;

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
    public long getBookCountByBookName(String name) {
        name = "%" + name + "%";
        long count = bookRepository.getBookCountByBookName(name);
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count/PAGE_SIZE + add;
        return count;
    }

    @Override
    public long getBookCountByBookAuthor(String author) {
        author = "%" + author + "%";
        long count = bookRepository.getBookCountByBookAuthor(author);
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count/PAGE_SIZE + add;
        return count;
    }

    @Override
    public long getBookCountByBookPublish(String publish) {
        publish = "%" + publish + "%";
        long count = bookRepository.getBookCountByBookPublish(publish);
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count/PAGE_SIZE + add;
        return count;
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
        List<User> users = null;
        User user = null;
        users = recordRepository.getNowOwnerByBookId(bookId);
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }

    @Override
    public List<LoanRecord> getRecordByBookId(int bookId) {
        List<LoanRecord> records;
        records = recordRepository.getRecordByBookId(bookId);
        return records;
    }

    @Override
    public long getNoticesPageCount() {
        long num = 0;
        int add = 0;
        num = noticeRepository.count();
        if (num % PAGE_SIZE != 0) {
            add = 1;
        }
        num = num / PAGE_SIZE + add;
        return num;
    }

    @Override
    public List<LoanRecord> getRecordsByUserId(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getRecodeByUserId(userId);
        return records;
    }

    @Override
    public long getSearchUserPageSize(String name) {
        name = "%" + name + "%";
        long count = userRepository.getSearchUserCount(name);
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count / PAGE_SIZE + add;
        return count;
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
    public List<Book> getBookByTypeId(int typeId, int pageNum) {
        List<Book> books = null;
        int start = pageNum * PAGE_SIZE;
        books = bookRepository.getBookByTypeId1(typeId, start, PAGE_SIZE);
        return books;
    }

    @Override
    public long getBookByTypeIdCount(int typeId) {
        long count = bookRepository.getBookByTypeCount(typeId);
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count / PAGE_SIZE + add;
        return count;
    }
}
