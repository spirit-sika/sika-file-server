package cc.sika.file.handler;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * lettuce心跳, 定时1分钟ping一下redis, 避免lettuce断开连接
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
@Slf4j
@EnableScheduling
public class LettuceIdle {

    private static final int IDLE = 60000;

    @Resource
    private StringRedisTemplate dupShowMasterRedisTemplate;

    // 1 minutes
    @Scheduled(fixedRate = IDLE)
    public void configureTasks() {
        log.info("lettuce idle...");
        dupShowMasterRedisTemplate.execute(RedisConnectionCommands::ping);
    }
}
