package book.flow.repository;

import book.flow.enity.ApplyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description.
 * User: huang
 * Date: 17-10-28
 */
public interface ApplyAdminRepository extends JpaRepository<ApplyAdmin, Integer> {

    @Query("select a from ApplyAdmin a where a.status = 0 order by a.applyDate desc ")
    List<ApplyAdmin> getAllApplies();

    @Transactional
    @Modifying
    @Query("update ApplyAdmin a set a.status = 1 where a.applyId = ?1")
    int okApply(int applyId);

    @Transactional
    @Modifying
    @Query("update ApplyAdmin a set a.status = 2 where a.applyId = ?1")
    int delApply(int applyId);

}
