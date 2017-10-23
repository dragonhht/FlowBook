package book.flow.repository;

import book.flow.enity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 活动表操作.
 * User: huang
 * Date: 17-10-22
 */
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a where a.user.identity = 0 order by a.status asc , a.activeDate desc ")
    List<Activity> getNotAdminAllActivies();

    @Query("select a from Activity a where a.user.identity = 1 order by a.status asc , a.activeDate desc ")
    List<Activity> getAdminAllActivies();

    @Query("select a from Activity a where a.activeId = ?1")
    Activity getActivitiesById(int id);

    @Transactional
    @Modifying
    @Query("update Activity a set a.status = 2 where a.activeId = ?1")
    int refuseActivity(int activeId);

    @Transactional
    @Modifying
    @Query("update Activity a set a.status = 1 where a.activeId = ?1")
    int okActivity(int activeId);

    @Query("select a from Activity a where a.status = 1 order by a.activeDate desc ")
    Page<Activity> getActivite(Pageable pageable);

    @Query("select count(a) from Activity a where a.status = 1")
    long getCountActivities();

}
