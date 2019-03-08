package cn.sambo.difference.platform.controller;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import cn.sambo.difference.platform.common.*;
import cn.sambo.difference.platform.domain.*;
import cn.sambo.difference.platform.repository.MenuRepositoryDsl;
import cn.sambo.difference.platform.vo.MenuSubmitParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "menu", tags = "")
@RestController
@RequestMapping(value = "/menu")
@SuppressWarnings("unchecked")
public class MenuController {

    @Autowired
    MenuRepositoryDsl menuRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;

    @ApiOperation(value = "查询",
            notes = "支持分页查询，默认返回10条记录")
    @GetMapping(value = "/query")
    @ResponseStatus(code = HttpStatus.OK)
    public ApiResult<List<Menu>> query(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) @ApiParam(value = "菜单名称") String name,
            @RequestParam(required = false) @ApiParam(value = "菜单标题") String title,
            @RequestParam(required = false) @ApiParam(value = "菜单图标") String icon,
            @RequestParam(required = false) @ApiParam(value = "菜单跳转") String jump,
            @RequestParam(required = false) @ApiParam(value = "表示顺序") Integer dispOrder,
            @RequestParam(required = false) @ApiParam(value = "删除标志") Integer delFlag,
            @RequestParam(required = false) @ApiParam(value = "页码") Long page,
            @RequestParam(required = false) @ApiParam(value = "限制条数") Long limit) {

        // 关联表
        QMenu qMenu = QMenu.menu;

        // 检索条件构造
        BooleanExpression whereExp = qMenu.id.eq(qMenu.id);
        if (id != null) {
            whereExp = whereExp.and(qMenu.id.eq(id));
        }
        if (StringUtils.isNotEmpty(name)) {
            whereExp = whereExp.and(qMenu.name.eq(name));
        }
        if (StringUtils.isNotEmpty(title)) {
            whereExp = whereExp.and(qMenu.title.eq(title));
        }
        if (StringUtils.isNotEmpty(icon)) {
            whereExp = whereExp.and(qMenu.icon.eq(icon));
        }
        if (StringUtils.isNotEmpty(jump)) {
            whereExp = whereExp.and(qMenu.jump.eq(jump));
        }
        if (dispOrder != null) {
            whereExp = whereExp.and(qMenu.dispOrder.eq(dispOrder));
        }
        if (delFlag != null) {
            whereExp = whereExp.and(qMenu.delFlag.eq(delFlag));
        }

        // 件数取得
        Long count = queryFactory.selectFrom(qMenu).where(whereExp).fetchCount();
        if (count == 0) {
            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("没有检索到任何数据").build();
        }

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(page, limit, count);

        // 数据检索
        List<Menu> data = queryFactory
                    .selectFrom(qMenu)
                    .where(whereExp)
                    .offset(pagination.getOffset())
                    .limit(pagination.getLimit())
                    .fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();
    }

    @PostMapping(value = "/save")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<Menu> save(@RequestBody ApiParams<Menu> params) {
        Menu target = null;

        // 新建
        if (params.getData().getId() == null) {
            target = params.getData();
            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            target.setCreatedBy(params.getUsername());
        // 更新
        } else {
            target = menuRepositoryDsl.getOne(params.getData().getId());
            target.setName(params.getData().getName());
            target.setTitle(params.getData().getTitle());
            target.setIcon(params.getData().getIcon());
            target.setJump(params.getData().getJump());
            target.setDispOrder(params.getData().getDispOrder());
            target.setUseFlag(params.getData().getUseFlag());
            target.setParentMenu(params.getData().getParentMenu());
            //target.setList(params.getData().getList());
            target.setRoleSet(params.getData().getRoleSet());
            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        target = menuRepositoryDsl.save(target);
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg(ApiResultCode.Success.msg())
                .data(target).build();
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {

        if (deleteParams.getData() == null) {
            return ApiResultBuilder.create()
                    .code(ApiResultCode.DataIllegality.code())
                    .msg(ApiResultCode.DataIllegality.msg()).build();
        }

        for (String id : deleteParams.getData()) {
            menuRepositoryDsl.deleteById(Long.parseLong(id));
        }
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
    }

    @GetMapping("init")
    @Transactional
    public ApiResult init() {

        Menu sysMenu = new Menu("user", "系统管理", "layui-icon-username", "", 9);
        sysMenu.addChildrenMenu(new Menu("administrators-dept", "部门管理", "", "user/administrators/dept", 1));
        sysMenu.addChildrenMenu(new Menu("administrators-list", "用户管理", "", "user/administrators/list", 2));
        sysMenu.addChildrenMenu(new Menu("administrators-role", "角色管理", "", "user/administrators/role", 3));
        sysMenu.addChildrenMenu(new Menu("administrators-menu", "菜单管理", "", "user/administrators/menu", 4));
        menuRepositoryDsl.save(sysMenu);

        Menu bizMenu1 = new Menu("bill", "对账单管理", "layui-icon-table", "", 1);
        bizMenu1.addChildrenMenu(new Menu("bill-list", "对账单下载", "", "bill/list", 1));

        menuRepositoryDsl.save(bizMenu1);

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("菜单初始化成功").build();
    }

    @GetMapping("getMenuJSON")
    @Transactional
    public ApiResult getMenuJSON() {

        QMenu qMenu = QMenu.menu;
        List<Menu> rootMenus = queryFactory.selectFrom(qMenu)
                .where(qMenu.parentMenu.isNull().and(qMenu.delFlag.eq(0)))
                .orderBy(qMenu.dispOrder.asc())
                .fetch();


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails) {
            PlatformUser platformUser = (PlatformUser) principal;
            List<PlatformRole> roles = new ArrayList<>();
            platformUser.getAuthorities().forEach(auth -> {
                roles.add((PlatformRole) auth);
            });
            List<Long> rolesIds = roles.stream().map(PlatformRole::getId).collect(Collectors.toList());

            // 第一级菜单
            rootMenus.forEach(menu -> {
                List<Long> menuRoleIds =  menu.getRoleSet().stream().map(PlatformRole::getId).collect(Collectors.toList());

                if (menuRoleIds.containsAll(rolesIds) || "admin".equals(platformUser.getUsername())) {
                    menu.setUseFlag(1);
                    // 第二级菜单
                    menu.getList().forEach(menu1 -> {
                        List<Long> menuRoleIds1 =  menu1.getRoleSet().stream().map(PlatformRole::getId).collect(Collectors.toList());
                        if (menuRoleIds1.containsAll(rolesIds) || "admin".equals(platformUser.getUsername())) {
                            menu1.setUseFlag(1);
                            // 第三级菜单
                            menu1.getList().forEach(menu2 -> {
                                List<Long> menuRoleIds2 =  menu2.getRoleSet().stream().map(PlatformRole::getId).collect(Collectors.toList());
                                if (menuRoleIds2.containsAll(rolesIds) || "admin".equals(platformUser.getUsername())) {
                                    menu2.setUseFlag(1);
                                } else {
                                    menu2.setUseFlag(0);
                                }
                            });
                        } else {
                            menu1.setUseFlag(0);
                        }
                    });
                 } else {
                    menu.setUseFlag(0);
                }

            });
        }

        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("成功").data(rootMenus).build();
    }


    @GetMapping(value = "getMenuTreeGridData")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult getMenuTreeGridData() {

        List<Map<String, Object>> result = Lists.newArrayList();

        QMenu qMenu = QMenu.menu;
        List<Menu> rootMenus = queryFactory.selectFrom(qMenu)
                //.where(qMenu.delFlag.eq(0))
                .orderBy(qMenu.dispOrder.asc())
                .fetch();

        for (Menu menu : rootMenus) {
            Map<String, Object> row = Maps.newLinkedHashMap();
            row.put("id", menu.getId());
            if (menu.getParentMenu() == null) {
                row.put("pid", 0);
            } else {
                row.put("pid", menu.getParentMenu().getId());
            }
            row.put("name", menu.getName());
            row.put("title", menu.getTitle());
            row.put("icon", menu.getIcon());
            row.put("jump", menu.getJump());
            row.put("dispOrder", menu.getDispOrder());
            row.put("delFlag", menu.getDelFlag());
            row.put("authorities", menu.getRoleSet());
            result.add(row);
        }

        return ApiResultBuilder.create().code(0).msg("成功返回").data(result).build();
    }



    @GetMapping(value = "getMenuTreeData")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<Map<String, Object>> getMenuTreeData() {

        List<Map<String, Object>> result = Lists.newArrayList();

        QMenu qMenu = QMenu.menu;
        List<Menu> rootMenus = queryFactory.selectFrom(qMenu)
                .where(qMenu.parentMenu.isNull().and(qMenu.delFlag.eq(0)))
                .orderBy(qMenu.dispOrder.asc())
                .fetch();

        Map<String, Object> rootMap = Maps.newLinkedHashMap();
        // 顶级菜单单独项目
        rootMap.put("id", "");
        rootMap.put("name", "顶级菜单");
        rootMap.put("open", true);
        rootMap.put("checked", false);
        result.add(rootMap);

        for (Menu rootMenu : rootMenus) {
            rootMap = Maps.newLinkedHashMap();
            // 第一层
            if (rootMenu != null) {
                rootMap.put("id", rootMenu.getId());
                rootMap.put("name", rootMenu.getTitle());
                rootMap.put("open", true);
                rootMap.put("checked", false);

                // 第二层
                if (!rootMenu.getList().isEmpty()) {
                    List<Map<String, Object>> rootChildren = new ArrayList<>();
                    rootMap.put("children", rootChildren);
                    for (Menu l2menu : rootMenu.getList()) {
                        Map<String, Object> leve12Map = Maps.newLinkedHashMap();
                        rootChildren.add(leve12Map);
                        leve12Map.put("id", l2menu.getId());
                        leve12Map.put("name", l2menu.getTitle());
                        leve12Map.put("open", true);
                        leve12Map.put("checked", false);

                        // 第三层
                        if (!l2menu.getList().isEmpty()) {
                            List<Map<String, Object>> level2Children = new ArrayList<>();
                            leve12Map.put("children", level2Children);
                            for (Menu l3menu : l2menu.getList()) {
                                Map<String, Object> leve13Map = Maps.newLinkedHashMap();
                                level2Children.add(leve13Map);
                                leve13Map.put("id", l3menu.getId());
                                leve13Map.put("name", l3menu.getTitle());
                                leve13Map.put("open", true);
                                leve13Map.put("checked", false);

                                // 第四层
                                if (!l3menu.getList().isEmpty()) {
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


    @PostMapping(value = "editMenu")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult editMenu(@RequestBody ApiParams<MenuSubmitParams> params) {

        Menu target = menuRepositoryDsl.getOne(Long.parseLong(params.getData().getId()));
        if (StringUtils.isNotEmpty(params.getData().getName())) target.setName(params.getData().getName());
        if (StringUtils.isNotEmpty(params.getData().getTitle())) target.setTitle(params.getData().getTitle());
        if (StringUtils.isNotEmpty(params.getData().getIcon())) target.setIcon(params.getData().getIcon());
        if (StringUtils.isNotEmpty(params.getData().getJump())) target.setJump(params.getData().getJump());
        if (params.getData().getDispOrder() != null) target.setDispOrder(params.getData().getDispOrder());
        target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        target.setUpdatedBy(params.getUsername());

        // 父子关系更新
        if (StringUtils.isNotEmpty(params.getData().getPid())) {
            target.setParentMenu(menuRepositoryDsl.getOne(Long.parseLong(params.getData().getPid())));
        } else {
            target.setParentMenu(null);
        }

        // 角色列表更新
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;
        Set<PlatformRole> platformRoles = new HashSet<>();
        Arrays.stream(params.getData().getPlatformRoles()).forEach(roleId -> {
            platformRoles.add(queryFactory.selectFrom(qPlatformRole).where(qPlatformRole.id.eq(Long.parseLong(roleId))).fetchOne());
        });
        target.setRoleSet(platformRoles);

        return ApiResultBuilder.create().code(0).msg("菜单编辑成功").build();
    }

    @PostMapping(value = "addMenu")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult addMenu(@RequestBody ApiParams<MenuSubmitParams> params) {

        Menu target = new Menu();
        if (StringUtils.isNotEmpty(params.getData().getName())) target.setName(params.getData().getName());
        if (StringUtils.isNotEmpty(params.getData().getTitle())) target.setTitle(params.getData().getTitle());
        if (StringUtils.isNotEmpty(params.getData().getIcon())) target.setIcon(params.getData().getIcon());
        if (StringUtils.isNotEmpty(params.getData().getJump())) target.setJump(params.getData().getJump());
        if (params.getData().getDispOrder() != null) target.setDispOrder(params.getData().getDispOrder());
        target.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        target.setCreatedBy(params.getUsername());
        target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        target.setUpdatedBy(params.getUsername());


        // 角色列表更新
        QPlatformRole qPlatformRole = QPlatformRole.platformRole;
        Set<PlatformRole> platformRoles = new HashSet<>();
        Arrays.stream(params.getData().getPlatformRoles()).forEach(roleId -> {
            platformRoles.add(queryFactory.selectFrom(qPlatformRole).where(qPlatformRole.id.eq(Long.parseLong(roleId))).fetchOne());
        });
        target.setRoleSet(platformRoles);


        // 父子关系更新
        if (StringUtils.isNotEmpty(params.getData().getPid())) {
            target.setParentMenu(menuRepositoryDsl.getOne(Long.parseLong(params.getData().getPid())));
        } else {
            target.setParentMenu(null);
        }

        menuRepositoryDsl.save(target); // 转化为持久对象

        return ApiResultBuilder.create().code(0).msg("菜单编辑成功").build();
    }

    @PostMapping(value = "logicDel/{id}/{delFlag}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult logicDelMenu(@PathVariable Long id, @PathVariable Integer delFlag) {
        Menu menu = menuRepositoryDsl.getOne(id);
        menu.setDelFlag(delFlag);
        return ApiResultBuilder.create().code(0).msg("状态更新成功").build();
    }

}
