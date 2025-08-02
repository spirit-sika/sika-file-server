package cc.sika.file.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@SuppressWarnings("unused")
public interface BaseEntity extends Serializable {

    Serializable getId();

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    String getCreateBy();

    void setCreateBy(String createBy);

    Long getCreateId();

    void setCreateId(Long createId);

    LocalDateTime getUpdateTime();

    void setUpdateTime(LocalDateTime updateTime);

    String getUpdateBy();

    void setUpdateBy(String updateBy);

    Long getUpdateId();

    void setUpdateId(Long updateId);
}
