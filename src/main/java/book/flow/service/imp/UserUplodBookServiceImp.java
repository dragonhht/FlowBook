package book.flow.service.imp;

import book.flow.enity.Book;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserUplodBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户新书漂流服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserUplodBookServiceImp implements UserUplodBookService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public Book uploadBook(Book book, String userId) {
        User user = userRepository.getUserById(userId);
        book.setBookDate(new Date());
        book.setContributor(user);
        Book b = bookRepository.save(book);
        userRepository.updataContributeNum(userId);
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
}
