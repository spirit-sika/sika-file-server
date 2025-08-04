package cc.sika.file.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * 公/私钥获取与加解密单例
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
public final class RSAUtil {
    private final RSA rsa = new RSA();
    @Getter
    private final String publicKey = Base64.getEncoder().encodeToString(rsa.getPublicKey().getEncoded());
    @Getter
    private final String privateKey = Base64.getEncoder().encodeToString(rsa.getPrivateKey().getEncoded());

    public String decrypt(String encryptedText) {
        return rsa.decryptStr(encryptedText, KeyType.PrivateKey);
    }
    
    public String encrypt(String plainText) {
        return rsa.encryptHex(plainText, KeyType.PublicKey);
    }

    private RSAUtil() {
        // do nothing
    }
}
