package cc.sika.file.mapper;

import cc.sika.file.entity.po.SikaPermission;
import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author 小吴来哩
 * @since 2025-08
 */
@SpringBootTest
class SikaPermissionMapperTest {

    private static final String TEST_USER_NAME = "sika";
    private static final Long TEST_USER_ID = -1L;
    private static final String TEST_PERMISSION = "sika";

    @Resource
    private SikaPermissionMapper sikaPermissionMapper;

    private static Long id;

    @BeforeAll
    static void setup() {
        id = IdUtil.getSnowflakeNextId();
    }

    @Test
    void testSaveAndSelect() {
        SikaPermission permission = SikaPermission.builder()
                .id(id)
                .permissionContent(TEST_PERMISSION)
                .permissionDesc("权限mapper测试")
                .permissionType(1)
                .createTime(LocalDateTime.now())
                .createBy(TEST_USER_NAME)
                .createId(TEST_USER_ID)
                .updateTime(LocalDateTime.now())
                .updateBy(TEST_USER_NAME)
                .updateId(TEST_USER_ID)
                .build();
        int inserted = sikaPermissionMapper.insert(permission);
        Assertions.assertEquals(1, inserted);
        SikaPermission selected = sikaPermissionMapper.selectById(id);
        Assertions.assertEquals(permission.getId(), selected.getId());
    }

    @Test
    void testUpdateAndSelect() {
        SikaPermission permission = SikaPermission.builder()
                .id(id)
                .permissionContent(TEST_PERMISSION)
                .permissionDesc("更新权限描述")
                .permissionType(1)
                .createTime(LocalDateTime.now())
                .createBy(TEST_USER_NAME)
                .createId(TEST_USER_ID)
                .updateTime(LocalDateTime.now())
                .updateBy(TEST_USER_NAME)
                .updateId(TEST_USER_ID)
                .build();
        int updated = sikaPermissionMapper.updateById(permission);
        Assertions.assertEquals(1, updated);
        SikaPermission selected = sikaPermissionMapper.selectById(id);
        Assertions.assertEquals("更新权限描述", selected.getPermissionDesc());
    }

    @Test
    void testDelete() {
        int deleted = sikaPermissionMapper.deleteById(id);
        Assertions.assertEquals(1, deleted);
        SikaPermission selected = sikaPermissionMapper.selectById(id);
        Assertions.assertNull(selected);
    }
}
