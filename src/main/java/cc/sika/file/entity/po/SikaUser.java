package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@TableName(value ="sika_user")
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("all")
public class SikaUser extends BaseEntityInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @NotBlank
    private String username;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 密码
     */
    @TableField(value = "password")
    @NotBlank
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @Email
    private String email;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 头像url
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    @TableField(value = "sex")
    @Min(0)
    @Max(2)
    private Integer sex;

    /**
     * 状态, 0禁用, 1-正常, 2-已删除
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    public SikaUser(LocalDateTime createTime, String createBy, Long createId, LocalDateTime updateTime, String updateBy, Long updateId, Long id, String username, String password, String email, String phone, String avatar, Integer sex, Integer status) {
        super(createTime, createBy, createId, updateTime, updateBy, updateId);
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.sex = sex;
        this.status = status;
    }
}