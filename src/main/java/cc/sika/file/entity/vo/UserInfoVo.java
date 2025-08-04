package cc.sika.file.entity.vo;

import cc.sika.file.entity.po.SikaUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoVo implements Serializable {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Integer sex;
    private Integer status;

    public static UserInfoVo toVo(SikaUser po) {
        return UserInfoVo.builder()
                .id(po.getId())
                .username(po.getUsername())
                .nickname(po.getNickname())
                .email(po.getEmail())
                .phone(po.getPhone())
                .avatar(po.getAvatar())
                .sex(po.getSex())
                .status(po.getStatus())
                .build();
    }
}
