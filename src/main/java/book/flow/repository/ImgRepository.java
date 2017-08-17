package book.flow.repository;

import book.flow.enity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 申请图片表操作.
 * User: huang
 * Date: 17-8-6
 */
public interface ImgRepository extends JpaRepository<Img, Integer> {

    @Query("select i from Img i where i.apply.book.bookId = ?1")
    Set<Img> getImgByBook(int bookId);
}
