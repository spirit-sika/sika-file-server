package cc.sika.file.entity.dto;

import cc.sika.file.entity.po.BaseEntity;
import cn.hutool.core.util.ObjectUtil;
import lombok.*;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
public class BasePageAdapter<T extends BaseEntity> implements BasePage<T> {
    private Long current;
    private Long size;
    private T condition;


    @Override
    public long getCurrent() {
        if (ObjectUtil.isNull(current) || current <= 1) {
            return 1;
        }
        return current;
    }

    @Override
    public long getSize() {
        if (ObjectUtil.isNull(size) || size <= 1) {
            return 1;
        }
        return size;
    }

    @Override
    public T getCondition() {
        return condition;
    }
}
