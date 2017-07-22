package book.flow.service.imp;

import book.flow.enity.Book;
import book.flow.enity.Notice;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.NoticeRepository;
import book.flow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import book.flow.service.TouristService;

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

    /** 分页， 每页显示的最大数量. */
    private static final int PAGE_SIZE = 20;
    /** 热门图书数量. */
    private static final int HOT_SIZE = 10;

    @Override
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
    public Page<User> searchUserByName(String name, int pageNum) {
        name = "%" + name + "%";
        Page<User> users = null;
        // 按编号排序
        Sort sort = new Sort(Sort.Direction.ASC, "userId");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        users = userRepository.searchUserByName(name, pageable);
        return null;
    }

    @Override
    public User register(User user) {
        User u = null;
        u = userRepository.save(user);
        return u;
    }

    @Override
    public Page<Notice> getNotice(int pageNum) {
        Page<Notice> notices = null;
        Sort sort = new Sort(Sort.Direction.DESC, "noticeDate");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        notices = noticeRepository.getNotice(pageable);
        return notices;
    }

    @Override
    public Page<Book> getHotBook() {
        Page<Book> books = null;
        Pageable pageable = new PageRequest(0, HOT_SIZE);
        books = bookRepository.getHotBooks(pageable);
        return books;
    }

    @Override
    public Book getBookById(int id) {
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
}
