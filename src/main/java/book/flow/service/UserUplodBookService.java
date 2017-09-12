package book.flow.service;

import book.flow.enity.Book;

/**
 * 用户新书漂流服务接口.
 * User: huang
 * Date: 17-9-12
 */
public interface UserUplodBookService {

    /**
     * 上传图书.
     * @param book 图书信息
     * @param userId 用户编号
     * @return 保存后的图书信息
     */
    Book uploadBook(Book book, int userId);

    /**
     * 更新图书封面.
     * @param imgPath 封面路径
     * @param bookId 图书编号
     */
    void updateBookImg(String imgPath, int bookId);
}
