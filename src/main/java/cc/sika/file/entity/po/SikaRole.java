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
 * 角色表
 */
@TableName(value ="sika_role")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class SikaRole extends BaseEntityInfo implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField(value = "role_desc")
    private String roleDesc;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    public SikaRole(LocalDateTime createTime, String createBy, Long createId, LocalDateTime updateTime, String updateBy, Long updateId, Long id, String roleName, String roleDesc) {
        super(createTime, createBy, createId, updateTime, updateBy, updateId);
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }
}