package book.flow.service;

import book.flow.enity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户申请服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserApplyService {

    /**
     * 通过用户编号获取所有申请.
     * @param userId 用户编号
     * @return 用户的所有申请
     */
    List<Apply> getAllAppliesByUserId(String userId);

    /**
     * 通过用户编号获取待审批的申请.
     * @param userId 用户编号
     * @return 待审批的申请
     */
    List<Apply> getWaitAppliesByUserId(String userId);

    /**
     * 通过用户编号获取已审核的申请.
     * @param userId 用户编号
     * @return 已审核的申请
     */
    List<Apply> getPassAppliesByUserId(String userId);

    /**
     * 保存申请.
     * @param apply 申请信息
     * @return 是否保存成功， true为保存成功
     */
    boolean addApply(Apply apply);

    /**
     * 通过用户查询可申请的书籍.
     * @param userId 用户编号
     * @return 可申请的书籍
     */
    List<Book> getBookToApply(String userId);

    /**
     * 保存图书退出申请.
     * @param bookId 图书编号
     * @param userId 用户编号
     * @param imgs 图片
     * @return 是否保存成功， true为成功
     */
    boolean applyBookOut(int bookId, String userId, List<Integer> imgs);


    /**
     * 保存申请图片.
     * @param uploadImg 图片
     * @param index 序号
     * @param bookId 图书编号
     * @param userId 用户编号
     * @return 图片编号
     */
    int saveApplyImg(MultipartFile uploadImg, int index, int bookId, String userId);

    /**
     * 通过申请编号获取申请.
     * @param applyId 申请编号
     * @return 申请内容
     */
    Apply getApplyById(int applyId);

    /**
     * 保存活动.
     * @param activity
     * @param user
     * @return
     */
    boolean saveActivity(Activity activity, User user);

    /**
     * 获取我的管理员申请.
     * @return
     */
    List<ApplyAdmin> getMyApplyAdmin();

}
