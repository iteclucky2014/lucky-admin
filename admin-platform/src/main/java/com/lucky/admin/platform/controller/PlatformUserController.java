package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.*;
import com.lucky.admin.platform.domain.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import com.lucky.admin.platform.repository.PlatformUserRepositoryDsl;
import com.lucky.admin.platform.vo.ChangePasswordParams;
import com.lucky.admin.platform.vo.PlatformUserSubmitParams;
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

@Api(value = "user", tags = "平台用户信息管理")
@RestController
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class PlatformUserController {

    @Autowired
    PlatformUserRepositoryDsl platformUserRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;

    @GetMapping(value = "user/security/{username}")
    public PlatformUser getPlatformUserByUsername(@PathVariable String username) {
        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        PlatformUser platformUser = queryFactory.selectFrom(qPlatformUser).where(qPlatformUser.username.eq(username)).fetchOne();
        return platformUser;
    }

    @PostMapping(value = "user/changepw")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult changePassword(@RequestBody ApiParams<ChangePasswordParams> changePasswordParams) {

        if (!changePasswordParams.getData().getPassword().equals(changePasswordParams.getData().getRepassword())) {
            return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg("两次输入密码不一致!").build();
        }

        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        Long count = queryFactory
                .update(qPlatformUser)
                .set(qPlatformUser.password, changePasswordParams.getData().getPassword())
                .where(qPlatformUser.username.eq(changePasswordParams.getData().getUsername())
                        .and(qPlatformUser.password.eq(changePasswordParams.getData().getOldPassword())))
                .execute();
        if (count == 0) {
            return ApiResultBuilder.create().code(ApiResultCode.DataError.code()).msg("旧密码不正确!").build();
        }
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改密码成功!").build();
    }

    @PostMapping(value = "user/setmyinfo")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult setMyInfo(@RequestBody ApiParams<PlatformUser> myInfo) {
        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        JPAUpdateClause updateClause = queryFactory.update(qPlatformUser);
        updateClause = updateClause.set(qPlatformUser.realName, myInfo.getData().getRealName());
        updateClause = updateClause.set(qPlatformUser.tel, myInfo.getData().getTel());
        updateClause.where(qPlatformUser.username.eq(myInfo.getData().getUsername())).execute();
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("基本资料修改成功!").build();
    }

    @GetMapping(value = "user/page")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<List<PlatformUser>> query(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String mail,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long limit) {

        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;
        //QBizCodeList qBizCodeList = QBizCodeList.bizCodeList;

        // 检索条件构造
        BooleanExpression whereExp = qPlatformUser.id.eq(qPlatformUser.id);
        if (StringUtils.isNotEmpty(username)) {
            whereExp = whereExp.and(qPlatformUser.username.eq(StringUtils.trim(username)));
        }
        if (StringUtils.isNotEmpty(password)) {
            whereExp = whereExp.and(qPlatformUser.password.eq(StringUtils.trim(password)));
        }
        if (StringUtils.isNotEmpty(realName)) {
            whereExp = whereExp.and(qPlatformUser.realName.like(StringUtils.trim(realName) + "%"));
        }
        if (StringUtils.isNotEmpty(tel)) {
            whereExp = whereExp.and(qPlatformUser.tel.like(StringUtils.trim(tel) + "%"));
        }
        if (StringUtils.isNotEmpty(mail)) {
            whereExp = whereExp.and(qPlatformUser.mail.eq(StringUtils.trim(mail)));
        }
        if (deptId != null) {
            whereExp = whereExp.and(qPlatformUser.dept.id.eq(deptId));
        }
        if (StringUtils.isNotEmpty(roleId)) {
            whereExp = whereExp.and(qPlatformUser.authorities.contains(
                    queryFactory
                            .selectFrom(qPlatformRole)
                            .where(qPlatformRole.id.eq(Long.parseLong(roleId)))
                            .fetchOne()));
        }
        if (StringUtils.isNotEmpty(roleName)) {
            whereExp = whereExp.and(qPlatformUser.authorities.contains(
                    queryFactory
                            .selectFrom(qPlatformRole)
                            .where(qPlatformRole.roleName.eq(roleName))
                            .fetchOne()));
        }

        // 件数取得
        Long count = queryFactory.selectFrom(qPlatformUser).where(whereExp).fetchCount();

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(page, limit, count);

        // 数据检索
        List<PlatformUser> data = queryFactory
                .selectFrom(qPlatformUser)
                .where(whereExp)
                .offset(pagination.getOffset())
                .limit(pagination.getLimit())
                .fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();
    }

    @PostMapping(value = "user/save")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<PlatformUser> save(@RequestBody ApiParams<PlatformUserSubmitParams> params) {

        // 数据校验
        if (StringUtils.isEmpty(params.getUsername())) {
            return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
        }

        PlatformUser target = null;
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;

        if (StringUtils.isEmpty(params.getData().getId())) {

            QPlatformUser qPlatformUser = QPlatformUser.platformUser;
            QPlatformDept qPlatformDept = QPlatformDept.platformDept;


            // 登录名唯一性检查
            Long count = queryFactory.selectFrom(qPlatformUser).where(qPlatformUser.username.eq(StringUtils.trim(params.getData().getUsername()))).fetchCount();
            if (count != 0) {
                return ApiResultBuilder.create().code(ApiResultCode.DataConflict.code()).msg("登录名已被其他用户使用！").build();
            }
            target = new PlatformUser(params.getData().getUsername(), params.getData().getPassword());
            target.setRealName(params.getData().getRealName());
            target.setTel(params.getData().getTel());
            target.setMail(params.getData().getMail());
            target.setDept(queryFactory.selectFrom(qPlatformDept).where(qPlatformDept.id.eq(params.getData().getDeptId())).fetchOne());
            target.setCreatedBy(params.getUsername());
            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));

        } else {
            target = platformUserRepositoryDsl.getOne(Long.parseLong(params.getData().getId()));

            QPlatformUser qPlatformUser = QPlatformUser.platformUser;
            QPlatformDept qPlatformDept = QPlatformDept.platformDept;

            // 登录名唯一性检查
            Long count = queryFactory.selectFrom(qPlatformUser)
                    .where(qPlatformUser.username.eq(StringUtils.trim(params.getData().getUsername()))
                    .and(qPlatformUser.username.notEqualsIgnoreCase(target.getUsername()))).fetchCount();
            if (count != 0) {
                return ApiResultBuilder.create().code(ApiResultCode.DataConflict.code()).msg("登录名已被其他用户使用！").build();
            }
            target.setUsername(params.getData().getUsername());
            target.setPassword(params.getData().getPassword());
            target.setRealName(params.getData().getRealName());
            target.setTel(params.getData().getTel());
            target.setMail(params.getData().getMail());
            target.setDept(queryFactory.selectFrom(qPlatformDept).where(qPlatformDept.id.eq(params.getData().getDeptId())).fetchOne());
            target.getAuthorities().clear();
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        }

        // 角色列表更新
        Set<PlatformRole> platformRoles = new HashSet<>();
        Arrays.stream(params.getData().getPlatformRoles()).forEach(roleId -> {
            platformRoles.add(queryFactory.selectFrom(qPlatformRole).where(qPlatformRole.id.eq(Long.parseLong(roleId))).fetchOne());
        });
        target.setAuthorities(platformRoles);
        target = platformUserRepositoryDsl.save(target);

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("保存成功").data(target).build();
    }

    @PostMapping(value = "user/delete")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {
        if (deleteParams.getData() == null) {
            return ApiResultBuilder.create()
                    .code(ApiResultCode.DataIllegality.code())
                    .msg(ApiResultCode.DataIllegality.msg()).build();
        }
        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        long count = queryFactory
                .delete(qPlatformUser)
                .where(qPlatformUser.id.in(deleteParams.getData().stream().map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList())))
                .execute();
        if (count == 0) {
            return ApiResultBuilder.create().code(ApiResultCode.DataNotExist.code()).msg(ApiResultCode.DataNotExist.msg()).build();
        }
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
    }
}
