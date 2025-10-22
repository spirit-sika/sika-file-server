package cc.sika.file.controller;

import cc.sika.file.entity.dto.FileMetaDto;
import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.entity.vo.R;
import cc.sika.file.service.SikaFileMetaService;
import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "异步上传文件到根目录")
    @Parameters(value = {@Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT)})
    @ApiResponse(description = "文件OSS访问url")
    @PostMapping("async-root")
    public R<String> uploadAsync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadAsync(file));
    }

    @Operation(summary = "同步上传文件到根目录")
    @Parameters(value = {@Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT)})
    @ApiResponse(description = "文件OSS访问url")
    @PostMapping("sync-root")
    public R<String> uploadSync(@RequestParam MultipartFile file) {
        return R.success(fileService.uploadSync(file));
    }

    @Operation(summary = "同步上传文件到根目录")
    @Parameters(value = {@Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT)})
    @ApiResponse(description = "sse模式响应文件上传进度")
    @PostMapping(value = "sync-progress-root", produces = "text/event-stream")
    public Flux<R<Integer>> uploadProgress(@RequestParam MultipartFile file) {
        return fileService.uploadAndGet(file).map(R::success);
    }

    @Operation(summary = "异步上传文件到指定目录")
    @Parameters(value = {
            @Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT),
            @Parameter(name = "parentId", description = "目录Id, 需要保证id为目录id不是文件id", in = ParameterIn.QUERY),
    })
    @ApiResponse(description = "立即放回文件访问url, 但不代表上传完成")
    @PostMapping("async")
    public R<String> uploadAsync(@RequestParam MultipartFile file, String parentId) {
        return R.success(fileService.uploadAsync(file, parentId));
    }

    @Operation(summary = "同步上传文件到指定目录")
    @Parameters(value = {
            @Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT),
            @Parameter(name = "parentId", description = "目录Id, 需要保证id为目录id不是文件id", in = ParameterIn.QUERY),
    })
    @ApiResponse(description = "上传完成文件访问url")
    @PostMapping("sync")
    public R<String> uploadSync(@RequestParam MultipartFile file, String parentId) {
        return R.success(fileService.uploadSync(file, parentId));
    }

    @Operation(summary = "同步上传文件到指定目录")
    @Parameters(value = {
            @Parameter(name = "file", description = "文件对象", in = ParameterIn.DEFAULT),
            @Parameter(name = "parentId", description = "目录Id, 需要保证id为目录id不是文件id", in = ParameterIn.QUERY),
    })
    @ApiResponse(description = "sse模式响应文件上传进度")
    @PostMapping(value = "sync-progress", produces = "text/event-stream")
    public Flux<R<Integer>> uploadProgress(@RequestParam MultipartFile file, String parentId) {
        return fileService.uploadAndGet(file, parentId).map(R::success);
    }

    @Operation(summary = "获取指定目录下的所有文件以及子文件夹")
    @Parameters(value = {@Parameter(name = "dirId", description = "目录id, 为空时获取根目录的内容", in = ParameterIn.QUERY)})
    @ApiResponse(description = "文件以及文件夹信息列表")
    @GetMapping
    public R<List<SikaFileMeta>> dir(@RequestParam(required = false) String dirId) {
        return R.success(fileService.listFileInLayer(dirId));
    }

    @Operation(summary = "分页获取指定目录下的所有文件以及子文件夹")
    @ApiResponse(description = "文件以及文件夹信息列表")
    @GetMapping("page")
    public R<IPage<SikaFileMeta>> page(@ModelAttribute FileMetaDto fileMetaDto) {
        return R.success(fileService.pageFileInLayer(fileMetaDto));
    }

    @Operation(summary = "获取文件信息")
    @Parameters(value = {@Parameter(name = "id", description = "获取文件信息", in = ParameterIn.QUERY)})
    @GetMapping("info")
    public R<SikaFileMeta> metaInfo(@RequestParam(value = "id") String id) {
        return R.success(fileService.getMetaInfo(id));
    }

    @PostMapping("mkdir")
    public R<Void> mkdir(@RequestParam String name, @RequestParam(required = false) String parentId) {
        fileService.mkDir(name, parentId);
        return R.success(null);
    }

    @DeleteMapping("rm")
    public R<Void> rm(@RequestParam String id) {
        fileService.rm(id);
        return R.success(null);
    }

}
