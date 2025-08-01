package cc.sika.file.controller;

import cc.sika.file.entity.po.SikaPermission;
import cc.sika.file.service.SikaPermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Resource
    private SikaPermissionService sikaPermissionService;

    @PostMapping
    public Boolean post(@RequestBody SikaPermission sikaPermission) {
        return sikaPermissionService.saveOrUpdate(sikaPermission);
    }

    @GetMapping
    public String getPermissions() {
        return "greeting!";
    }
}
