package cc.sika.file.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final ThreadPoolTaskExecutor sikaThreadPool;


    public WebConfig(@Qualifier("sikaThreadPool") ThreadPoolTaskExecutor sikaThreadPool) {
        this.sikaThreadPool = sikaThreadPool;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(sikaThreadPool);
        configurer.setDefaultTimeout(30000); // 可以根据业务需要调整超时时间
    }
}