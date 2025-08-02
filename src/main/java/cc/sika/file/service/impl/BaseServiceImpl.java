package cc.sika.file.service.impl;

import cc.sika.file.entity.po.BaseEntity;
import cc.sika.file.exception.BeanTableException;
import cc.sika.file.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<E>, E extends BaseEntity> extends ServiceImpl<M, E> implements BaseService<E> {

    @Override
    public QueryWrapper<E> dynamicWrapper(E qo) {
        return dynamicWrapper(qo, null);
    }

    @Override
    public QueryWrapper<E> dynamicWrapper(E qo, QueryWrapper<E> additionalWrapper) {
        if (Objects.isNull(additionalWrapper)) {
            additionalWrapper = new QueryWrapper<>();
        }
        if (Objects.isNull(qo) || BeanUtil.isEmpty(qo)) {
            return additionalWrapper;
        }

        // 获取实体信息
        Class<?> clazz = ReflectionKit.getSuperClassGenericType(getClass(), BaseEntity.class, 0);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        /* 将属性名与表字段名映射为map提高查找效率 key: 类属性名, value: 字段名 */
        Map<String, String> fieldMapToCol = tableInfo
                .getFieldList()
                .stream()
                .collect(Collectors
                        .toMap(TableFieldInfo::getProperty, TableFieldInfo::getColumn));

        try {
            /* 读取bean信息与get方法 */
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()) {
                String fieldName = propertyDescriptor.getName();
                Object getResult = propertyDescriptor.getReadMethod().invoke(qo);
                /* 存在值且值有效再拼接到wrapper */
                if (fieldMapToCol.containsKey(fieldName) && isValidValue(getResult)) {
                    additionalWrapper.eq(fieldMapToCol.get(fieldName), getResult);
                }
            }
        } catch (IntrospectionException introspectionException) {
            log.error("尝试内省类型为:[{}]的bean失败", clazz.getSimpleName());
            throw new BeanTableException(introspectionException.getLocalizedMessage(), clazz);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new BeanTableException(e.getLocalizedMessage(), clazz);
        }

        return additionalWrapper;
    }

    protected boolean isValidValue(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (obj instanceof String string) {
            return CharSequenceUtil.isBlank(string);
        }
        return true;
    }
}
