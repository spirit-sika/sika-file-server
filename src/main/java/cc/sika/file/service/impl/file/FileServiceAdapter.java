package cc.sika.file.service.impl.file;

import cc.sika.file.consts.FileConsts;
import cc.sika.file.entity.dto.FileMetaDto;
import cc.sika.file.entity.po.SikaFileMeta;
import cc.sika.file.mapper.SikaFileMetaMapper;
import cc.sika.file.service.SikaFileMetaService;
import cc.sika.file.service.impl.BaseServiceImpl;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cc.sika.file.consts.FileConsts.MetaType.*;

/**
 *
 * @author 小吴来哩
 * @since 2025-09
 */
@Component
public class FileServiceAdapter extends BaseServiceImpl<SikaFileMetaMapper, SikaFileMeta> implements SikaFileMetaService {

    private static final long DEFAULT_PAGE_SERIAL = 1;
    private static final long DEFAULT_PAGE_SIZE = 20;

    @Resource
    private ApplicationContext context;

    @Override
    public List<SikaFileMeta> listFileInLayer(String id) {
        if (CharSequenceUtil.isBlank(id)) {
            return baseMapper.selectList(new LambdaQueryWrapper<SikaFileMeta>().isNull(SikaFileMeta::getParentId));
        }
        return baseMapper.selectList(new LambdaQueryWrapper<SikaFileMeta>().eq(SikaFileMeta::getParentId, id));
    }

    @Override
    public IPage<SikaFileMeta> pageFileInLayer(FileMetaDto fileMetaDto) {

        // 不带条件任何查询分页条件, 默认查询根目录下的20条记录
        if (ObjectUtil.isNull(fileMetaDto)) {
            return baseMapper.selectPage(new Page<>(DEFAULT_PAGE_SERIAL, DEFAULT_PAGE_SIZE),
                                         new LambdaQueryWrapper<SikaFileMeta>()
                                                 .isNull(SikaFileMeta::getParentId));
        }

        Page<SikaFileMeta> pageCondition = new Page<>(
                ObjectUtil.defaultIfNull(fileMetaDto.getCurrent(), DEFAULT_PAGE_SERIAL),
                ObjectUtil.defaultIfNull(fileMetaDto.getSize(), DEFAULT_PAGE_SIZE)
        );

        LambdaQueryWrapper<SikaFileMeta> queryCondition = new LambdaQueryWrapper<SikaFileMeta>()
                                                            .orderByDesc(SikaFileMeta::getMetaType)
                                                            .orderByDesc(SikaFileMeta::getCreateTime);

        // dto无查询条件, 查根路径所有
        if (ObjectUtil.isNull(fileMetaDto.getCondition())) {
            return baseMapper.selectPage(pageCondition, queryCondition.isNull(SikaFileMeta::getParentId));
        }

        SikaFileMeta condition = fileMetaDto.getCondition();
        // 根据条件拼接
        if (CharSequenceUtil.isNotBlank(condition.getParentId())) {
            queryCondition.eq(SikaFileMeta::getParentId, condition.getParentId());
        }
        // 不带parentId则查根路径
        else {
            queryCondition.isNull(SikaFileMeta::getParentId);
        }
        // 查所有文件时不拼接条件
        if (ObjectUtil.isNotNull(condition.getMetaType()) && !condition.getMetaType().equals(ALL_TYPE)) {
            queryCondition.eq(SikaFileMeta::getMetaType, condition.getMetaType());
        }
        // 两头like文件名
        if (CharSequenceUtil.isNotBlank(condition.getOriginalName())) {
            queryCondition.like(SikaFileMeta::getOriginalName, condition.getOriginalName());
        }
        return baseMapper.selectPage(pageCondition, queryCondition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mkDir(String dirName, String parentId) {
        // 创建根目录下的文件夹
        if (CharSequenceUtil.isBlank(parentId)) {
            String newId = IdUtil.getSnowflakeNextIdStr();
            SikaFileMeta inRoot = SikaFileMeta.builder()
                    .id(newId)
                    .parentId(ROOT)
                    .originalName(dirName)
                    .absolutePath(ROOT_PATH + dirName)
                    .absoluteIdPath(ROOT_ID + newId)
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
            String newId = IdUtil.getSnowflakeNextIdStr();
            SikaFileMeta inRoot = SikaFileMeta.builder()
                    .id(newId)
                    .parentId(parentId)
                    .absoluteIdPath(sikaFileMeta.getAbsoluteIdPath() + SEPARATOR + newId)
                    .absolutePath(sikaFileMeta.getAbsolutePath() + SEPARATOR + dirName)
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
            throw new UnsupportedOperationException("文件不支持重命名文件夹操作!");
        }
        String absolutePath = ObjectUtil.defaultIfNull(meta.getAbsolutePath(), "");
        if (CharSequenceUtil.isBlank(absolutePath)) {
            throw new UnsupportedOperationException("根路径文件名不支持修改!");
        }
        // 处理绝对路径
        meta.setOriginalName(newName);
        String newPath = absolutePath.substring(0, absolutePath.lastIndexOf(SEPARATOR)) + newName;
        meta.setAbsolutePath(newPath);
        // 处理绝对路径id
        String absoluteIdPath = ObjectUtil.defaultIfNull(meta.getAbsoluteIdPath(), "");
        String newIdPath = absoluteIdPath.substring(0, absoluteIdPath.lastIndexOf(SEPARATOR)) + newName;
        meta.setAbsoluteIdPath(newIdPath);
        baseMapper.updateById(meta);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rm(String id) {
        SikaFileMeta meta = baseMapper.selectById(id);
        if (meta.getMetaType().equals(DIR)) {
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

        // 删除文件夹自身, 文件直接删除
        baseMapper.deleteById(id);
    }

    @Override
    public void removeBatch(List<String> ids) {
        ids.forEach(context.getBean(SikaFileMetaService.class)::rm);
    }

    @Override
    public SikaFileMeta getMetaInfo(String id) {
        if (CharSequenceUtil.isBlank(id)) {
            throw new IllegalArgumentException("文件不存在");
        }
        SikaFileMeta sikaFileMeta = baseMapper.selectOne(new LambdaQueryWrapper<SikaFileMeta>().eq(SikaFileMeta::getId, id));
        if (ObjectUtil.isNull(sikaFileMeta)) {
            throw new IllegalArgumentException("文件不存在");
        }
        return sikaFileMeta;
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
