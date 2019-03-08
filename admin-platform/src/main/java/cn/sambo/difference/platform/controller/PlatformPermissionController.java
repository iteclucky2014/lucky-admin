package cn.sambo.difference.platform.controller;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cn.sambo.difference.platform.common.*;
import cn.sambo.difference.platform.domain.PlatformPermission;
import cn.sambo.difference.platform.domain.QPlatformPermission;
import cn.sambo.difference.platform.repository.PlatformPermissionRepositoryDsl;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "permission", tags = "平台权限信息管理")
@RestController
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class PlatformPermissionController {

    @Autowired
    PlatformPermissionRepositoryDsl platformPermissionRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;

    @GetMapping(value = "permission/page")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResult<List<PlatformPermission>> query(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long limit) {

        QPlatformPermission qPlatformPermission = QPlatformPermission.platformPermission;

        // 检索条件构造
        BooleanExpression whereExp = qPlatformPermission.id.eq(qPlatformPermission.id);
        if (StringUtils.isNotEmpty(name)) {
            whereExp = whereExp.and(qPlatformPermission.name.like("%" + StringUtils.trim(name) + "%"));
        }
        if (type != null) {
            whereExp = whereExp.and(qPlatformPermission.type.eq(type));
        }

        // 件数取得
        Long count = queryFactory.selectFrom(qPlatformPermission).where(whereExp).fetchCount();

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(page, limit, count);

        // 数据检索
        List<PlatformPermission> data = queryFactory
                .selectFrom(qPlatformPermission)
                .where(whereExp)
                .offset(pagination.getOffset())
                .limit(pagination.getLimit())
                .fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();
    }

    @PostMapping(value = "permission/save")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<PlatformPermission> save(@RequestBody ApiParams<PlatformPermission> params) {
        PlatformPermission target = null;
        if (params.getData().getId() == null) {
            target = new PlatformPermission(params.getData().getName(), params.getData().getUrl(), params.getData().getType());
            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            target = platformPermissionRepositoryDsl.getOne(params.getData().getId());
            target.setName(params.getData().getName());
            target.setUrl(params.getData().getUrl());
            target.setType(params.getData().getType());
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        target = platformPermissionRepositoryDsl.save(target);
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("保存成功").data(target).build();
    }

    @PostMapping(value = "permission/import")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiUploadResult batchImport(@RequestParam MultipartFile file) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0 || row.getRowNum() == 1) {
                    continue;
                }
                String name = row.getCell(0).getStringCellValue();
                String url = row.getCell(1).getStringCellValue();
                double type = row.getCell(2).getNumericCellValue();
                PlatformPermission target = new PlatformPermission(name, url, (int)type);
                target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                target.setCreatedBy("批量导入");
                target.setUpdatedBy("批量导入");
                platformPermissionRepositoryDsl.save(target);
            }
        } catch (Exception e) {
            throw new BizException(e);
        }
        return new ApiUploadResult();
    }

    @PostMapping(value = "permission/delete")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {
        if (deleteParams.getData() == null) {
            return ApiResultBuilder.create()
                    .code(ApiResultCode.DataIllegality.code())
                    .msg(ApiResultCode.DataIllegality.msg()).build();
        }
        List<Long> ids = deleteParams.getData().stream().map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        ids.forEach(id -> platformPermissionRepositoryDsl.deleteById(id));
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
    }
}
