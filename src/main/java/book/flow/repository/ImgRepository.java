package book.flow.repository;

import book.flow.enity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 申请图片表操作.
 * User: huang
 * Date: 17-8-6
 */
public interface ImgRepository extends JpaRepository<Img, Integer> {
}
