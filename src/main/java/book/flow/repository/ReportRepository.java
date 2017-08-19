package book.flow.repository;

import book.flow.enity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 举报表操作类.
 * User: huang
 * Date: 17-8-18
 */
public interface ReportRepository extends JpaRepository<Report, Integer> {

    /**
     * 获得所有举报内容.
     * @return 举报信息
     */
    @Query("select r from Report r order by r.status asc , r.reportDate desc ")
    List<Report> getAllReport();

    /**
     * 获得所有待处理的举报.
     * @return 举报信息
     */
    @Query("select r from Report r where r.status = 0 order by r.reportDate desc ")
    List<Report> getAllWaitReport();

    /**
     * 通过编号获取举报信息.
     * @param reportId 举报编号
     * @return 举报信息
     */
    @Query("select r from Report r where r.reportId = ?1")
    Report getReportById(int reportId);

    /**
     * 举报通过.
     * @param reportId 举报编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Report r set r.status = 1 where r.reportId = ?1")
    int passReport(int reportId);

    /**
     * 举报未通过.
     * @param reportId 举报编号
     * @return
     */
    @Transactional
    @Modifying
    @Query("update Report r set r.status = 2 where r.reportId = ?1")
    int notPassReport(int reportId);
}
