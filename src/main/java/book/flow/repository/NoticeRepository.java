package book.flow.repository;

import book.flow.enity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 公告表操作.
 * User: huang
 * Date: 17-7-22
 */
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    /**
     * 获取所有的公告.
     * @param pageable 分页设置
     * @return 公告信息
     */
    @Query("select n from Notice n order by n.noticeDate desc ")
    Page<Notice> getNotice(Pageable pageable);
}
