package book.flow.repository;

import book.flow.enity.BookRoute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 路线表操作.
 * User: huang
 * Date: 17-7-22
 */
public interface BookRouteRepository extends JpaRepository<BookRoute, Integer> {
}
