package book.flow.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作接口.
 * User: huang
 * Date: 17-8-3
 */
public interface FileService {

    /**
     * 保存.
     * @param file 文件
     * @param path 文件保存路径
     * @param bookId 图书编号
     */
    void store(MultipartFile file, String path, int bookId);
}
