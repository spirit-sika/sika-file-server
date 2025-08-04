package cc.sika.file.service;

import cc.sika.file.entity.dto.LoginDto;
import cc.sika.file.entity.po.SikaUser;
import cc.sika.file.entity.vo.UserInfoVo;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
public interface AuthenticationService {

    /**
     * 用户登录, 登录成功后返回token
     * @param loginDto 登录表单信息
     * @return 可用于鉴权的token
     */
    String login(LoginDto loginDto);

    /**
     * 用户注册, 注册成功自动登录并返回token
     * @param sikaUser 用户信息表单
     * @return 可用于鉴权的token
     */
    String doRegister(SikaUser sikaUser);

    /**
     * 根据token获取用户信息
     * @param token -
     * @return -
     */
    UserInfoVo getUserInfo(String token);

}
