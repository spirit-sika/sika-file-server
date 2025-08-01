package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表
 */
@TableName("SIKA_PERMISSION")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class SikaPermission extends BaseEntityInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 权限字符串
     */
    @TableField(value = "permission_content")
    private String permissionContent;

    /**
     * 权限描述
     */
    @TableField(value = "permission_desc")
    private String permissionDesc;

    /**
     * 权限类型
     */
    @TableField(value = "permission_type")
    private Integer permissionType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人用户名
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    private Long createId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新人用户名
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新人id
     */
    @TableField(value = "update_id")
    private Long updateId;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    public SikaPermission(LocalDateTime createTime, String createBy, Long createId, LocalDateTime updateTime, String updateBy, Long updateId, Long id, String permissionContent, String permissionDesc, Integer permissionType, LocalDateTime createTime1, String createBy1, Long createId1, LocalDateTime updateTime1, String updateBy1, Long updateId1) {
        super(createTime, createBy, createId, updateTime, updateBy, updateId);
        this.id = id;
        this.permissionContent = permissionContent;
        this.permissionDesc = permissionDesc;
        this.permissionType = permissionType;
        this.createTime = createTime1;
        this.createBy = createBy1;
        this.createId = createId1;
        this.updateTime = updateTime1;
        this.updateBy = updateBy1;
        this.updateId = updateId1;
    }
}