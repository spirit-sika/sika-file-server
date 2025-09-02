package cc.sika.file.service.impl.file;

import com.qiniu.storage.UploadManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 七牛云文件上传服务实现类
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Service
@ConditionalOnBean(UploadManager.class)
@Primary
public class QiniuFileMetaServiceImpl extends FileServiceAdapter {
}
