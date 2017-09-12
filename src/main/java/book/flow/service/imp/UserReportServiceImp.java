package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.enity.Report;
import book.flow.enity.ReportImg;
import book.flow.enity.User;
import book.flow.repository.ReportImgRepository;
import book.flow.repository.ReportRepository;
import book.flow.repository.UserRepository;
import book.flow.service.FileService;
import book.flow.service.UserReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户举报服务实现.
 * User: huang
 * Date: 17-9-12
 */
@Service
public class UserReportServiceImp implements UserReportService {

    private static final Logger logger = LoggerFactory.getLogger(BookFlowApplication.class);

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportImgRepository reportImgRepository;

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
