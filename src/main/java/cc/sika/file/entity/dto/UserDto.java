package cc.sika.file.entity.dto;

import cc.sika.file.entity.po.SikaUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息查询相关dto
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BasePageAdapter<SikaUser> {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Integer sex;
    private Integer status;
}
