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
    public String store(MultipartFile file, String path) {

        path = rootLacation + path;
        Path location = Paths.get(path);
        if (Files.notExists(location)) {
            try {
                Files.createDirectories(location);
            } catch (IOException e) {
                logger.debug("文件目录创建失败");
            }
        }
        // 保存文件
        try {
            Files.deleteIfExists(location);
            Files.copy(file.getInputStream(), location);
            return location.toString();
        } catch (IOException e) {
            logger.debug("文件保存失败", e);
            return null;
        }
    }
}
