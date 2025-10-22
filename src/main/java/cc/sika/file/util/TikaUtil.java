package cc.sika.file.util;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
public class TikaUtil {

    private final Tika tika;

    private static final MimeTypes MIME_TYPES = MimeTypes.getDefaultMimeTypes();

    /**
     * 根据文件内容获取 MIME 类型
     * @param stream 文件输入流
     * @return MIME类型
     * @throws IOException -
     */
    public String detectMimeType(InputStream stream) throws IOException {
        return tika.detect(stream);
    }

    /**
     * 根据文件内容获取 MIME 类型
     * @param bytes 文件输入流
     * @return MIME类型
     */
    public String detectMimeType(byte[] bytes) {
        return tika.detect(bytes);
    }

    /**
     * 根据文件路径获取 MIME 类型
     * @param filePath nio的Path对象
     * @return MIME类型
     * @throws IOException -
     */
    public String detectMimeType(Path filePath) throws IOException {
        return tika.detect(filePath);
    }

    /**
     * 根据文件路径获取 MIME 类型
     * @param file file对象
     * @return MIME类型
     * @throws IOException -
     */
    public String detectMimeType(File file) throws IOException {
        return tika.detect(file);
    }

    /**
     * 根据 MIME 类型获取文件扩展名（带点号）
     */
    public String getExtensionFromMime(String mimeType) {
        try {
            MimeType type = MIME_TYPES.forName(mimeType);
            return type.getExtension(); // 如: ".png"
        }
        catch (MimeTypeException e) {
            return ""; // 未知类型返回空字符串
        }
    }


    public TikaUtil(Tika tika) {
        this.tika = tika;
    }
}
