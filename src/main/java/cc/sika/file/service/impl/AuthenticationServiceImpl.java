package cc.sika.file.service.impl;

import cc.sika.file.entity.dto.LoginDto;
import cc.sika.file.entity.po.SikaUser;
import cc.sika.file.entity.vo.UserInfoVo;
import cc.sika.file.exception.BeanTableException;
import cc.sika.file.exception.UserException;
import cc.sika.file.mapper.SikaUserMapper;
import cc.sika.file.service.AuthenticationService;
import cc.sika.file.service.SikaUserService;
import cc.sika.file.util.IdGenerator;
import cc.sika.file.util.RSAUtil;
import cc.sika.file.util.SecurityUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static cc.sika.file.util.SecurityUtil.verifyPassword;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${sika.login.captcha.enable}")
    private boolean enableCaptcha;

    @Resource
    private IdGenerator idGenerator;
    @Resource
    private SikaUserService sikaUserService;
    @Resource
    private SikaUserMapper sikaUserMapper;
    @Resource
    private RSAUtil rsaUtil;

    @Override
    public String login(LoginDto loginDto) {
        if (ObjectUtil.isNull(loginDto) || BeanUtil.isEmpty(loginDto)) {
            throw new IllegalArgumentException("参数异常");
        }
        // 校验验证码
        if (enableCaptcha) {
            log.warn("暂未校验验证码");
        }

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        // 解密用户名并做数据库校验
        String decryptedUsername = rsaUtil.decrypt(username);
        SikaUser selectedUser = sikaUserService.getOne(new LambdaQueryWrapper<SikaUser>().eq(SikaUser::getUsername, decryptedUsername));
        if (ObjectUtil.isEmpty(selectedUser)) {
            throw new UserException(HttpStatus.HTTP_BAD_REQUEST,
                    CharSequenceUtil.format("用户[{}]不存在", decryptedUsername));
        }
        // 用户信息存在, 解密密码并校验登录
        String decryptedPassword = rsaUtil.decrypt(password);
        if (!verifyPassword(decryptedPassword, selectedUser.getPassword())) {
            throw new UserException(HttpStatus.HTTP_BAD_REQUEST, "用户名或密码错误!");
        }
        return doLogin(selectedUser.getId());
    }

    private String doLogin(Long userId) {
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }

    @Override
    public String doRegister(SikaUser sikaUser) {
        // 尝试获取用户名并解密
        String name;
        try {
            if (CharSequenceUtil.isBlank(sikaUser.getUsername())) {
                throw new UserException(HttpStatus.HTTP_BAD_REQUEST, "用户名不能为空");
            }
            name = rsaUtil.decrypt(sikaUser.getUsername());
        } catch (CryptoException e) {
            log.error("解密失败", e);
            throw new UserException(HttpStatus.HTTP_BAD_REQUEST, "请提交正确加密的用户信息");
        }
        // 避免用户名重复
        if (userExists(name)) {
            throw new UserException(HttpStatus.HTTP_UNPROCESSABLE_ENTITY, String.format("用户名[%s]已存在", name));
        }
        // 用户注册成功时直接登录并响应token
        if (StpUtil.isLogin()) {
            Long loginId = Long.valueOf(StpUtil.getLoginId().toString());
            SikaUser user = buildUser(sikaUser, loginId);
            assertInsertSuccess(sikaUserMapper.insert(user));
        }
        // 主动注册, 生成新的id
        else {
            SikaUser user = buildUser(sikaUser, null);
            assertInsertSuccess(sikaUserMapper.insert(user));
            // 注册完成直接登录, 登录成功响应token
            return doLogin(user.getId());
        }
        return "";
    }

    @Override
    public UserInfoVo getUserInfo(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            throw new IllegalArgumentException("用户未登录");
        }
        Long id = Long.valueOf(StpUtil.getLoginIdByToken(token).toString());
        if (ObjectUtil.isNull(id)) {
            throw new IllegalArgumentException("非法token");
        }
        SikaUser user = sikaUserService.getById(id);
        return UserInfoVo.toVo(user);
    }

    boolean userExists(String username) {
        if (ObjectUtil.isEmpty(username)) {
            return false;
        }
        LambdaQueryWrapper<SikaUser> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(SikaUser::getUsername, username);
        return !BeanUtil.isEmpty(sikaUserService.getOne(userQuery, false));
    }

    private void assertInsertSuccess(int rowCount) throws BeanTableException {
        if (rowCount <= 0) {
            throw new BeanTableException("插入数据失败");
        }
    }

    /**
     * 构建用户信息对象, 将加密的用户名解密为明文
     *
     * @param registerDTO 用户信息
     * @param id          用户id, 内部创建时为当前登录人id, 用户主动注册时为新用户id
     * @return 用户信息对象
     */
    private SikaUser buildUser(SikaUser registerDTO, Long id) {
        if (ObjectUtil.isNull(id)) {
            id = idGenerator.newId();
        }
        String pw;
        String name;
        try {
            pw = rsaUtil.decrypt(registerDTO.getPassword());
            name = rsaUtil.decrypt(registerDTO.getUsername());
        } catch (CryptoException e) {
            log.error("解密失败!", e);
            throw new UserException();
        }
        return SikaUser.builder()
                .id(id)
                .nickname(registerDTO.getNickname())
                // 解密用户名
                .username(name)
                // 密码解密后转为不可逆写入数据库
                .password(SecurityUtil.encryptPassword(pw))
                .email(registerDTO.getEmail())
                .phone(registerDTO.getPhone())
                .avatar(registerDTO.getAvatar())
                .sex(registerDTO.getSex())
                .status(1)
                .build();
    }
}
