package cc.sika.file.handler;

import cc.sika.file.entity.po.SikaUser;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static cc.sika.file.consts.AuthConsts.USER_INFO_KEY;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
@Slf4j
public class BaseInfoMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        // 注册用户只处理创建时间
        if (metaObject.getOriginalObject().getClass().equals(SikaUser.class)) {
            strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
            return;
        }

        Object extra = StpUtil.getExtra(USER_INFO_KEY);

        if (ObjectUtil.isNotNull(extra) && BeanUtil.isNotEmpty(extra)) {
            SikaUser user = BeanUtil.toBean(extra, SikaUser.class);
            strictInsertFill(metaObject, "createBy", String.class, user.getUsername());
            strictInsertFill(metaObject, "createId", Long.class, user.getId());
        }
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Object extra = StpUtil.getExtra(USER_INFO_KEY);
        if (ObjectUtil.isNotNull(extra) && BeanUtil.isNotEmpty(extra)) {
            SikaUser user = BeanUtil.toBean(extra, SikaUser.class);
            strictInsertFill(metaObject, "updateBy", String.class, user.getUsername());
            strictInsertFill(metaObject, "updateId", Long.class, user.getId());
        }
        strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
