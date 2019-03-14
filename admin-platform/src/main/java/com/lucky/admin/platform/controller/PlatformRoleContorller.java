package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.*;
import com.lucky.admin.platform.domain.PlatformPermission;
import com.lucky.admin.platform.domain.QPlatformPermission;
import com.lucky.admin.platform.domain.QPlatformRole;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.lucky.admin.platform.domain.PlatformRole;
import com.lucky.admin.platform.repository.PlatformRoleRepositoryDsl;
import com.lucky.admin.platform.vo.PlatformRoleSubmitParams;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(value = "role", tags = "平台角色信息管理")
@RestController
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class PlatformRoleContorller {

    @Autowired
    PlatformRoleRepositoryDsl platformRoleRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;

    @GetMapping(value = "role/page")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResult<List<PlatformRole>> query(
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long limit) {

        QPlatformRole qPlatformRole = QPlatformRole.platformRole;

        // 检索条件构造
        BooleanExpression whereExp = qPlatformRole.id.eq(qPlatformRole.id);
        if (roleId != null) {
            whereExp = whereExp.and(qPlatformRole.id.eq(roleId));
        }
        if (StringUtils.isNotEmpty(roleName)) {
            whereExp = whereExp.and(qPlatformRole.roleName.contains(StringUtils.trim(roleName)));
        }

        // 件数取得
        Long count = queryFactory.selectFrom(qPlatformRole).where(whereExp).fetchCount();
        if (count == 0) {
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("没有检索到任何数据").build();
        }

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(page, limit, count);

        // 数据检索
        List<PlatformRole> data = queryFactory
                    .selectFrom(qPlatformRole)
                    .where(whereExp)
                    .offset(pagination.getOffset())
                    .limit(pagination.getLimit())
                    .fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();
    }

    @PostMapping(value = "role/save")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<PlatformRole> save(@RequestBody ApiParams<PlatformRoleSubmitParams> params) {
        PlatformRole target = null;
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;
        QPlatformPermission qPlatformPermission = QPlatformPermission.platformPermission;

        // 数据校验
        if (StringUtils.isEmpty(params.getUsername())) {
            return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
        }

        String role = params.getData().getRole();
        String roleName = params.getData().getRoleName();
        if (StringUtils.isEmpty(role) && StringUtils.isNotEmpty(roleName)) {
            role = roleName;
        }

        if (StringUtils.isEmpty(params.getData().getId())) {

            // 唯一性校验
            Long count = queryFactory.selectFrom(qPlatformRole)
                    .where(qPlatformRole.roleName.eq(StringUtils.trim(params.getData().getRoleName())))
                    .fetchCount();
            if (count != 0) {
                return ApiResultBuilder.create().code(ApiResultCode.DataConflict.code()).msg("这个角色已存在").build();
            }

            target = new PlatformRole(role, roleName);
            target.setRoleDesc(params.getData().getRoleDesc());
            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            target = platformRoleRepositoryDsl.getOne(Long.parseLong(params.getData().getId()));

            // 唯一性校验
            Long count = queryFactory.selectFrom(qPlatformRole)
                    .where(qPlatformRole.roleName.eq(StringUtils.trim(params.getData().getRoleName()))
                            .and(qPlatformRole.roleName.notEqualsIgnoreCase(target.getRoleName())))
                    .fetchCount();
            if (count != 0) {
                return ApiResultBuilder.create().code(ApiResultCode.DataConflict.code()).msg("这个角色已存在").build();
            }

            target.setRole(role);
            target.setRoleName(roleName);
            target.setRoleDesc(params.getData().getRoleDesc());
            target.getPlatformPermissions().clear();
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        }

        Set<PlatformPermission> platformPermissions = new HashSet<>();
        Arrays.stream(params.getData().getPlatformPermissions()).forEach(permissionId -> {
            platformPermissions.add(queryFactory.selectFrom(qPlatformPermission).where(qPlatformPermission.id.eq(Long.parseLong(permissionId))).fetchOne());
        });
        target.getPlatformPermissions().addAll(platformPermissions);
        target = platformRoleRepositoryDsl.save(target);

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg(ApiResultCode.Success.msg())
                .data(target).build();
    }

    @PostMapping(value = "role/delete")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {

        if (deleteParams.getData() == null) {
            return ApiResultBuilder.create()
                    .code(ApiResultCode.DataIllegality.code())
                    .msg(ApiResultCode.DataIllegality.msg()).build();
        }
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;
        long count = queryFactory
                .delete(qPlatformRole)
                .where(qPlatformRole.id.in(deleteParams.getData().stream().map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList())))
                .execute();
        if (count == 0) {
            return ApiResultBuilder.create().code(ApiResultCode.DataNotExist.code()).msg(ApiResultCode.DataNotExist.msg()).build();
        }
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
    }
}
