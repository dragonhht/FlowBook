package book.flow.service.imp;

import book.flow.enity.Apply;
import book.flow.enity.Book;
import book.flow.enity.ReportImg;
import book.flow.enity.User;
import book.flow.repository.*;
import book.flow.service.FileService;
import book.flow.service.UserApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户申请服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserApplyServiceImp implements UserApplyService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ReportImgRepository reportImgRepository;

    @Override
    public List<Apply> getAllAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getAllAppliesByUserId(userId);
        return applies;
    }

    @Override
    public List<Apply> getWaitAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getWaitAppliesByUserId(userId);
        return applies;
    }

    @Override
    public List<Apply> getPassAppliesByUserId(int userId) {
        List<Apply> applies = applyRepository.getPassAppliesByUserId(userId);
        return applies;
    }

    @Override
    public boolean addApply(Apply apply) {
        boolean ok = false;
        Apply a = applyRepository.save(apply);
        return ok;
    }

    @Override
    public List<Book> getBookToApply(int userId) {
        List<Book> books = recordRepository.getBookToApplyByUser(userId);
        return books;
    }

    @Override
    public boolean applyBookOut(int bookId, int userId, List<Integer> imgs) {
        boolean ok = false;
        int i = 0;
        String path;
        Book book = bookRepository.getBookById(bookId);
        User user = userRepository.getUserById(userId);
        Apply apply = new Apply();
        apply.setBook(book);
        apply.setUser(user);
        apply.setApplyDate(new Date());
        apply.setStatus("待审批");
        String text = user.getUserName() + " 申请 " + book.getBookName() + " 退出系统";
        apply.setApplyText(text);
        // apply.setApplyId(1000000);
        Set<ReportImg> imgSet = new HashSet<>();
        for (int img : imgs) {
            ReportImg im = new ReportImg();
            im.setId(img);
            imgSet.add(im);
        }
        apply.setImgs(imgSet);
        Apply apply1 = applyRepository.save(apply);
        if (apply1 != null) {
            ok = true;
        }
        return ok;
    }


    @Override
    public int saveApplyImg(MultipartFile uploadImg, int index, int bookId, int userId) {
        int id = 0;
        String imgPath = "apply_img/" + userId + "/" + bookId + "_" + index + ".png";
        imgPath = fileService.store(uploadImg, imgPath);
        imgPath = "http://localhost:8080/FlowBook/" + imgPath;
        System.out.println("图片路径" + imgPath);
        ReportImg img = new ReportImg();
        img.setPath(imgPath);
        img = reportImgRepository.save(img);
        if (img != null) {
            id = img.getId();
        }
        return id;
    }

    @Override
    public Apply getApplyById(int applyId) {
        Apply apply = null;
        apply = applyRepository.getApplyById(applyId);
        return apply;
    }

}
