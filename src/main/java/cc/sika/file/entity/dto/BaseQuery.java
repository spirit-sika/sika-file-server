package cc.sika.file.entity.dto;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
public interface BaseQuery<T> {

    /**
     * 获取实体查询条件
     * @return -
     */
    T getCondition();
}
