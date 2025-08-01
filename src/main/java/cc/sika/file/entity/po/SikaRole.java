package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 */
@TableName(value ="SIKA_ROLE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SikaRole implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 角色名称
     */
    @TableField(value = "ROLE_NAME")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField(value = "ROLE_DESC")
    private String roleDesc;

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