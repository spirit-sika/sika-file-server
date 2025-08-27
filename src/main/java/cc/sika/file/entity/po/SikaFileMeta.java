package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;

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
@SuppressWarnings("all")
public class SikaFileMeta extends BaseEntityInfo {

    /**
     * id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 原始文件名
     */
    @TableField("ORIGINAL_NAME")
    private String originalName;

    /**
     * 原文件携带的创建时间
     */
    @TableField("ORIGINAL_CREATE_TIME")
    private LocalDateTime originalCreateTime;

    /**
     * 原文件携带的最后修改时间
     */
    @TableField("ORIGINAL_UPDATE_TIME")
    private LocalDateTime originalUpdateTime;

    /**
     * 文件类型, 不带.的后缀
     */
    @TableField("FILE_TYPE")
    private String fileType;

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
     * 文件系统中的路径
     */
    @TableField("ABSOLUTE_PATH")
    private String absolutePath;

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
