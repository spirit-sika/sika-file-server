package cc.sika.file.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 异步文件处理时需要先缓存一份本地文件, 避免Spring的MultipartFile因为切换线程被释放, 该类可以在程序启动时确保临时文件存储路径存在
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Getter
@Configuration
@Slf4j
public class FileStorageConfig {

    @Value("${sika.upload.temp-path:/tmp/sika/uploads}") // 默认给一个路径
    private String tempPath;

    @PostConstruct
    public void init() {
        File dir = new File(tempPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("临时文件目录 [{}] 创建成功", tempPath);
            } else {
                log.warn("临时文件目录 [{}] 创建失败，可能没有权限", tempPath);
            }
        } else {
            log.info("临时文件目录 [{}] 已存在", tempPath);
        }
    }

}
