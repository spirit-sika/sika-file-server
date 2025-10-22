package cc.sika.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "sikaThreadPool")
    public ThreadPoolTaskExecutor sikaThreadPool(ThreadPoolProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCoreSize());
        executor.setMaxPoolSize(properties.getMaxSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setWaitForTasksToCompleteOnShutdown(true); // 优雅停机
        executor.setAwaitTerminationSeconds(30); // 最多等30秒
        return executor;
    }
}
