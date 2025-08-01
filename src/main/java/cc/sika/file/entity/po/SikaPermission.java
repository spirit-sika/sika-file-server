package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表
 */
@TableName(value ="SIKA_PERMISSION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SikaPermission implements Serializable {
    /**
     * id
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 权限字符串
     */
    @TableField(value = "PERMISSION_CONTENT")
    private String permissionContent;

    /**
     * 权限描述
     */
    @TableField(value = "PERMISSION_DESC")
    private String permissionDesc;

    /**
     * 权限类型
     */
    @TableField(value = "PERMISSION_TYPE")
    private Integer permissionType;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 创建人用户名
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 创建人id
     */
    @TableField(value = "CREATE_ID")
    private Long createId;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 更新人用户名
     */
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 更新人id
     */
    @TableField(value = "UPDATE_ID")
    private Long updateId;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

}