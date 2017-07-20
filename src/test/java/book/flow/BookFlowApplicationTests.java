package book.flow;


import book.flow.enity.Book;
import book.flow.repository.BookReqpsitory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import service.TouristService;
import service.imp.TouristServiceImp;

import javax.jws.WebService;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookFlowApplicationTests {
    @Autowired
    private BookReqpsitory bookReqpsitory;

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
            bookReqpsitory.save(book);
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
        books = bookReqpsitory.searchBookByBookName(name, pageable);
        for (Book book : books) {
            System.out.println(book);
        }
    }

}
