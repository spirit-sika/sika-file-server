package cc.sika.file.controller;

import cc.sika.file.entity.vo.R;
import cc.sika.file.service.SikaFileMetaService;
import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

/**
 * 文件相关接口控制器
 * <p>
 * 处理文件目录树, 文件上传, 下载, 修改, 预览相关相关功能
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@RestController
@RequestMapping("file")
@Tag(name = "文件管理", description = "文件目录树, 文件上传, 下载, 修改, 预览相关相关接口")
@Slf4j
@SaCheckLogin
public class FileController {

    @Resource
    private SikaFileMetaService fileService;

    @PostMapping("async")
    public R<String> uploadAsync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadAsync(file));
    }

    @PostMapping("sync")
    public R<String> uploadSync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadSync(file));
    }

    @PostMapping(value = "sync-progress", produces = "text/event-stream")
    public Flux<R<Integer>> uploadProgress(@RequestParam MultipartFile file) {
        return fileService.uploadAndGet(file).map(R::success);
    }

}
