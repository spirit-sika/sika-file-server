package cc.sika.file.service;

import cc.sika.file.entity.po.SikaFileMeta;
import cn.hutool.core.util.IdUtil;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件元数据表操作服务
 *
 * @author 小吴来哩
 * @since 2025-08
 */
public interface SikaFileMetaService extends BaseService<SikaFileMeta>, FileUploadService {

    String ROOT = null;

    String FILE_NAME_SEPARATOR = "________";

    /**
     * 触发文件拷贝为临时文件的阈值,避免内存溢出或占用过高
     * <p>
     * 文件小于此大小, 转为bytes数组处理,
     * 超过此大小将复制为临时文件再处理
     * <p>
     * 20MB
     */
    long COPY_TO_TEMP_FILE_THRESHOLD = 10 * 1024 * 1024L;

    default String buildUuidFileName(String originalFileName) {
        return IdUtil.fastSimpleUUID() + FILE_NAME_SEPARATOR + originalFileName;
    }

    default void notEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空!");
        }
    }

    default boolean isLarge(MultipartFile file) {
        notEmpty(file);
        return file.getSize() > COPY_TO_TEMP_FILE_THRESHOLD;
    }

    /**
     * 列出id下的文件夹和文件
     *
     * @param id 文件夹id, 为空时列出根目录下的文件
     * @return -
     */
    List<SikaFileMeta> listFileInLayer(@Nullable String id);

    /**
     * 创建文件夹
     *
     * @param dirName  目录名称
     * @param parentId 上级目录id
     */
    void mkDir(String dirName, String parentId);

    /**
     * 修改文件夹名称
     *
     * @param dirId   文件夹id
     * @param newName 新名称
     */
    void renameDir(String dirId, String newName);

    /**
     * 删除文件或文件夹
     *
     * @param id 文件id, 可以是文件夹id, 如果是文件夹id会递归的进行删除
     */
    void rm(String id);

    /**
     * 批量删除文件或文件夹
     * @param ids id列表
     */
    void removeBatch(List<String> ids);

}
