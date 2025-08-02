package cc.sika.file.service;

import cc.sika.file.entity.po.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
public interface BaseService<E extends BaseEntity> extends IService<E> {

    /**
     * 根据实体类的查询条件动态生成wrapper
     * @param qo 要包含查询条件则为entity带上相应属性的值会自动转为查询条件, 默认都为eq
     * @return 合并entity的查询条件
     */
    QueryWrapper<E> dynamicWrapper(E qo);

    /**
     * 根据实体类的查询条件动态生成wrapper
     * @param qo 要包含查询条件则为entity带上相应属性的值会自动转为查询条件, 默认都为eq
     * @param additionalWrapper 手动处理的wrapper可以追加到生成的wrapper对象中
     * @return 合并entity的查询条件和additionalWrapper手动指定的查询条件
     */
    QueryWrapper<E> dynamicWrapper(E qo, QueryWrapper<E> additionalWrapper);
}
