package cc.sika.file.controller;

import cc.sika.file.entity.dto.BasePage;
import cc.sika.file.entity.po.BaseEntity;
import cc.sika.file.entity.vo.R;
import cc.sika.file.service.BaseService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 小吴来哩
 * @since 2025-08
 * @param <S> 服务层接口, 必须为mp的IService实现类
 * @param <E> 实体, 与MP实体要求一直并且需要额外实现BaseEntity
 * @param <P> 分页实体对象, 大多数情况下为DTO
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public abstract class BaseController<S extends BaseService<E>, E extends BaseEntity, P extends BasePage<E>> {

    @Autowired
    protected S service;

    @GetMapping
    public R<E> getT(@RequestParam Long id) {
        return R.success(service.getById(id));
    }

    @GetMapping("list")
    public R<List<E>> list() {
        return R.success(service.list());
    }

    @GetMapping("page")
    public R<IPage<E>> getPage(@ModelAttribute P p) {
        long size = p.getSize();
        long current = p.getCurrent();
        E condition = p.getCondition();

        Page<E> pageCondition = new Page<>(current, size);

        // 未带查询限定条件, 直接分页
        if (BeanUtil.isEmpty(condition)) {
            return R.success(service.page(pageCondition));
        }

        QueryWrapper<E> conditionWrapper = service.dynamicWrapper(condition);
        return  R.success(service.page(pageCondition, conditionWrapper));
    }


}
