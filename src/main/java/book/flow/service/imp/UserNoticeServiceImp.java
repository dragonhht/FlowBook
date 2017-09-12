package book.flow.service.imp;

import book.flow.enity.Notice;
import book.flow.enity.User;
import book.flow.repository.NoticeRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户寻书服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserNoticeServiceImp implements UserNoticeService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public boolean addNotice(String text, int userId) {
        boolean ok = false;
        Notice notice = new Notice();
        notice.setNoticeDate(new Date());
        notice.setNoticeText(text);
        User user = userRepository.getUserById(userId);
        notice.setUser(user);
        Notice n = noticeRepository.save(notice);
        if (n != null) {
            ok = true;
        }
        return ok;
    }

}
