package book.flow.service.imp;

import book.flow.enity.LoanRecord;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserHomeService;
import book.flow.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Description.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserHomeServiceImp implements UserHomeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<LoanRecord> getAllRecode(String userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getOutRecode(String userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getOutRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getHaveRecode(String userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getHaveRecodeByUserId(userId);
        return records;
    }


    @Override
    public String checkEmail(String email) {
        boolean ok = false;
        Random random = new Random();
        int code = random.nextInt(999) + 1000;
        MailUtils mailUtils = MailUtils.getMailUtils();
        String context = "<p>您正在尝试校验图书漂流所要绑定的邮箱， 若是本人操作请将以下校验码填入， 若非本人操作则有可能账号已被盗<p><br/>" +
                "<h3>校验码</h3>" +
                "<h1>" + code + "</h1>";
        ok = mailUtils.sendMailByQQ(email, "图书漂流邮箱校验", context);
        if (!ok) {
            code = 0;
        }
        return String.valueOf(code);
    }

    @Override
    public boolean updateUserEmail(String email, String userId) {
        boolean ok = false;
        int i = 0;
        i = userRepository.updateUserEmail(email, userId);
        if (i != 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean updateUserImg(String path, String userId) {
        boolean ok = false;
        int i = 0;
        i = userRepository.updateUserImg(path, userId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }
}
