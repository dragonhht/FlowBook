package book.flow.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作接口.
 * User: huang
 * Date: 17-8-3
 */
public interface FileService {

    /**
     * 文件根目录.
     */
    String rootLacation = "file-dir/files/";
    /** 访问根目录. */
    String urlRoot = "files/";

    /**
     * 保存.
     * @param file 文件
     * @param path 文件保存路径
     * @return 文件路径
     */
    String store(MultipartFile file, String path);
}
