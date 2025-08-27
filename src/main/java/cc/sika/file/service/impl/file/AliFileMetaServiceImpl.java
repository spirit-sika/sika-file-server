package cc.sika.file.service.impl.file;

import cc.sika.file.config.FileStorageConfig;
import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.exception.BaseRuntimeException;
import cc.sika.file.mapper.SikaFileMetaMapper;
import cc.sika.file.service.SikaFileMetaService;
import cc.sika.file.service.impl.BaseServiceImpl;
import cc.sika.file.util.AliOssUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

/**
 * 文件上传服务阿里云OSS实现
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Service
@ConditionalOnBean(AliOssUtil.class)
@Slf4j
public class AliFileMetaServiceImpl extends BaseServiceImpl<SikaFileMetaMapper, SikaFileMeta>
        implements SikaFileMetaService {

    @Resource
    private AliOssUtil aliOssUtil;
    private String bucket;

    @Resource
    private ThreadPoolTaskExecutor sikaThreadPool;
    @Resource
    private FileStorageConfig fileStorageConfig;

    @PostConstruct
    public void init(){
        this.bucket = aliOssUtil.getBucketName();
    }


    @Override
    public String uploadAsync(MultipartFile file) {
        String uploadName = buildUuidFileName(file.getOriginalFilename());

        // 将文件落盘到临时目录
        File temp = new File(fileStorageConfig.getTempPath(), uploadName);
        try {
            file.transferTo(temp);
        }
        catch (IOException e) {
            log.error("生成临时文件失败!", e);
            throw new BaseRuntimeException("上传失败");
        }

        String url = spliceOssUrl(uploadName);

        // 提交线程池处理
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try(InputStream inputStream = Files.newInputStream(temp.toPath())) {
                return uploadAsyncInternal(uploadName, inputStream);
            }
            catch (IOException e) {
                log.error("上传失败!", e);
                throw new BaseRuntimeException("上传失败!");
            }
            finally {
                try {
                    Files.delete(temp.toPath());
                }
                catch (IOException e) {
                    log.warn("临时文件清除失败!, {}", e.getMessage());
                }
            }
        }, sikaThreadPool);

        future.whenComplete((result, ex) -> {
            if (ObjectUtil.isNull(ex)) {
                log.info("上传成功, 访问url为: {}", result);
                return;
            }
            log.error("上传失败!", ex);
        });
        return url;
    }

    public String uploadAsyncInternal(String fileName, InputStream inputStream) {
        OSS ossClient = aliOssUtil.buildOssClient();
        try {
            PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream);
            ossClient.putObject(request);
            return spliceOssUrl(fileName);
        }
        finally {
            ossClient.shutdown();
        }
    }

    @Override
    public String uploadSync(MultipartFile file) {
        /* 建立阿里OSS连接客户端和数据推送请求 */
        OSS ossClient = aliOssUtil.buildOssClient();
        // 构建文件名, 使用 UUID 前缀去重
        String filename = buildUuidFileName(file.getOriginalFilename());
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        }
        catch (IOException e) {
            log.error("获取文件输入流失败!", e);
            throw new BaseRuntimeException("获取文件输入流失败!");
        }

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filename, inputStream);

        /* 推送数据, 会阻塞, 此方法过后可以直接关闭客户端 */
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();

        // 构建 OSS 文件访问 URL
        return spliceOssUrl(filename);
    }

    @Override
    public Flux<Integer> uploadAndGet(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return Flux.empty();
        }

        // 构建文件名, 使用 UUID 前缀去重
        final String filename = buildUuidFileName(file.getOriginalFilename());

        return Flux.create(sink -> {

            final ProgressTracker tracker = new ProgressTracker(sink, file.getSize());

            Schedulers.boundedElastic().schedule(() -> {

                OSS ossClient = aliOssUtil.buildOssClient();

                InputStream inputStream;
                try {
                    inputStream = file.getInputStream();
                } catch (IOException e) {
                    log.error("获取输入流失败!", e);
                    ossClient.shutdown();
                    throw new BaseRuntimeException("获取输入流失败!");
                }

                try {
                    PutObjectRequest request = new PutObjectRequest(bucket, filename, inputStream);
                    request.setProgressListener(tracker);
                    ossClient.putObject(request);
                }
                catch (Exception e) {
                    log.error("文件上传失败", e);
                    sink.error(e);
                    throw new BaseRuntimeException(e.getMessage());
                }
                finally {
                    ossClient.shutdown();
                }

            });
        }, FluxSink.OverflowStrategy.LATEST);
    }

    /**
     * 生成可以直接访问Oss的文件路径
     *
     * @param filename 上传的文件名
     * @return 可以直接访问Oss的文件路径
     */
    private String spliceOssUrl(String filename) {
        return "https://" + bucket +
                "." +
                aliOssUtil.getEndpoint() +
                "/" +
                filename;
    }
}
