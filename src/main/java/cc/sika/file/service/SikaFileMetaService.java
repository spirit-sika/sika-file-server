package cc.sika.file.service;

import cc.sika.file.entity.po.SikaFileMeta;
import cn.hutool.core.util.IdUtil;

/**
 * 文件元数据表操作服务
 *
 * @author 小吴来哩
 * @since 2025-08
 */
public interface SikaFileMetaService extends BaseService<SikaFileMeta>, FileUploadService {

    String FILE_NAME_SEPARATOR = "________";

    default String buildUuidFileName(String originalFileName) {
        return IdUtil.fastSimpleUUID() + FILE_NAME_SEPARATOR + originalFileName;
    }

}
