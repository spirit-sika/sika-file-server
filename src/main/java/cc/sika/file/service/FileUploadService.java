package cc.sika.file.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

/**
 * 文件上传功能相关服务
 *
 * @author 小吴来哩
 * @since 2025-08
 */
public interface FileUploadService {

    /**
     * 异步上传文件
     *
     * @param file 文件
     * @return oss访问路径
     */
    String uploadAsync(MultipartFile file);

    /**
     * 同步上传文件
     *
     * @param file 文件
     * @return oss访问路径
     */
    String uploadSync(MultipartFile file);

    /**
     * 上传文件同时回传上传进度
     *
     * @param file 文件
     * @return 进度流
     */
    Flux<Integer> uploadAndGet(MultipartFile file);

}
