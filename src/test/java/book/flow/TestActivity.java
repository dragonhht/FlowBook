package book.flow;

import book.flow.enity.Activity;
import book.flow.repository.ActivityRepository;
import book.flow.repository.BookRepository;
import book.flow.service.TouristService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Description.
 * User: huang
 * Date: 17-10-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActivity {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TouristService touristService;

    @Test
    public void s() {
        int s = bookRepository.searchBookByBookName("%%", 7, 5).size();
        System.out.println(s);
    }


}
