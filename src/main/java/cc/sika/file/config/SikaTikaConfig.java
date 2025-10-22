package cc.sika.file.config;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Configuration
public class SikaTikaConfig {

    private final ResourceLoader resourceLoader;

    @Bean
    public Tika tika() throws IOException, TikaException, SAXException {
        Resource resource = resourceLoader.getResource("classpath:tika-config.xml");
        InputStream inputStream = resource.getInputStream();
        TikaConfig tikaConfig = new TikaConfig(inputStream);
        // 校验器
        Detector detector = tikaConfig.getDetector();
        // 解析器
        AutoDetectParser autoDetectParser = new AutoDetectParser(tikaConfig);

        return new Tika(detector, autoDetectParser);
    }

    @Autowired
    public SikaTikaConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
