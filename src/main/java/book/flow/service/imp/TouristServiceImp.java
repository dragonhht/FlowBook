package book.flow.service.imp;

import book.flow.enity.Book;
import book.flow.enity.User;
import book.flow.repository.BookReqpsitory;
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
    private BookReqpsitory bookReqpsitory;
    @Autowired
    private UserRepository userRepository;

    /** 分页， 每页显示的最大数量. */
    private static final int PAGE_SIZE = 20;

    @Override
    public Page<Book> searchBookByBookName(String name, int pageNum) {
        name = "%" + name + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookReqpsitory.searchBookByBookName(name, pageable);
        return books;
    }

    @Override
    public Page<Book> searchBookByBookAuthor(String author, int pageNum) {
        author = "%" + author + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookReqpsitory.searchBookByBookAuthor(author, pageable);
        return books;
    }

    @Override
    public Page<Book> searchBookByBookPublish(String publish, int pageNum) {
        publish = "%" + publish + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(pageNum, PAGE_SIZE, sort);
        books = bookReqpsitory.searchBookByBookPublish(publish, pageable);
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
}
