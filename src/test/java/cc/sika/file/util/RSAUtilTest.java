package cc.sika.file.util;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@SpringBootTest
class RSAUtilTest {

    @Resource
    private RSAUtil rsaUtil;

    @Test
    void testEncryptAndDecrypt() {
        String testStr = "sika";
        String encrypted = rsaUtil.encrypt(testStr);
        String decrypted = rsaUtil.decrypt(encrypted);
        Assertions.assertEquals(testStr, decrypted);
    }
}
