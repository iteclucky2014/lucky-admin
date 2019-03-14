package com.lucky.admin.platform.controller;

import java.sql.Timestamp;
import java.util.List;

import com.lucky.admin.platform.domain.QPlatformDept;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.lucky.admin.platform.common.ApiPagination;
import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.ApiResultCode;
import com.lucky.admin.platform.common.FileStorageManager;
import com.lucky.admin.platform.domain.PlatformDept;
import com.lucky.admin.platform.domain.PlatformDevice;
import com.lucky.admin.platform.domain.PlatformUser;
import com.lucky.admin.platform.domain.QPlatformDevice;
import com.lucky.admin.platform.domain.QPlatformRole;
import com.lucky.admin.platform.domain.QPlatformUser;
import com.lucky.admin.platform.repository.PlatformDeptRepositoryDsl;
import io.swagger.annotations.Api;

@Api(value = "Device", tags = "平台部门信息管理")
@RestController
@RequestMapping(value = "/device")
@SuppressWarnings("unchecked")
public class PlatformDeviceController {

    @Autowired
    PlatformDeptRepositoryDsl platformDeptRepositoryDsl;

    @Autowired
    JPAQueryFactory queryFactory;
    
    @Autowired
    FileStorageManager fileStorageManager;
    
    @Value("${pdf.urlPrefix}")
    private String urlPrefix;

    @Value("${pdf.store.path}")
    private String dirPath;

    @GetMapping(value = "/page")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<List<PlatformDevice>> query(@RequestParam(required = false) Long belongRegion,
            @RequestParam(required = false) String code, @RequestParam(required = false) String standard,
            @RequestParam(required = false) String manuFacturer, @RequestParam(required = false) String installDate,
            @RequestParam(required = false) String installLocation, @RequestParam(required = false) String type,
            @RequestParam(required = false) String alarmRules, @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long limit) {

        QPlatformDevice qPlatformDevice = QPlatformDevice.platformDevice;
        QPlatformDept qPlatformDept = QPlatformDept.platformDept;

        BooleanExpression whereDev = qPlatformDevice.id.eq(qPlatformDevice.id);

        // 隶属分区
        if (belongRegion != null) {

            PlatformDept platformDept = queryFactory.selectFrom(qPlatformDept).where(qPlatformDept.id.eq(belongRegion))
                    .fetchOne();
            whereDev = whereDev.and(qPlatformDevice.belongRegion.eq(platformDept.getId()));

            for (PlatformDept depts : platformDept.getChildrenDeptList()) {
                whereDev = whereDev.or(qPlatformDevice.belongRegion.eq(depts.getId()));
                for (PlatformDept cDept : depts.getChildrenDeptList()) {
                    whereDev = whereDev.or(qPlatformDevice.belongRegion.eq(cDept.getId()));
                }
            }
        }

        // 编号
        if (StringUtils.isNotEmpty(code)) {
            whereDev = whereDev.and(qPlatformDevice.code.eq(code));
        }
        // 规格
        if (StringUtils.isNotEmpty(standard)) {
            whereDev = whereDev.and(qPlatformDevice.standard.eq(standard));
        }
        // 生产厂家
        if (StringUtils.isNotEmpty(manuFacturer)) {
            whereDev = whereDev.and(qPlatformDevice.manuFacturer.eq(manuFacturer));
        }
        // 类型
        if (StringUtils.isNotEmpty(type)) {
            whereDev = whereDev.and(qPlatformDevice.type.eq(type));
        }
        // 安装日期
        if (StringUtils.isNotEmpty(installDate)) {
            whereDev = whereDev.and(qPlatformDevice.installDate.between(Timestamp.valueOf(installDate + " 00:00:00"),
                    Timestamp.valueOf(installDate + " 23:59:59")));
        }
        // 安装位置
        if (StringUtils.isNotEmpty(installLocation)) {
            whereDev = whereDev.and(qPlatformDevice.installLocation.eq(installLocation));
        }
        // 报警规则
        if (StringUtils.isNotEmpty(alarmRules)) {
            whereDev = whereDev.and(qPlatformDevice.alarmRules.eq(alarmRules));
        }
        // 件数取得
        Long count = queryFactory.selectFrom(qPlatformDevice).where(whereDev).fetchCount();

        // 分页控制信息构造
        ApiPagination pagination = new ApiPagination(Long.valueOf(1), Long.valueOf(10), count);

        // 数据检索
        List<PlatformDevice> data = queryFactory.selectFrom(qPlatformDevice).where(whereDev)
                .offset(pagination.getOffset()).limit(pagination.getLimit()).fetch();

        // 分页数据返回
        return ApiResultBuilder.create().code(0).msg("成功返回").data(data).pagination(pagination).build();

    }
    
    @GetMapping(value = "reporter/page")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public ApiResult<List<PlatformUser>> query(
    		@RequestParam(required = false) String reporterType,
            @RequestParam(required = false) String statisticsTime,
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
        
        for (PlatformUser d : data) {
        	try {
    			PdfWriter writer = new PdfWriter(dirPath+"/"+d.getRealName()+".pdf");
    			PdfDocument pdf = new PdfDocument(writer);
    			Document document = new Document(pdf);
    			PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", true);// 解决显示中文字体
    			document.add(new Paragraph(d.getRealName()).setFont(font));
    			com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List().setSymbolIndent(12)
    					.setListSymbol("\u2022").setFont(font);
    			list.add(new ListItem("锄禾日当午"));
    			list.add(new ListItem("汗滴禾下土"));
    			list.add(new ListItem("谁知盘中餐"));
    			list.add(new ListItem("粒粒皆辛苦"));
    			document.add(list);
    			document.close();
    			d.setUrl(urlPrefix+"?fileName%3D"+d.getRealName()+".pdf");
    		} catch (Exception e) {
    			e.printStackTrace();
    			return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("生成报表失败！").data(data).build();
    		}
        }

        // 分页数据返回
        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg(data.size() == 0 ? "无数据" : "").data(data).build();
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        byte[] fileData = fileStorageManager.getFile(fileName);

        ResponseEntity<byte[]> picResp = new ResponseEntity<>(fileData, headers, HttpStatus.OK);
        return picResp;
    }

//    @PostMapping(value = "reporter/pdf")
//    @ResponseStatus(code = HttpStatus.OK)
//    @Transactional
//	public void reporter(@RequestBody ApiParams<Map<String, String>> apiParams, HttpServletRequest req, HttpServletResponse resp) {
//
//    	HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//		String fileName = apiParams.getUsername()+".pdf";
//		resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
//		try {
//			PdfWriter writer = new PdfWriter(resp.getOutputStream());
//			PdfDocument pdf = new PdfDocument(writer);
//			Document document = new Document(pdf);
//			PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", true);// 解决显示中文字体
//			document.add(new Paragraph("测试导出Pdf").setFont(font));
//			com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List().setSymbolIndent(12)
//					.setListSymbol("\u2022").setFont(font);
//			list.add(new ListItem("锄禾日当午"));
//			list.add(new ListItem("汗滴禾下土"));
//			list.add(new ListItem("谁知盘中餐"));
//			list.add(new ListItem("粒粒皆辛苦"));
//			document.add(list);
//			document.close();
//		} catch (IOException e) {
//			e.printStackTrace();
////			return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg("生成报表失败！").build();
//		}
//
//		// 分页数据返回
////		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).build();
//	}
}
