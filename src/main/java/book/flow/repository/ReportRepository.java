package book.flow.repository;

import book.flow.enity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 举报表操作类.
 * User: huang
 * Date: 17-8-18
 */
public interface ReportRepository extends JpaRepository<Report, Integer> {
}
