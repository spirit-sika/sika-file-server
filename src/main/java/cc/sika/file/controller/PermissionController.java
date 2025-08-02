package cc.sika.file.controller;

import cc.sika.file.entity.dto.PermissionDto;
import cc.sika.file.entity.po.SikaPermission;
import cc.sika.file.service.SikaPermissionService;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@RestController
@RequestMapping("permission")
public class PermissionController extends BaseController<SikaPermissionService, SikaPermission, PermissionDto> {
}
