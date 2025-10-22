package cc.sika.file.config;

import cc.sika.file.util.AliOssUtil;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.qiniu.storage.Configuration.create;

/**
 * Oss相关配置
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Configuration
@ConfigurationProperties("sika.upload")
@Getter
@Setter
@Slf4j
public class OssConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endPoint;
    private OssType ossType;

    /*********************************七牛云*********************************/
    @Bean
    @ConditionalOnProperty(name = "sika.upload.oss-type", havingValue = "qi_niu")
    public Auth auth() {
        log.info("启用七牛云oss");
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    @ConditionalOnProperty(name = "sika.upload.oss-type", havingValue = "qi_niu")
    public UploadManager getUploadManager(){
        return new UploadManager(create());
    }

    /*********************************阿里云*********************************/
    @Bean
    @ConditionalOnProperty(name = "sika.upload.oss-type", havingValue = "a_li")
    public AliOssUtil aliOssUtil() {
        log.info("启用阿里oss");
        return new AliOssUtil(endPoint, accessKey, secretKey, bucket);
    }


    enum OssType {
        A_LI,
        QI_NIU,
        MINIO
    }
}
