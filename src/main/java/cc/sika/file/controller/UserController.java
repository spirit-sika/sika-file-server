package cc.sika.file.controller;

import cc.sika.file.entity.dto.UserDto;
import cc.sika.file.entity.po.SikaUser;
import cc.sika.file.service.SikaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController extends BaseController<SikaUserService, SikaUser, UserDto> {
}
