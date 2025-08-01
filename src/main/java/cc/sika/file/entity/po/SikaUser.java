package cc.sika.file.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@TableName(value ="SIKA_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SikaUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "ID")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "PASSWORD")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "EMAIL")
    private String email;

    /**
     * 手机号码
     */
    @TableField(value = "PHONE")
    private String phone;

    /**
     * 头像url
     */
    @TableField(value = "AVATAR")
    private String avatar;

    /**
     * 性别: 0-未知, 1-男, 2-女
     */
    @TableField(value = "SEX")
    private Integer sex;

    /**
     * 状态, 0禁用,1-正常, 2-已删除
     */
    @TableField(value = "STATUS")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 创建人用户名
     */
    @TableField(value = "CREATE_BY")
    private String createBy;

    /**
     * 创建人id
     */
    @TableField(value = "CREATE_ID")
    private Long createId;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 更新人用户名
     */
    @TableField(value = "UPDATE_BY")
    private String updateBy;

    /**
     * 更新人id
     */
    @TableField(value = "UPDATE_ID")
    private Long updateId;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

}