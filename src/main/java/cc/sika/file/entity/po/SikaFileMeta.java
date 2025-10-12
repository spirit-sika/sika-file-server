package cc.sika.file.entity.po;

import cc.sika.file.consts.FileConsts;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

/**
 * 文件元数据
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@TableName("SIKA_FILE_META")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SikaFileMeta extends BaseEntityInfo {

    /**
     * id
     */
    @TableId(value = "id")
    private String id;

    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 元类型, 1-文件夹, 2-文件
     * @see FileConsts.MetaType 元类型
     */
    @TableField(value = "meta_type")
    private Integer metaType;

    /**
     * 原始文件名
     */
    @TableField("ORIGINAL_NAME")
    private String originalName;

    /**
     * 文件mime类型
     */
    @TableField("file_mime")
    private String fileMime;

    /**
     * 文件扩展名带.
     */
    @TableField("file_extension")
    private String fileExtension;

    /**
     * 文件大小字节数
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * S3/OSS存储路径(加密)
     */
    @TableField("STORAGE_PATH")
    private String storagePath;

    /**
     * 文件预览ISS存储路径
     */
    @TableField("PREVIEW_PATH")
    private String previewPath;

    /**
     * 文件系统中的路径, 使用/分隔
     */
    @TableField("ABSOLUTE_PATH")
    private String absolutePath;

    /**
     * 文件系统中的id路径, 使用/分隔
     */
    @TableField("ABSOLUTE_ID_PATH")
    private String absoluteIdPath;

    /**
     * 文件哈希(校验完整性)
     */
    @TableField("sha256")
    private String sha256;

    /**
     * 目标区域(US/CN/ALL)
     */
    @TableField("REGION_TARGET")
    private String regionTarget;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}
