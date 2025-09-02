package cc.sika.file.controller;

import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.entity.vo.R;
import cc.sika.file.service.SikaFileMetaService;
import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

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

    @PostMapping("async-root")
    public R<String> uploadAsync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadAsync(file));
    }

    @PostMapping("sync-root")
    public R<String> uploadSync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadSync(file));
    }

    @PostMapping(value = "sync-progress-root", produces = "text/event-stream")
    public Flux<R<Integer>> uploadProgress(@RequestParam MultipartFile file) {
        return fileService.uploadAndGet(file).map(R::success);
    }

    @PostMapping("async")
    public R<String> uploadAsync(@RequestParam MultipartFile file, String parentId) {
        return R.success(fileService.uploadAsync(file, parentId));
    }

    @PostMapping("sync")
    public R<String> uploadSync(@RequestParam MultipartFile file, String parentId) {
        return R.success(fileService.uploadSync(file, parentId));
    }

    @PostMapping(value = "sync-progress", produces = "text/event-stream")
    public Flux<R<Integer>> uploadProgress(@RequestParam MultipartFile file, String parentId) {
        return fileService.uploadAndGet(file, parentId).map(R::success);
    }

    @GetMapping
    public R<List<SikaFileMeta>> dir(@RequestParam(required = false) String dirId) {
        return R.success(fileService.listFileInLayer(dirId));
    }

    @PostMapping("mkdir")
    public R<Void> mkdir(@RequestParam String name, @RequestParam(required = false) String parentId) {
        fileService.mkDir(name, parentId);
        return R.success(null);
    }

}
