package book.flow.service.imp;

import book.flow.enity.*;
import book.flow.repository.*;
import book.flow.utils.PasswordTool;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
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
    @Autowired
    private ActivityRepository activityRepository;

    /** 分页， 每页显示的最大数量. */
    private static final int PAGE_SIZE = 20;
    /** 热门图书数量. */
    private static final int HOT_SIZE = 4;
    private static final int INDEX_ACTIVE_SIZE = 6;


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
        Friends friends = new Friends();
        friends.setSelf(u);
        friends.setFriend(u);
        friendsRepository.save(friends);
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
    public Page<Book> getHotBook(int size) {
        Page<Book> books = null;
        Pageable pageable = new PageRequest(0, size);
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
    public User getUserById(String id) {
        User user = null;
        user = userRepository.getUserById(id);
        return user;
    }

    @Override
    public boolean isUserNotExist(String userName) {
        boolean ok = false;
        User user = userRepository.getUserByUserName(userName);
        if (user == null) {
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
    public List<LoanRecord> getRecordsByUserId(String userId) {
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
    public boolean isFriendExist(String selfId, String friendId) {
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

    @Override
    public Page<Activity> getActivity() {
        Page<Activity> activities = null;
        Pageable pageable = new PageRequest(0, INDEX_ACTIVE_SIZE);
        activities = activityRepository.getActivite(pageable);
        return activities;
    }

    @Override
    public Activity getActivityById(int activeId) {
        return activityRepository.getActivitiesById(activeId);
    }

    @Override
    public Page<Activity> getActiveList(int pageNum) {
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE);
        return activityRepository.getActivite(pageable);
    }

    @Override
    public long getActivitiesCount() {
        long count = activityRepository.getCountActivities();
        int add = 0;
        if (count % PAGE_SIZE != 0) {
            add = 1;
        }
        count = count / PAGE_SIZE + add;
        return count;
    }

    @Override
    public Page<Book> filterSearchBookByName(String name, int types, int pageNum) {
        name = "%" + name + "%";

            Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
            Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
            List<Book> books = bookRepository.searchBookByBookName(name, types);
            Page<Book> page = new PageImpl<Book>(books, pageable, books.size());
            return page;
    }

    @Override
    public Page<Book> filterSearchBookByAuthor(String name, int types, int pageNum) {
        name = "%" + name + "%";
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        List<Book> books = bookRepository.searchBookByBookAuthor(name, types);
        Page<Book> page = new PageImpl<Book>(books, pageable, books.size());
        return page;

    }

    @Override
    public Page<Book> filterSearchBookByPublish(String name, int types, int pageNum) {
        name = "%" + name + "%";
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        List<Book> books = bookRepository.searchBookByBookPublish(name, types);
        Page<Book> page = new PageImpl<Book>(books, pageable, books.size());
        return page;
    }

    @Override
    public Page<User> orderUserSearch(String name, String types, String order, int pageNum) {
        name = "%" + name + "%";
        Page<User> users = null;
        Sort sort = null;
        Sort.Direction direction = null;
        String type = "";
        if ("date".equals(types)) {
            type = "userDate";
        }
        if ("get".equals(types)) {
            type = "contributeNum";
        }
        if ("belive".equals(types)) {
            type = "credit";
        }
        if ("up".equals(order)) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        sort = new Sort(direction, type);
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        users = userRepository.searchUserByName(name, pageable);
        return users;
    }

    @Override
    public Page<Book> getGoodBook(int size) {
        Pageable pageable = new PageRequest(0, size);
        return bookRepository.getGoodBook(pageable);
    }

    @Override
    public boolean isExistPhone(String phone) {
        boolean ok = false;
        int num = userRepository.findUserByPhone(phone);
        if (num > 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean isExistEmail(String email) {
        boolean ok = false;
        int num = userRepository.findUserCountByEmail(email);
        if (num > 0) {
            ok = true;
        }
        return ok;
    }
}
