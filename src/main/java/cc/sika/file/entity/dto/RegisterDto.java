package cc.sika.file.entity.dto;

import cc.sika.file.entity.po.SikaUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 注册DTO
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@Data
public class RegisterDto implements Serializable {
    private SikaUser sikaUser;

    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
