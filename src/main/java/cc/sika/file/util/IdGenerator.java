package cc.sika.file.util;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
@SuppressWarnings("unused")
public class IdGenerator {

    private final Snowflake snowflake;

    public Long newId() {
        return snowflake.nextId();
    }

    public String newStrId() {
        return snowflake.nextIdStr();
    }

    private IdGenerator() {
        snowflake = new Snowflake(1, 2);
    }
}
