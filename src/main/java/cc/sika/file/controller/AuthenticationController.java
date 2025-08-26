package cc.sika.file.controller;

import cc.sika.file.entity.dto.LoginDto;
import cc.sika.file.entity.po.SikaUser;
import cc.sika.file.entity.vo.R;
import cc.sika.file.entity.vo.UserInfoVo;
import cc.sika.file.service.AuthenticationService;
import cc.sika.file.util.RSAUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 鉴权相关接口控制器
 * <p>
 * 处理登录, 注册, 公钥, 验证码相关功能
 *
 * @author 小吴来哩
 * @since 2025-08
 */
@RestController
@RequestMapping("auth")
@Tag(name = "鉴权管理", description = "用户处理登录, 注册, 公钥, 验证码相关接口")
public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private RSAUtil rsaUtil;

    @GetMapping("pk")
    @Operation(summary = "获取登录公钥")
    public R<String> publicKey() {
        return R.success(rsaUtil.getPublicKey());
    }

    @PostMapping("register")
    @Operation(summary = "注册")
    public R<String> register(@RequestBody SikaUser sikaUser) {
        return R.success(authenticationService.doRegister(sikaUser));
    }

    @PostMapping
    @Operation(summary = "登录")
    public R<String> login(@RequestBody LoginDto loginDto) {
        return R.success(authenticationService.login(loginDto));
    }

    @GetMapping
    @Operation(summary = "获取用户信息")
    public R<UserInfoVo> getUserInfo(@RequestHeader String token) {
        return R.success(authenticationService.getUserInfo(token));
    }

    @GetMapping("encrypt")
    @Operation(summary = "加密文本")
    public R<String> encrypt(String plainText) {
        return R.success(rsaUtil.encrypt(plainText));
    }

    @GetMapping("captcha")
    @Operation(summary = "获取验证码")
    public R<String> getCaptcha() {
        return R.success(authenticationService.loadCaptcha());
    }

}
