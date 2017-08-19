package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.*;
import book.flow.model.MsgModel;
import book.flow.repository.*;
import book.flow.service.FileService;
import book.flow.service.UserService;
import book.flow.utils.MailUtils;
import book.flow.utils.PasswordTool;
import org.omg.CORBA.INTERNAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.BinaryClient;

import java.util.*;

/**
 * 用户服务层实现类.
 * User: huang
 * Date: 17-7-21
 */
@Service
public class UserServiceImp implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(BookFlowApplication.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private ApplyRepository applyRepository;
    @Autowired
    private ImgRepository imgRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private ChatRecordRepository chatRecordRepository;
    @Autowired
    private FlowApplyRepository flowApplyRepository;
    @Autowired
    private ReportImgRepository reportImgRepository;
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public User login(String text, String password, int role) {
        User u = null;
        password = PasswordTool.encryptionMD5(password);

        try {
            int id = Integer.parseInt(text);
            u = userRepository.loginById(id, password, role);
        } catch (Exception e) {
            logger.info("未使用编号登录");
        }
        if (u == null) {
            u = userRepository.loginByName(text, password, role);
        }
        if (u == null) {
            u = userRepository.loginByPhone(text, password, role);
        }
        return u;
    }

    @Override
    public List<LoanRecord> getAllRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getOutRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getOutRecodeByUserId(userId);
        return records;
    }

    @Override
    public List<LoanRecord> getHaveRecode(int userId) {
        List<LoanRecord> records = null;
        records = recordRepository.getHaveRecodeByUserId(userId);
        return records;
    }

    @Override
    public boolean addComment(String text, int userId, int bookId) {
        boolean ok = false;
        User user = userRepository.getUserById(userId);
        Book book = bookRepository.getBookById(bookId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBook(book);
        comment.setCommentText(text);
        comment.setCommentDate(new Date());
        Comment comment1 = commentRepository.save(comment);
        if (comment1 != null) {
            ok = true;
        }
        return ok;
    }

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

    @Override
    @Transactional
    public Book uploadBook(Book book, int userId) {
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
        Set<Img> imgSet = new HashSet<>();
        for (int img : imgs) {
            Img im = new Img();
            im.setImgId(img);
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
    public Img saveImg(Img img) {
        Img img1 = imgRepository.save(img);
        return img1;
    }

    @Override
    public boolean addFriend(int selfId, int friendId) {
        boolean ok = false;
        User self = userRepository.getUserById(selfId);
        User friend = userRepository.getUserById(friendId);
        Friends friends = new Friends();
        friends.setFriend(friend);
        friends.setSelf(self);
        Friends f = friendsRepository.save(friends);
        if (f != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean isFriendExist(int selfId, int friendId) {
        boolean ok = false;
        Friends friend = friendsRepository.isFriendExist(selfId, friendId);
        if (friend != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public List<User> getFriends(int selfId) {
        List<User> friends = null;
        friends = friendsRepository.getUserFriends(selfId);
        return friends;
    }

    @Override
    public List<Integer> getFriendsId(int selfId) {
        List<Integer> ids = chatRecordRepository.getSenderId(selfId);
        if (ids == null) {
            ids = new ArrayList<>();
        }
        return ids;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        user = userRepository.getUserById(userId);
        return user;
    }

    @Override
    public boolean addChatRecord(ChatRecord record) {
        boolean ok = false;
        ChatRecord r = chatRecordRepository.save(record);
        if (r != null) {
            ok = true;
        }
        return ok;
    }

    @Override
    public long msgCount(int userId) {
        long count = 0;
        count = chatRecordRepository.haveMsg(userId);
        return count;
    }

    @Override
    public List<Integer> getSenderId(int userId) {
        List<Integer> idList = null;
        idList = chatRecordRepository.getSenderId(userId);
        return idList;
    }

    @Override
    public List<MsgModel> getFriendMsg(int selfId, int friendId) {
        List<MsgModel> messages = null;
        messages = chatRecordRepository.getFriendMsg(selfId, friendId);
        return messages;
    }

    @Override
    public String getUserImg(int userId) {
        String s = "";
        s= userRepository.getUserImg(userId);
        return s;
    }

    @Override
    public List<MsgModel> getToFriendMsg(int selfId, int friendId) {
        List<MsgModel> messages = null;
        messages = chatRecordRepository.getToFriendMsg(selfId, friendId);
        return messages;
    }

    @Override
    public List<User> getNotFriend(int selfId) {
        List<User> users = null;
        List<Integer> ids = friendsRepository.getUserFriendsId(selfId);
        users = chatRecordRepository.getNotFriend(selfId, ids);
        return users;
    }

    @Override
    public boolean setChatReaded(int selfId, int friendId) {
        boolean ok = false;
        int c = 0;
        c = chatRecordRepository.setChatReaded(selfId, friendId);
        if (c != 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean delFriend(int selfId, int friendId) {
        boolean ok = false;
        int l = 0;
        l = friendsRepository.delFriend(selfId, friendId);
        if (l != 0) {
            ok = true;
        }
        return ok;
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
    public boolean updateUserEmail(String email, int userId) {
        boolean ok = false;
        int i = 0;
        i = userRepository.updateUserEmail(email, userId);
        if (i != 0) {
            ok = true;
        }
        return ok;
    }

    @Override
    public boolean updateUserImg(String path, int userId) {
        boolean ok = false;
        int i = 0;
        i = userRepository.updateUserImg(path, userId);
        if (i > 0) {
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
        Img img = new Img();
        img.setImgPath(imgPath);
        img = imgRepository.save(img);
        if (img != null) {
            id = img.getImgId();
        }
        return id;
    }

    @Override
    public Apply getApplyById(int applyId) {
        Apply apply = null;
        apply = applyRepository.getApplyById(applyId);
        return apply;
    }

    @Override
    public boolean isOkToFlow(int bookId) {
        boolean ok = false;
        LoanRecord record = recordRepository.getNowRecordByBook(bookId);
        Date date = record.getRecordDate();
        Calendar calendar_0 = Calendar.getInstance();
        calendar_0.setTime(date);
        long time_0 = calendar_0.getTimeInMillis();
        calendar_0.setTime(new Date());
        long time_1 = calendar_0.getTimeInMillis();
        long day = (time_1-time_0)/(1000*3600*24);
        if (day > 30) {
            ok = true;
        }
        return ok;
    }

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
    public int saveReportImg(int index, int reportedId, int userId, MultipartFile img) {
        int imgId = 0;
        String imgPath = "report_img/" + userId + "/" + reportedId + "_" + index + ".png";
        imgPath = fileService.store(img, imgPath);
        imgPath = "http://localhost:8080/FlowBook/" + imgPath;
        System.out.println("图片路径" + imgPath);
        ReportImg i = new ReportImg();
        i.setPath(imgPath);
        ReportImg img1 = reportImgRepository.save(i);
        if (img1 != null) {
            imgId = img1.getId();
        }
        return imgId;
    }

    @Override
    public boolean saveReport(int reportId, int beReportId, String text, String[] img) {
        boolean ok = false;
        User report = userRepository.getUserById(reportId);
        User beReport = userRepository.getUserById(beReportId);
        Set<ReportImg> imgSet = new HashSet<>();
        if (img != null) {
            try {
                for (String i : img) {
                    int a = Integer.parseInt(i);
                    ReportImg img1 = reportImgRepository.getReportImgById(a);
                    imgSet.add(img1);
                }
            } catch (Exception e) {
                logger.debug("无法转换为整数");
            }
        }
        Report r = new Report();
        r.setBeReport(beReport);
        r.setImg(imgSet);
        r.setReport(report);
        r.setReportDate(new Date());
        r.setReportText(text);
        Report re = reportRepository.save(r);
        if (re != null) {
            ok = true;
        }
        return ok;
    }


}
