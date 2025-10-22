package cc.sika.file.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
@ConfigurationProperties(prefix = "sika.thread-pool")
@Getter
@Setter
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int coreSize = 10;

    /**
     * 最大线程数
     */
    private int maxSize = 50;

    /**
     * 队列容量
     */
    private int queueCapacity = 200;

    /**
     * 非核心线程存活时长, 单位: 秒
     */
    private int keepAliveSeconds = 60;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "sika-pool-";

}
