package book.flow.service.imp;

import book.flow.enity.Apply;
import book.flow.enity.Book;
import book.flow.enity.Img;
import book.flow.enity.Report;
import book.flow.repository.*;
import book.flow.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 管理员服务接口实现类.
 * User: huang
 * Date: 17-8-17
 */
@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FlowApplyRepository flowApplyRepository;
    @Autowired
    private ImgRepository imgRepositoryl;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Apply> getAllApplies() {
        List<Apply> applies = null;
        applies = applyRepository.getAllApplies();
        return applies;
    }

    @Override
    public void delBook(int applyId) {
        Apply apply = applyRepository.getApplyById(applyId);
        Book book = apply.getBook();
        recordRepository.delRecordByBook(book.getBookId());
        commentRepository.delCommentByBook(book.getBookId());
        Set<Img> imgSet = imgRepositoryl.getImgByBook(book.getBookId());
        for (Img img : imgSet) {
            imgRepositoryl.delete(img.getImgId());
        }
        applyRepository.delApplyByBook(book.getBookId());
        flowApplyRepository.delFlowApplyByBook(book.getBookId());
        int i = bookRepository.delBookById(book.getBookId());
        System.out.println(i);
    }

    @Override
    public boolean refuseApply(int applyId) {
        boolean ok = false;
        int i = 0;
        i = applyRepository.refuseApply(applyId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public List<Report> getAllReport() {
        List<Report> reports = null;
        reports = reportRepository.getAllReport();
        return reports;
    }

    @Override
    public List<Report> getAllWaitReport() {
        List<Report> reports = null;
        reports = reportRepository.getAllWaitReport();
        return reports;
    }

    @Override
    public Report getReportById(int reportId) {
        Report report = null;
        report = reportRepository.getReportById(reportId);
        return report;
    }

    @Override
    public boolean passReport(int reportId) {
        boolean ok = false;
        int i = 0;
        i = reportRepository.passReport(reportId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean notPassReport(int reportId) {
        boolean ok = false;
        int i = 0;
        i = reportRepository.notPassReport(reportId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }
}