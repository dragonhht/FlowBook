package book.flow;

import book.flow.enity.Activity;
import book.flow.repository.ActivityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description.
 * User: huang
 * Date: 17-10-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestActivity {

    @Autowired
    private ActivityRepository activityRepository;


}
