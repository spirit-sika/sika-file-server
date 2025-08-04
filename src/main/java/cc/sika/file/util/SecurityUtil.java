package cc.sika.file.util;

import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Component;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Component
public class SecurityUtil {

    /**
     * 生成加密后的密码
     * @param plainPassword 原始密码
     * @return 加密后的密码（不可逆）
     */
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * 校验密码是否匹配
     * @param plainPassword 用户输入的原始密码
     * @param hashedPassword 数据库存储的加密密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    private SecurityUtil() {
        // do nothing
    }
}
