package cn.sambo.difference.platform.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cn.sambo.difference.platform.domain.QPlatformDept;
import cn.sambo.difference.platform.common.*;
import cn.sambo.difference.platform.domain.PlatformDept;
import cn.sambo.difference.platform.repository.PlatformDeptRepositoryDsl;
import cn.sambo.difference.platform.vo.PlatformDeptSubmitParams;
import cn.sambo.difference.platform.domain.QPlatformUser;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value = "dept", tags = "平台部门信息管理")
@RestController
@RequestMapping(value = "/")
@SuppressWarnings("unchecked")
public class PlatformDeptController {

    @Autowired
    PlatformDeptRepositoryDsl platformDeptRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;

    @GetMapping(value = "dept/page")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResult<List<PlatformDept>> query(
            @RequestParam(required = false) Long deptId,
            @RequestParam(required = false) String deptName,
            @RequestParam(required = false) Long parentDeptId,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long limit) {

        QPlatformDept qPlatformDept = QPlatformDept.platformDept;

        // 检索条件构造
        BooleanExpression whereExp = qPlatformDept.id.eq(qPlatformDept.id);
        if (StringUtils.isNotEmpty(deptName)) {
            whereExp = whereExp.and(qPlatformDept.deptName.contains(StringUtils.trim(deptName)));
        }
        if (deptId != null) {
            whereExp = whereExp.and(qPlatformDept.id.eq(deptId));
        }
        if (parentDeptId != null) {
            whereExp = whereExp.and(qPlatformDept.parentDept.id.eq(parentDeptId));
        }

        // 件数取得
        Long count = queryFactory.selectFrom(qPlatformDept).where(whereExp).fetchCount();

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(page, limit, count);

        // 数据检索
        List<PlatformDept> data = queryFactory
                .selectFrom(qPlatformDept)
                .where(whereExp)
                .offset(pagination.getOffset())
                .limit(pagination.getLimit())
                .fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();
    }


    @GetMapping(value = "dept/getDeptTreeData")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<Map<String, Object>> getDeptTreeData(@RequestParam(required = false) String userId) {

        List<Map<String, Object>> result = Lists.newArrayList();
        
        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        QPlatformDept qPlatformDept = QPlatformDept.platformDept;
        List<PlatformDept> rootDepts;
        
        if(StringUtils.isEmpty(userId)) {
        	//从顶级部门取得全体部门
            rootDepts = queryFactory.selectFrom(qPlatformDept)
                .where(qPlatformDept.delFlag.eq(0), qPlatformDept.parentDept.id.isNull())
                .fetch();
        }else {
        	//根据登陆用户的部门权限取得部门和子部门
            rootDepts = queryFactory.selectFrom(qPlatformDept)
                .where(qPlatformDept.delFlag.eq(0)
                		,qPlatformDept.deptUserList.contains(queryFactory
                                .selectFrom(qPlatformUser)
                                .where(qPlatformUser.id.eq(Long.parseLong(userId)))
                                .fetchOne())
                		).fetch();
        }
        for (PlatformDept rootDept : rootDepts) {
            Map<String, Object> rootMap = Maps.newLinkedHashMap();
            // 第一层
            if (rootDept != null) {
                rootMap.put("id", rootDept.getId());
                rootMap.put("name", rootDept.getDeptName());
                rootMap.put("deptId", rootDept.getDeptId());
                rootMap.put("centerPoint", rootDept.getCenterPoint());
                rootMap.put("deptRange", rootDept.getDeptRange());
                rootMap.put("zoom", rootDept.getZoom());
                rootMap.put("open", true);
                rootMap.put("checked", false);

                // 第二层
                if (!rootDept.getChildrenDeptList().isEmpty()) {
                    List<Map<String, Object>> rootChildren = new ArrayList<>();
                    rootMap.put("children", rootChildren);
                    for (PlatformDept level2Dept : rootDept.getChildrenDeptList()) {
                        Map<String, Object> leve12Map = Maps.newLinkedHashMap();
                        rootChildren.add(leve12Map);
                        leve12Map.put("id", level2Dept.getId());
                        leve12Map.put("name", level2Dept.getDeptName());
                        leve12Map.put("deptId", level2Dept.getDeptId());
                        leve12Map.put("centerPoint", level2Dept.getCenterPoint());
                        leve12Map.put("deptRange", level2Dept.getDeptRange());
                        leve12Map.put("zoom", level2Dept.getZoom());
                        leve12Map.put("open", true);
                        leve12Map.put("checked", false);

                        // 第三层
                        if (!level2Dept.getChildrenDeptList().isEmpty()) {
                            List<Map<String, Object>> level2Children = new ArrayList<>();
                            leve12Map.put("children", level2Children);
                            for (PlatformDept level3Dept : level2Dept.getChildrenDeptList()) {
                                Map<String, Object> leve13Map = Maps.newLinkedHashMap();
                                level2Children.add(leve13Map);
                                leve13Map.put("id", level3Dept.getId());
                                leve13Map.put("name", level3Dept.getDeptName());
                                leve13Map.put("deptId", level3Dept.getDeptId());
                                leve13Map.put("centerPoint", level3Dept.getCenterPoint());
                                leve13Map.put("deptRange", level3Dept.getDeptRange());
                                leve13Map.put("zoom", level3Dept.getZoom());
                                leve13Map.put("open", true);
                                leve13Map.put("checked", false);

                                // 第四层
                                if (!level3Dept.getChildrenDeptList().isEmpty()) {
                                    System.out.println("只支持三层解析！！！");
                                }

                            }
                        }

                    }
                }

            }
            result.add(rootMap);
        }

        return result;
    }

    @GetMapping(value = "dept/getDeptTreeGridData")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult getDeptTreeGridData() {

        List<Map<String, Object>> result = Lists.newArrayList();

        QPlatformDept qPlatformDept = QPlatformDept.platformDept;
        List<PlatformDept> deptList = queryFactory.selectFrom(qPlatformDept)
                .orderBy(qPlatformDept.parentDept.id.asc(), qPlatformDept.dispOrder.asc())
                .fetch();

        for (PlatformDept dept : deptList) {
            Map<String, Object> row = Maps.newLinkedHashMap();
            row.put("id", dept.getId());
            if (dept.getParentDept() == null) {
                row.put("pid", 0);
            } else {
                row.put("pid", dept.getParentDept().getId());
            }
            row.put("deptName", dept.getDeptName());
            row.put("deptId", dept.getDeptId());
            row.put("acreage", dept.getAcreage());
            row.put("waterDemand", dept.getWaterDemand());
            row.put("delFlag", dept.getDelFlag());
            row.put("centerPoint", dept.getCenterPoint());
            row.put("deptrange", dept.getDeptRange());
            row.put("zoom", dept.getZoom());
            row.put("dispOrder", dept.getDispOrder());
            result.add(row);
        }

        return ApiResultBuilder.create().code(0).msg("成功返回").data(result).build();
    }



    @PostMapping(value = "dept/save")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<PlatformDept> save(@RequestBody ApiParams<PlatformDeptSubmitParams> params) {
        PlatformDept target = null;

        PlatformDept parentDept = null;
        if (params.getData().getParentDeptId() != null) {
            parentDept = platformDeptRepositoryDsl.getOne(params.getData().getParentDeptId());
        }

        if (params.getData().getId() == null) {
            target = new PlatformDept();
            target.setDeptName(params.getData().getDeptName());
            target.setDeptId(params.getData().getDeptId());
            target.setAcreage(params.getData().getAcreage());
            target.setWaterDemand(params.getData().getWaterDemand());
            target.setCenterPoint(params.getData().getCenterPoint());
            target.setDeptRange(params.getData().getDeptRange());
            target.setZoom(params.getData().getZoom());
            target.setParentDept(parentDept);
            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            target = platformDeptRepositoryDsl.getOne(params.getData().getId());
            target.setDeptName(params.getData().getDeptName());
            target.setDeptId(params.getData().getDeptId());
            target.setAcreage(params.getData().getAcreage());
            target.setWaterDemand(params.getData().getWaterDemand());
            target.setCenterPoint(params.getData().getCenterPoint());
            target.setDeptRange(params.getData().getDeptRange());
            target.setZoom(params.getData().getZoom());
            target.setParentDept(parentDept);
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        target = platformDeptRepositoryDsl.save(target);
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("保存成功").data(target).build();
    }

    @PostMapping(value = "dept/delete")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {
        if (deleteParams.getData() == null) {
            return ApiResultBuilder.create()
                    .code(ApiResultCode.DataIllegality.code())
                    .msg(ApiResultCode.DataIllegality.msg()).build();
        }

        List<Long> ids = deleteParams.getData().stream().map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        ids.forEach(id -> platformDeptRepositoryDsl.deleteById(id));

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
    }

    @PostMapping(value = "dept/editDept")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult editMenu(@RequestBody ApiParams<PlatformDeptSubmitParams> params) {

        PlatformDept target = platformDeptRepositoryDsl.getOne(params.getData().getId());
        if (StringUtils.isNotEmpty(params.getData().getDeptName())) target.setDeptName(params.getData().getDeptName());
        if (params.getData().getDispOrder() != null) target.setDispOrder(params.getData().getDispOrder());
        target.setDeptId(params.getData().getDeptId());
        target.setAcreage(params.getData().getAcreage());
        target.setWaterDemand(params.getData().getWaterDemand());
        target.setCenterPoint(params.getData().getCenterPoint());
        target.setDeptRange(params.getData().getDeptRange());
        target.setZoom(params.getData().getZoom());
        target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        target.setUpdatedBy(params.getUsername());

        // 父子关系更新
        if (params.getData().getParentDeptId() != null) {
            target.setParentDept(platformDeptRepositoryDsl.getOne(params.getData().getParentDeptId()));
        } else {
            target.setParentDept(null);
        }
        return ApiResultBuilder.create().code(0).msg("部门编辑成功").build();
    }

    @PostMapping(value = "dept/addDept")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult addMenu(@RequestBody ApiParams<PlatformDeptSubmitParams> params) {

        PlatformDept target = new PlatformDept();
        if (StringUtils.isNotEmpty(params.getData().getDeptName())) target.setDeptName(params.getData().getDeptName());
        if (params.getData().getDispOrder() != null) target.setDispOrder(params.getData().getDispOrder());
        target.setDeptId(params.getData().getDeptId());
        target.setAcreage(params.getData().getAcreage());
        target.setWaterDemand(params.getData().getWaterDemand());
        target.setCenterPoint(params.getData().getCenterPoint());
        target.setDeptRange(params.getData().getDeptRange());
        target.setZoom(params.getData().getZoom());
        target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        target.setCreatedBy(params.getUsername());
        target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        target.setUpdatedBy(params.getUsername());

        // 父子关系更新
        if (params.getData().getParentDeptId() != null) {
            target.setParentDept(platformDeptRepositoryDsl.getOne(params.getData().getParentDeptId()));
        } else {
            target.setParentDept(null);
        }

        platformDeptRepositoryDsl.save(target); // 转化为持久对象

        return ApiResultBuilder.create().code(0).msg("部门添加成功").build();
    }

    @PostMapping(value = "dept/logicDel/{id}/{delFlag}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult logicDelDept(@PathVariable Long id, @PathVariable Integer delFlag) {
        PlatformDept dept = platformDeptRepositoryDsl.getOne(id);
        dept.setDelFlag(delFlag);
        return ApiResultBuilder.create().code(0).msg("状态更新成功").build();
    }
}
