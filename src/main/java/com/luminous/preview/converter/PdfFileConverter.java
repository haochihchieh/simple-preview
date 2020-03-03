package com.luminous.preview.converter;

import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.common.utils.SHAUtil;
import com.luminous.preview.domain.dto.TargetFileNature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/22 10:23
 * @Description: pdf文件转换
 */

@Component
@Slf4j
public class PdfFileConverter {

    @Value("${converter.target}")
    private String target;

    /**
     * pdf文件转换后格式不变，改变存储目录为 hash码目录/源文件
     *
     * @param sourcePathFileName
     */
    public TargetFileNature conventer(String sourcePathFileName) {

        File sourceFile = new File(sourcePathFileName);
        if (sourceFile.exists()) {
            //获取文件的hashCode
            String hashCode = SHAUtil.SHAHashCode(sourcePathFileName);
            //创建target目录
            String targetDir = target + File.separator + hashCode;
            File hashDir = FileUtil.createDir(targetDir);
            if (hashDir.exists() && hashDir.isDirectory()) {
                //将源文件复制到 转换资源文件夹 中,名称不变
                FileUtil.copyFile(sourceFile.getPath(), targetDir);
                String targetPathFileName =
                        target + File.separator + hashCode + File.separator + sourceFile.getName();
                TargetFileNature targetFileNature =
                        new TargetFileNature();
                long bytes = sourceFile.length();
                targetFileNature.setPathFileName(targetPathFileName)
                        .setFileBytes(bytes).setFileExtension("pdf");
                log.info("pdf文件复制成功[pdf file copy finish]");
                return targetFileNature;
            } else {
                log.warn("hash 目录不存在！[target dir is not exist]");
                throw new RuntimeException("hash dir is not exist!");
            }
        } else {
            log.warn("源文件不存在！[source file is not exist]");
            throw new RuntimeException("source file is not exist!");
        }
    }

}
