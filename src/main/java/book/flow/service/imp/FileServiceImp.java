package book.flow.service.imp;

import book.flow.BookFlowApplication;
import book.flow.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作实现类.
 * User: huang
 * Date: 17-8-3
 */
@Service
public class FileServiceImp implements FileService {

    private final static Logger logger = LoggerFactory.getLogger(BookFlowApplication.class);

    @Override
    public void store(MultipartFile file, String path, int bookId) {

        path =  "file-dir/" + path;
        Path location = Paths.get(path);
        // 保存文件
        try {
            Files.copy(file.getInputStream(), location.resolve(bookId + ".png"));
            System.out.println(location.relativize(location));
        } catch (IOException e) {
            logger.info("图片保存失败");
        }
    }
}
