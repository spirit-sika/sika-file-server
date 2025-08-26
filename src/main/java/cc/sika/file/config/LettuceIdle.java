package cc.sika.file.config;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * lettuce心跳, 定时1分钟ping一下redis, 避免lettuce断开连接
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
public class LettuceIdle {

    private static final int IDLE = 60000;

    @Resource
    private StringRedisTemplate dupShowMasterRedisTemplate;

    // 1 minutes
    @Scheduled(fixedRate = IDLE)
    private void configureTasks() {
        dupShowMasterRedisTemplate.execute(RedisConnectionCommands::ping);
    }
}
