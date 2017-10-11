package book.flow;


import book.flow.enity.Book;
import book.flow.enity.BookRoute;
import book.flow.enity.Comment;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.BookRouteRepository;
import book.flow.repository.CommentRepository;
import book.flow.utils.PasswordTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookFlowApplicationTests {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRouteRepository bookRouteRepository;
    

    @Test
    public void addBook() {
        for (int i = 0; i < 100; i++) {
            Book book = new Book();
            book.setBookName("图书" + i);
            book.setAuthor("作者" + i);
            book.setBookDate(new Date());
            book.setBookStart(0);
            book.setIntroduction("图书介绍" + i);
            book.setPublish("图书出版社" + i);
            bookRepository.save(book);
        }
    }

    @Test
    public void addComment() {
        User user = new User();
        user.setUserId("1000000");
        int id = 1000002;
        for (int i = 0; i < 50; i++) {
            Book book = new Book();
            id = id++;
            book.setBookId(id);
            for (int j = 0 ; j < i; j++) {
                Comment comment = new Comment();
                comment.setUser(user);
                comment.setCommentText("内容"+ j);
                comment.setCommentDate(new Date());
                comment.setBook(book);
                commentRepository.save(comment);
            }
        }
    }

    @Test
    public void searchBook() {
        String name = "图书";
        name = "%" + name + "%";
        Page<Book> books = null;
        // 按图书星级倒序
        Sort sort = new Sort(Sort.Direction.DESC, "bookStart");
        Pageable pageable = new PageRequest(0, 20, sort);
        books = bookRepository.searchBookByBookName(name, pageable);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void encryptionMD5Test() {
        String s = "你好";
        String s1 = PasswordTool.encryptionMD5(s);
        String s2 = PasswordTool.encryptionMD5(s);
        System.out.println(s1);
        assert s1.equals(s2);
    }

    @Test
    public void testHotBook() {
        Page<Book> books = null;
        Pageable pageable = new PageRequest(0, 10);
        books = bookRepository.getHotBooks(pageable);
        for (Book book : books) {
            System.out.println(book.getBookId());
        }
    }

    @Test
    public void addRoute() {
        Book book = new Book();
        book.setBookId(1000000);
        User user = new User();
        user.setUserId("1000000");
        for (int i = 0; i < 10; i++) {
            BookRoute route = new BookRoute();
            route.setBook(book);
            route.setRouteDate(new Date());
            route.setUser(user);
            bookRouteRepository.save(route);
        }
    }

    @Test
    public void getBook() {
        int id = 1000000;
        Book book = bookRepository.getBookById(id);
        for (BookRoute route : book.getBookRoute()) {
            System.out.println(route.getUser());
        }
    }

    @Test
    public void testGetHotBook() {
        Pageable pageable = new PageRequest(0, 10);
        Page<Book> books = bookRepository.getHotBooks(pageable);
        for (Book book : books) {
            System.out.println(book.getBookId());
        }
    }

    @Test
    public void testCount() {
        long n = bookRepository.getBookCountByBookName("%图%");
        System.out.println(n);
    }

    @Test
    public void getBooksByType() {
        List<Book> books = null;
        books =  bookRepository.getBookByTypeId1(1, 0, 20);
        for (Book book : books) {
            System.out.println(book.getBookId());
        }
    }

}
