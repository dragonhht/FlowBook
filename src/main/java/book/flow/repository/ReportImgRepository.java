package book.flow.repository;

import book.flow.enity.ReportImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Description.
 * User: huang
 * Date: 17-8-18
 */
public interface ReportImgRepository extends JpaRepository<ReportImg, Integer> {

    /**
     * 通过编号获取举报图片信息.
     * @param id 编号
     * @return
     */
    @Query("select r from ReportImg r where r.id = ?1")
    ReportImg getReportImgById(int id);
}
