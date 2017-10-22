package book.flow.repository;

import book.flow.enity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 活动表操作.
 * User: huang
 * Date: 17-10-22
 */
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
}
