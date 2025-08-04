package cc.sika.file.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录dto
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String captcha;

    /**
     * 记住我, 1-是, 2-否
     */
    private Integer rememberMe;

    /**
     * 有效token时长, 毫秒, -1时不限制, 使用系统默认token时长
     */
    private long expire;
}
