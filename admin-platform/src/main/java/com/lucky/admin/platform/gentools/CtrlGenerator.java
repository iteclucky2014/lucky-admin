package com.lucky.admin.platform.gentools;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CtrlGenerator {
    private GenProperties genProperties;

    @Autowired
    public CtrlGenerator(GenProperties genProperties) {
        this.genProperties = genProperties;
    }

    public void execute() throws Exception {
        File directory = new File(genProperties.getGenPath());// 参数为空
        String projectDir = directory.getCanonicalPath();
        File distDir = new File(projectDir, "difference-platform/src/main/java/cn/sambo/workflow/platform/controller");
        System.out.println(distDir);

        System.out.println("开始生成");

        String[] types = {"Long", "String"};
        List typeList = Arrays.asList(types);

        genProperties.getControllers().forEach(item -> {
            try {
                String domainName = item.getDomain();
                GenProperties.DomainConf domainConf = genProperties.findDomian(domainName);
                String repoName = item.getRepository();
                String ctrlName = item.getName();

                List<String> fileLineList = new ArrayList<>();
                fileLineList.add("package com.bill.platform.controller;");
                fileLineList.add("");
                fileLineList.add("import com.querydsl.core.types.dsl.BooleanExpression;");
                fileLineList.add("import com.querydsl.jpa.impl.JPAQueryFactory;");
                fileLineList.add("import com.bill.platform.common.*;");
                fileLineList.add("import com.bill.platform.domain.*;");
                fileLineList.add("import com.bill.platform.enmu.*;");
                fileLineList.add("import com.bill.platform.repository.*;");
                fileLineList.add("import io.swagger.annotations.Api;");
                fileLineList.add("import io.swagger.annotations.ApiOperation;");
                fileLineList.add("import io.swagger.annotations.ApiParam;");
                fileLineList.add("import org.apache.commons.lang.StringUtils;");
                fileLineList.add("import org.springframework.beans.factory.annotation.Autowired;");
                fileLineList.add("import org.springframework.http.HttpStatus;");
                fileLineList.add("import org.springframework.transaction.annotation.Transactional;");
                fileLineList.add("import org.springframework.web.bind.annotation.*;");
                fileLineList.add("import java.sql.Timestamp;");
                fileLineList.add("import java.util.*;");
                fileLineList.add("");
                fileLineList.add("@Api(value = \""+WordUtils.uncapitalize(domainName)+"\", tags = \"\")");
                fileLineList.add("@RestController");
                fileLineList.add("@RequestMapping(value = \"/"+ WordUtils.uncapitalize(domainName)+"\")");
                fileLineList.add("@SuppressWarnings(\"unchecked\")");
                fileLineList.add("public class "+ctrlName+" {");
                fileLineList.add("");
                fileLineList.add("    @Autowired");
                fileLineList.add("    "+repoName+" "+WordUtils.uncapitalize(repoName)+";");
                fileLineList.add("");
                fileLineList.add("    @Autowired");
                fileLineList.add("    JPAQueryFactory queryFactory;");
                fileLineList.add("");


                // 分页检索方法生成
                fileLineList.add("    @ApiOperation(value = \"查询\",");
                fileLineList.add("            notes = \"支持分页查询，默认返回10条记录\")");
                fileLineList.add("    @GetMapping(value = \"/query\")");
                fileLineList.add("    @ResponseStatus(code = HttpStatus.OK)");
                fileLineList.add("    public ApiResult<List<"+domainName+">> query(");
                fileLineList.add("            @RequestParam(required = false) Long id,");

                List<String> domainFields = domainConf.getFields();
                domainFields.forEach(fieldDef ->{
                    String[] fieldInfo = StringUtils.splitPreserveAllTokens(fieldDef, "|");
                    String comment = fieldInfo[0];
                    String fieldName = fieldInfo[1];
                    String fieldType = fieldInfo[2];
                    String relation = fieldInfo[3];
                    String mainTableFlag = fieldInfo[4];

                    if (StringUtils.isEmpty(relation)) {
                        fileLineList.add("            @RequestParam(required = false) @ApiParam(value = \""+comment+"\") "+fieldType+" "+fieldName+",");
                    }

                });

                fileLineList.add("            @RequestParam(required = false) @ApiParam(value = \"页码\") Long page,");
                fileLineList.add("            @RequestParam(required = false) @ApiParam(value = \"限制条数\") Long limit) {");
                fileLineList.add("");
                fileLineList.add("        // 关联表");
                fileLineList.add("        Q"+domainName+" q"+domainName+" = Q"+domainName+"."+WordUtils.uncapitalize(domainName)+";");
                fileLineList.add("");
                fileLineList.add("        // 检索条件构造");
                fileLineList.add("        BooleanExpression whereExp = q"+domainName+".id.eq(q"+domainName+".id);");
                fileLineList.add("        if (id != null) {");
                fileLineList.add("            whereExp = whereExp.and(q"+domainName+".id.eq(id));");
                fileLineList.add("        }");

                domainFields.forEach(fieldDef ->{
                    String[] fieldInfo = StringUtils.splitPreserveAllTokens(fieldDef, "|");
                    String comment = fieldInfo[0];
                    String fieldName = fieldInfo[1];
                    String fieldType = fieldInfo[2];
                    String relation = fieldInfo[3];
                    String mainTableFlag = fieldInfo[4];

                    if (StringUtils.isEmpty(relation)) {

                        if ("Long".equals(fieldType)
                                || "Integer".equals(fieldType)
                                || "Double".equals(fieldType)
                                || "Float".equals(fieldType)
                                || "Short".equals(fieldType)
                                || "Byte".equals(fieldType)) {
                            fileLineList.add("        if ("+fieldName+" != null) {");
                            fileLineList.add("            whereExp = whereExp.and(q"+domainName+"."+fieldName+".eq("+fieldName+"));");
                            fileLineList.add("        }");
                        } else if ("String".equals(fieldType)) {
                            fileLineList.add("        if (StringUtils.isNotEmpty("+fieldName+")) {");
                            fileLineList.add("            whereExp = whereExp.and(q"+domainName+"."+fieldName+".eq("+fieldName+"));");
                            fileLineList.add("        }");
                        }
                    }

                });

                fileLineList.add("");
                fileLineList.add("        // 件数取得");
                fileLineList.add("        Long count = queryFactory.selectFrom(q"+domainName+").where(whereExp).fetchCount();");
                fileLineList.add("        if (count == 0) {");
                fileLineList.add("            return ApiResultBuilder.create().code(ApiResultCode.Fail.code()).msg(\"没有检索到任何数据\").build();");
                fileLineList.add("        }");
                fileLineList.add("");
                fileLineList.add("        // 分页控制信息构造");
                fileLineList.add("        ApiPagination pagination = new ApiPagination(page, limit, count);");
                fileLineList.add("");
                fileLineList.add("        // 数据检索");
                fileLineList.add("        List<"+domainName+"> data = queryFactory");
                fileLineList.add("                    .selectFrom(q"+domainName+")");
                fileLineList.add("                    .where(whereExp)");
                fileLineList.add("                    .offset(pagination.getOffset())");
                fileLineList.add("                    .limit(pagination.getLimit())");
                fileLineList.add("                    .fetch();");
                fileLineList.add("");
                fileLineList.add("        // 分页数据返回");
                fileLineList.add("        return ApiResultBuilder.create().code(0).msg(\"成功返回\").data(data).pagination(pagination).build();");
                fileLineList.add("    }");
                fileLineList.add("");


                // 插入/更新方法生成
                fileLineList.add("    @PostMapping(value = \"/save\")");
                fileLineList.add("    @ResponseStatus(code = HttpStatus.OK)");
                fileLineList.add("    @Transactional");
                fileLineList.add("    public ApiResult<"+domainName+"> save(@RequestBody ApiParams<"+domainName+"> params) {");
                fileLineList.add("        "+domainName+" target = null;");
                //fileLineList.add("        Q"+domainName+" q"+domainName+" = Q"+domainName+"."+WordUtils.uncapitalize(domainName)+";");
                fileLineList.add("");
                fileLineList.add("        // 新建");
                fileLineList.add("        if (params.getData().getId() == null) {");
                fileLineList.add("            target = params.getData();");
                fileLineList.add("            target.setCreatedTime(new Timestamp(System.currentTimeMillis()));");
                fileLineList.add("            target.setCreatedBy(params.getUsername());");
                fileLineList.add("        // 更新");
                fileLineList.add("        } else {");
                fileLineList.add("            target = "+WordUtils.uncapitalize(repoName)+".getOne(params.getData().getId());");

                domainFields.forEach(fieldDef ->{
                    String[] fieldInfo = StringUtils.splitPreserveAllTokens(fieldDef, "|");
                    String comment = fieldInfo[0];
                    String fieldName = fieldInfo[1];
                    String fieldType = fieldInfo[2];
                    String relation = fieldInfo[3];
                    String mainTableFlag = fieldInfo[4];

                    fileLineList.add("            target.set"+WordUtils.capitalize(fieldName)+"(params.getData().get"+WordUtils.capitalize(fieldName)+"());");

                });


                fileLineList.add("            target.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));");
                fileLineList.add("        }");
                fileLineList.add("        target = "+WordUtils.uncapitalize(repoName)+".save(target);");
                fileLineList.add("        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg(ApiResultCode.Success.msg())");
                fileLineList.add("                .data(target).build();");
                fileLineList.add("    }");
                fileLineList.add("");

                // 删除方法
                fileLineList.add("    @PostMapping(value = \"/delete\")");
                fileLineList.add("    @ResponseStatus(code = HttpStatus.OK)");
                fileLineList.add("    @Transactional");
                fileLineList.add("    public ApiResult delete(@RequestBody ApiParams<List<String>> deleteParams) {");
                fileLineList.add("");
                fileLineList.add("        if (deleteParams.getData() == null) {");
                fileLineList.add("            return ApiResultBuilder.create()");
                fileLineList.add("                    .code(ApiResultCode.DataIllegality.code())");
                fileLineList.add("                    .msg(ApiResultCode.DataIllegality.msg()).build();");
                fileLineList.add("        }");
                fileLineList.add("");
                fileLineList.add("        for (String id : deleteParams.getData()) {");
                fileLineList.add("            "+WordUtils.uncapitalize(repoName)+".deleteById(Long.parseLong(id));");
                fileLineList.add("        }");
                fileLineList.add("        return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg(\"删除成功\").build();");
                fileLineList.add("    }");
                fileLineList.add("");
                fileLineList.add("}");
                fileLineList.add("");

                File distFile = new File(distDir, ctrlName + ".java");
                if (distFile.exists()) {
                    distFile.delete();
                }
                RandomAccessFile aFile = new RandomAccessFile(distFile, "rw");
                FileChannel fileChannel = aFile.getChannel();

                byte[] bytes = StringUtils.join(fileLineList, '\n').getBytes("UTF-8");

                ByteBuffer buf= ByteBuffer.allocate(bytes.length);
                buf.clear();

                buf.put(bytes);
                buf.flip();
                while (buf.hasRemaining()) {
                    fileChannel.write(buf);
                }

                fileChannel.close();

                System.out.println("----> " + ctrlName + " 完成");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
