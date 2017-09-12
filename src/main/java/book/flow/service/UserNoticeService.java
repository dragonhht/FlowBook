package book.flow.service;

/**
 * 用户寻书服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserNoticeService {

    /**
     * 上传期望.
     * @param text 内容
     * @param userId 用户编号
     * @return 是否成功， true为成功
     */
    boolean addNotice(String text, int userId);

}
