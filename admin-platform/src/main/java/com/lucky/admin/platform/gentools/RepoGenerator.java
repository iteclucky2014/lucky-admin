package com.lucky.admin.platform.gentools;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Component
public class RepoGenerator {

    private GenProperties genProperties;

    @Autowired
    public RepoGenerator(GenProperties genProperties) {
        this.genProperties = genProperties;
    }

    public void execute() throws Exception {
        File directory = new File(genProperties.getGenPath());// 参数为空
        String projectDir = directory.getCanonicalPath();
        File distDir = new File(projectDir, "difference-platform/src/main/java/cn/sambo/workflow/platform/repository");
        System.out.println(distDir);

        System.out.println("开始生成");

        genProperties.getRepositorys().forEach(item -> {
            try {
                String domianClassName = item.getDomain();
                String pkType = item.getPkType();

                String repoClassName = domianClassName + "RepositoryDsl";
                if (StringUtils.isNotEmpty(item.getName())) {
                    repoClassName = item.getName();
                }

                String[] strings = {
                        "package com.bill.platform.repository;",
                        "",
                        "import com.bill.platform.domain."+ domianClassName + ";",
                        "import org.springframework.data.jpa.repository.JpaRepository;",
                        "import org.springframework.data.querydsl.QuerydslPredicateExecutor;",
                        "",
                        "public interface "+ repoClassName +" extends JpaRepository<" + domianClassName + ", " + pkType + ">,QuerydslPredicateExecutor<" + domianClassName + "> {",
                        "}"
                };

                File distFile = new File(distDir, repoClassName + ".java");
                if (distFile.exists()) {
                    distFile.delete();
                }
                RandomAccessFile aFile = new RandomAccessFile(distFile, "rw");
                FileChannel fileChannel = aFile.getChannel();

                byte[] bytes = StringUtils.join(strings, '\n').getBytes("UTF-8");

                ByteBuffer buf= ByteBuffer.allocate(bytes.length);
                buf.clear();

                buf.put(bytes);
                buf.flip();
                while (buf.hasRemaining()) {
                    fileChannel.write(buf);
                }

                fileChannel.close();

                System.out.println("----> " + repoClassName + " 完成");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("生成结束");
    }

}
