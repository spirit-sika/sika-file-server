package cc.sika.file.handler;

import cc.sika.file.entity.po.SikaUser;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        if (!metaObject.getOriginalObject().getClass().equals(SikaUser.class)) {
            Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
            strictInsertFill(metaObject, "createBy", String.class, "");
            strictInsertFill(metaObject, "createId", Long.class, loginId);
        }
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
        strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "updateBy", String.class, "");
        strictInsertFill(metaObject, "updateId", Long.class, loginId);
    }
}
