package book.flow.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户举报服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserReportService {

    /**
     * 保存举报图片.
     * @param index 图片序号
     * @param reportedId 被举报人编号
     * @param userId 举报人编号
     * @param img 图片
     * @return 图片编号
     */
    int saveReportImg(int index, int reportedId, int userId, MultipartFile img);

    /**
     * 保存举报.
     * @param reportId 举报人
     * @param beReportId 被举报人
     * @param text 举报说明
     * @param img 图片
     * @return 结果
     */
    boolean saveReport(int reportId, int beReportId, String text, String[] img);

}
