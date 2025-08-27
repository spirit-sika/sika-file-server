package cc.sika.file.service.impl.file;

import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.mapper.SikaFileMetaMapper;
import cc.sika.file.service.SikaFileMetaService;
import cc.sika.file.service.impl.BaseServiceImpl;
import com.qiniu.storage.UploadManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

/**
 * 七牛云文件上传服务实现类
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Service
@ConditionalOnBean(UploadManager.class)
public class QiniuFileMetaServiceImpl extends BaseServiceImpl<SikaFileMetaMapper, SikaFileMeta>
        implements SikaFileMetaService {

    @Override
    public String uploadAsync(MultipartFile file) {
        return "";
    }

    @Override
    public String uploadSync(MultipartFile file) {
        return "";
    }

    @Override
    public Flux<Integer> uploadAndGet(MultipartFile file) {
        return null;
    }
}
