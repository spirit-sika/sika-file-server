package cc.sika.file.service.impl.file;

import cc.sika.file.consts.FileConsts;
import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.mapper.SikaFileMetaMapper;
import cc.sika.file.service.SikaFileMetaService;
import cc.sika.file.service.impl.BaseServiceImpl;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cc.sika.file.consts.FileConsts.MetaType.DIR;
import static cc.sika.file.consts.FileConsts.MetaType.FILE;

/**
 *
 * @author 小吴来哩
 * @since 2025-09
 */
@Component
public class FileServiceAdapter extends BaseServiceImpl<SikaFileMetaMapper, SikaFileMeta> implements SikaFileMetaService {

    @Resource
    private ApplicationContext context;

    @Override
    public List<SikaFileMeta> listFileInLayer(String id) {
        if (CharSequenceUtil.isEmpty(id)) {
            return baseMapper.selectList(new LambdaQueryWrapper<SikaFileMeta>().isNull(SikaFileMeta::getParentId));
        }
        return baseMapper.selectList(new LambdaQueryWrapper<SikaFileMeta>().eq(SikaFileMeta::getParentId, id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mkDir(String dirName, String parentId) {
        // 创建根目录下的文件夹
        if (CharSequenceUtil.isBlank(parentId)) {
            SikaFileMeta inRoot = SikaFileMeta.builder()
                    .id(IdUtil.getSnowflakeNextIdStr())
                    .parentId(ROOT)
                    .originalName(dirName)
                    .regionTarget("ALL")
                    .metaType(DIR)
                    .build();
            baseMapper.insert(inRoot);
        }
        else {
            SikaFileMeta sikaFileMeta = baseMapper.selectById(parentId);
            // 父文件夹id不存在, 创建根目录下的文件夹
            if (ObjectUtil.isNull(sikaFileMeta)) {
                context.getBean(SikaFileMetaService.class).mkDir(dirName, ROOT);
                return;
            }
            // 传递上级文件夹id不是文件夹而是文件, 创建与文件同级的文件夹
            if (sikaFileMeta.getMetaType().equals(FILE)) {
                String upLayerId = sikaFileMeta.getParentId();
                context.getBean(SikaFileMetaService.class).mkDir(dirName, upLayerId);
                return;
            }
            SikaFileMeta inRoot = SikaFileMeta.builder()
                    .id(IdUtil.getSnowflakeNextIdStr())
                    .parentId(parentId)
                    .originalName(dirName)
                    .regionTarget("ALL")
                    .metaType(DIR)
                    .build();
            baseMapper.insert(inRoot);
        }
    }

    @Override
    public void renameDir(String dirId, String newName) {
        SikaFileMeta meta = baseMapper.selectById(dirId);
        // 文件不支持修改
        if (meta.getMetaType().equals(FileConsts.MetaType.FILE)) {
            return;
        }
        meta.setOriginalName(newName);
        baseMapper.updateById(meta);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rm(String id) {
        SikaFileMeta meta = baseMapper.selectById(id);
        // 文件直接删除
        if (meta.getMetaType().equals(FILE)) {
            baseMapper.deleteById(id);
            return;
        }
        // 文件夹查出关联的子文件夹和文件
        LambdaQueryWrapper<SikaFileMeta> childrenCondition = new LambdaQueryWrapper<SikaFileMeta>()
                .eq(SikaFileMeta::getParentId, id);
        List<SikaFileMeta> metaList = baseMapper.selectList(childrenCondition);
        // 如果存在文件, 清空文件, 再继续递归文件夹
        Map<Integer, List<SikaFileMeta>> typedMap = metaList
                .stream()
                .collect(Collectors.groupingBy(SikaFileMeta::getMetaType));
        typedMap.forEach((type, fileMetaList) -> {
            if (ObjectUtil.isNull(type)) {
                return;
            }
            if (type.equals(FILE)) {
                baseMapper.deleteByIds(fileMetaList.stream().map(SikaFileMeta::getId).toList());
            }
            fileMetaList.forEach(dir -> context.getBean(SikaFileMetaService.class).rm(dir.getId()));
        });
    }

    @Override
    public void removeBatch(List<String> ids) {
        ids.forEach(context.getBean(SikaFileMetaService.class)::rm);
    }

    @Override
    public String uploadAsync(MultipartFile file) {
        return "";
    }

    @Override
    public String uploadAsync(MultipartFile file, String parentId) {
        return "";
    }

    @Override
    public String uploadSync(MultipartFile file) {
        return "";
    }

    @Override
    public String uploadSync(MultipartFile file, String parentId) {
        return "";
    }

    @Override
    public Flux<Integer> uploadAndGet(MultipartFile file) {
        return null;
    }

    @Override
    public Flux<Integer> uploadAndGet(MultipartFile file, String parentId) {
        return null;
    }
}
