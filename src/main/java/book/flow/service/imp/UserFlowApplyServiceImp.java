package book.flow.service.imp;

import book.flow.enity.Book;
import book.flow.enity.FlowApply;
import book.flow.enity.LoanRecord;
import book.flow.enity.User;
import book.flow.repository.BookRepository;
import book.flow.repository.FlowApplyRepository;
import book.flow.repository.RecordRepository;
import book.flow.repository.UserRepository;
import book.flow.service.UserFlowApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * 用户图书传阅服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserFlowApplyServiceImp implements UserFlowApplyService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FlowApplyRepository flowApplyRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public boolean saveFlowApply(int bookId, int toUserId, String wantSay, int userId) {
        boolean ok = false;
        Book book = bookRepository.getBookById(bookId);
        User toUser = userRepository.getUserById(toUserId);
        User user = userRepository.getUserById(userId);
        FlowApply apply = new FlowApply();
        apply.setApplyUser(user);
        apply.setBook(book);
        apply.setOkUser(toUser);
        apply.setStatus(0);
        apply.setWantSay(wantSay);
        apply.setApplyDate(new Date());
        FlowApply a = flowApplyRepository.save(apply);
        if (a != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public List<FlowApply> getFlowApplyByToUser(int toUserId) {
        List<FlowApply> applies = null;
        applies = flowApplyRepository.getApplyByToUser(toUserId);
        return applies;
    }

    @Override
    public List<FlowApply> getNotLookApplyByToUser(int toUserId) {
        List<FlowApply> applies = null;
        applies = flowApplyRepository.getNotLookApplyByToUser(toUserId);
        return applies;
    }

    @Override
    public List<FlowApply> getLookedApplyByToUser(int toUserId) {
        List<FlowApply> applies = null;
        applies = flowApplyRepository.getLookedApplyByToUser(toUserId);
        return applies;
    }

    @Override
    public List<FlowApply> getDealingApplyByToUser(int toUserId) {
        List<FlowApply> applies = null;
        applies = flowApplyRepository.getDealingApplyByToUser(toUserId);
        return applies;
    }

    @Override
    public List<FlowApply> getMyFlowApplies(int userId) {
        List<FlowApply> applies = null;
        applies = flowApplyRepository.getMyFlowApplies(userId);
        return applies;
    }

    @Override
    public FlowApply getFlowApplyById(int flowBookId) {
        FlowApply apply = null;
        apply = flowApplyRepository.getFlowApplyById(flowBookId);
        return apply;
    }

    @Override
    public boolean dealFlowApply(int flowApplyId) {
        boolean ok = false;
        int i = 0;
        i = flowApplyRepository.dealFlowApply(flowApplyId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean flowBookToNext(int flowApplyId) {
        boolean ok  =false;
        FlowApply apply = flowApplyRepository.getFlowApplyById(flowApplyId);
        User user = apply.getApplyUser();
        Book book = apply.getBook();
        flowApplyRepository.agreedApply(flowApplyId);
        int bookId = apply.getBook().getBookId();
        List<LoanRecord> records = recordRepository.getBookNowLoanRecord(bookId);
        if (records != null && records.size() > 0) {
            LoanRecord record = records.get(0);
            record.setOut(true);
            recordRepository.save(record);
            LoanRecord r = new LoanRecord();
            r.setOut(false);
            r.setUser(user);
            r.setRecordDate(new Date());
            r.setBook(book);
            recordRepository.save(r);
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean refuseFlowApply(String refuse, int applyId) {
        boolean ok = false;
        int i = 0;
        i = flowApplyRepository.refuseFlowApply(refuse, applyId);
        if (i > 0) {
            ok = true;
        }
        return ok;
    }

}
