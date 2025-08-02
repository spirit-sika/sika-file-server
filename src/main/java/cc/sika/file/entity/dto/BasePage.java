package cc.sika.file.entity.dto;

import java.io.Serializable;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
public interface BasePage<T> extends BaseQuery, Serializable {

    long getCurrent();

    long getSize();

    T getCondition();
}
