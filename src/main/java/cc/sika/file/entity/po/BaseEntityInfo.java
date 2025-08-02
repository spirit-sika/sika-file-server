package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityInfo implements BaseEntity {

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
}
