package book.flow.repository;

import book.flow.enity.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 借阅记录操作.
 * User: huang
 * Date: 17-7-22
 */
public interface RecordRepository extends JpaRepository<LoanRecord, Integer> {

}
