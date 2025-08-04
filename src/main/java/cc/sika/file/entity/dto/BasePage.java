package cc.sika.file.entity.dto;

import java.io.Serializable;

/**
 * 提供通用分页查询能力
 * @author 小吴来哩
 * @since 2025-08
 */
public interface BasePage<T> extends BaseQuery<T>, Serializable {

    /**
     * 获取当前页码
     * @return -
     */
    long getCurrent();

    /**
     * 获取页面大小
     * @return -
     */
    long getSize();

}
