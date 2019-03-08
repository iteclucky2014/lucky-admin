package cn.sambo.difference.platform.gentools;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

@Component
public class DomainGenerator {

    private GenProperties genProperties;

    @Autowired
    public DomainGenerator(GenProperties genProperties) {
        this.genProperties = genProperties;
    }

    public void execute() throws Exception {

        File directory = new File(genProperties.getGenPath());// 参数为空
        String projectDir = directory.getCanonicalPath();
        File distDir = new File(projectDir, "difference-platform/src/main/java/cn/sambo/workflow/platform/domain");
        System.out.println(distDir);

        genProperties.getDomains().forEach(item -> {

            try{

                List<String> fileLineList = new ArrayList<>();

                String domainClassName = item.getName();

                fileLineList.add("package com.bill.platform.domain;");
                fileLineList.add("import com.fasterxml.jackson.annotation.JsonIgnore;");
                fileLineList.add("import com.fasterxml.jackson.annotation.JsonIgnoreProperties;");
                fileLineList.add("import org.hibernate.annotations.DynamicInsert;");
                fileLineList.add("import org.hibernate.annotations.DynamicUpdate;");
                fileLineList.add("");
                fileLineList.add("import javax.persistence.*;");
                fileLineList.add("import java.io.Serializable;");
                fileLineList.add("import java.util.*;");
                fileLineList.add("");
                fileLineList.add("@Entity");
                fileLineList.add("@DynamicUpdate(true)");
                fileLineList.add("@DynamicInsert(true)");
                fileLineList.add("@JsonIgnoreProperties(value = {\"handler\",\"hibernateLazyInitializer\",\"fieldHandler\"})");
                fileLineList.add("@Table(name = \"BIZ_" + domainClassName.toUpperCase() + "\")");
                fileLineList.add("public class " + domainClassName + " extends BaseEntity implements Serializable {");
                fileLineList.add("    private static final long serialVersionUID = 1L;");
                fileLineList.add("");

                List<String> domainFields = item.getFields();
                domainFields.forEach(fieldDef ->{
                    String[] fieldInfo = StringUtils.splitPreserveAllTokens(fieldDef, "|");
                    String comment = fieldInfo[0];
                    String fieldName = fieldInfo[1];
                    String fieldType = fieldInfo[2];
                    String relation = fieldInfo[3];
                    String mainTableFlag = fieldInfo[4];
                    fileLineList.add("    /** " + comment + " */");

                    if ("ManyToOne".equals(relation)) {
                        fileLineList.add("    @JsonIgnore");
                        fileLineList.add("    @ManyToOne");
                        fileLineList.add("    @JoinColumn(name = \""+fieldName+"_id\")");
                        fileLineList.add("    private " + fieldType + " " + fieldName + ";");
                    } else if ("OneToMany".equals(relation)) {
                        fileLineList.add("    @OneToMany(mappedBy = \"parent"+ domainClassName +"\", cascade = CascadeType.ALL, orphanRemoval = true)");
                        if (StringUtils.contains(fieldType, "Set")) {
                            fileLineList.add("    private " + fieldType + " " + fieldName + " = new TreeSet<>();");
                        } else {
                            fileLineList.add("    private " + fieldType + " " + fieldName + " = new ArrayList<>();");
                        }

                    } else if("ManyToMany".equals(relation)) {
                        if ("main".equals(mainTableFlag)) {
                            String mainTableName = domainClassName;
                            String slaveTableName = fieldName.replace("List", "");
                            fileLineList.add("    @ManyToMany(fetch = FetchType.EAGER)");
                            fileLineList.add("    @JoinTable(");
                            fileLineList.add("            name=\"" + mainTableName + "_" + slaveTableName + "\",");
                            fileLineList.add("            joinColumns=@JoinColumn(name=\"" + mainTableName + "_ID" + "\", referencedColumnName=\"ID\"),");
                            fileLineList.add("            inverseJoinColumns=@JoinColumn(name=\"" + slaveTableName + "_ID" + "\", referencedColumnName=\"ID\"))");
                            if (StringUtils.contains(fieldType, "Set")) {
                                fileLineList.add("    private " + fieldType + " " + fieldName + " = new TreeSet<>();");
                            } else {
                                fileLineList.add("    private " + fieldType + " " + fieldName + " = new ArrayList<>();");
                            }
                        } else {
                            fileLineList.add("    @JsonIgnore");
                            fileLineList.add("    @ManyToMany(mappedBy=\"" + WordUtils.uncapitalize(domainClassName) + "List" + "\", fetch = FetchType.LAZY)");
                            if (StringUtils.contains(fieldType, "Set")) {
                                fileLineList.add("    private " + fieldType + " " + fieldName + " = new TreeSet<>();");
                            } else {
                                fileLineList.add("    private " + fieldType + " " + fieldName + " = new ArrayList<>();");
                            }
                        }
                    } else if ("OneToOne".equals(relation)) {
                        if ("main".equals(mainTableFlag)) {
                            fileLineList.add("    @OneToOne(mappedBy = \""+WordUtils.uncapitalize(domainClassName)+"\", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)");
                            fileLineList.add("    private " + fieldType + " " + fieldName + ";");
                        } else {
                            fileLineList.add("    @JsonIgnore");
                            fileLineList.add("    @OneToOne(fetch = FetchType.LAZY)");
                            fileLineList.add("    @JoinColumn(name = \""+fieldName+"_id\")");
                            fileLineList.add("    private " + fieldType + " " + fieldName + ";");
                        }
                    } else {
                        fileLineList.add("    private " + fieldType + " " + fieldName + ";");
                    }
                });
                domainFields.forEach(fieldDef ->{
                    String[] fieldInfo = StringUtils.splitPreserveAllTokens(fieldDef, "|");
                    String comment = fieldInfo[0];
                    String fieldName = fieldInfo[1];
                    String fieldType = fieldInfo[2];
                    String relation = fieldInfo[3];

                    // GET方法
                    fileLineList.add("    /**");
                    fileLineList.add("     * 获取 " + comment);
                    fileLineList.add("     *");
                    fileLineList.add("     * @return "+fieldName+" "+comment);
                    fileLineList.add("     */");
                    fileLineList.add("    public "+fieldType+" get"+StringUtils.capitalize(fieldName)+"() {");
                    fileLineList.add("        return this."+fieldName+";");
                    fileLineList.add("    }");
                    fileLineList.add("");

                    // SET方法
                    fileLineList.add("    /**");
                    fileLineList.add("     * 设置 " + comment);
                    fileLineList.add("     *");
                    fileLineList.add("     * @param "+fieldName+" "+comment);
                    fileLineList.add("     */");
                    fileLineList.add("    public void set"+StringUtils.capitalize(fieldName)+"("+fieldType+" "+fieldName+") {");
                    fileLineList.add("        this."+fieldName+" = "+fieldName+";");
                    fileLineList.add("    }");
                    fileLineList.add("");
                });
                fileLineList.add("}");

                File distFile = new File(distDir, domainClassName + ".java");
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

                System.out.println("----> " + domainClassName + " 完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
