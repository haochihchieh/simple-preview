package com.luminous.preview.converter;

import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.common.utils.SHAUtil;
import com.luminous.preview.domain.dto.TargetFileNature;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/22 8:44
 * @Description: office文件转换 word/excel/ppt
 */

@Component
@Slf4j
public class OfficeFileConverter {

    @Value("${converter.target}")
    private String target;

    @Autowired(required = false)
    private DocumentConverter converter;

    /**
     * Excel文件 转换成html文件
     *
     * @param sourcePathFileName
     */
    public TargetFileNature converterToHtml(String sourcePathFileName) {
        File sourceFile = new File(sourcePathFileName);

        if (sourceFile.exists()) {
            String sourceFileName = sourceFile.getName();
            String pureName = sourceFileName.substring(0,
                    sourceFileName.lastIndexOf('.'));
            String extension =
                    sourceFileName.substring(sourceFileName.lastIndexOf('.') + 1);
            //获取文件的hashCode
            String hashCode = SHAUtil.SHAHashCode(sourcePathFileName);
            //创建target目录
            String targetDir = target + File.separator + hashCode;
            File hashDir = FileUtil.createDir(targetDir);
            if (hashDir.exists() && hashDir.isDirectory()) {
                try {
                    String targetPathFileName =
                            targetDir + File.separator + pureName + ".html";
                    File targetFile = new File(targetPathFileName);
                    converter.convert(sourceFile).to(targetFile).execute();
                    TargetFileNature targetFileNature =
                            new TargetFileNature();
                    long bytes = targetFile.length();
                    targetFileNature.setPathFileName(targetPathFileName)
                            .setFileBytes(bytes).setFileExtension(extension);
                    return targetFileNature;
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw new RuntimeException(ex);
                } finally {
                    log.info("Excel转换任务成功[excel conversion task finish]");
                }
            } else {
                log.warn("hash目录不存在！[hash dir is not exist]");
                throw new RuntimeException("hash dir is not exist!");
            }
        } else {
            log.warn("源文件不存在！[source file is not exist]");
            throw new RuntimeException("source file is not exist!");
        }
    }

    /**
     * word、ppt文件转换成pdf文件
     *
     * @param sourcePathFileName
     */
    public TargetFileNature converterToPdf(String sourcePathFileName) {
        File sourceFile = new File(sourcePathFileName);
        if (sourceFile.exists()) {
            String sourceFileName = sourceFile.getName();
            String pureName = sourceFileName.substring(0,
                    sourceFileName.lastIndexOf('.'));
            String extension =
                    sourceFileName.substring(sourceFileName.lastIndexOf('.') + 1);
            //获取文件的hashCode
            String hashCode = SHAUtil.SHAHashCode(sourcePathFileName);
            //创建target目录
            String targetDir = target + File.separator + hashCode;
            File hashDir = FileUtil.createDir(targetDir);
            if (hashDir.exists() && hashDir.isDirectory()) {
                try {
                    String targetPathFileName =
                            targetDir + File.separator + pureName + ".pdf";
                    File targetFile = new File(targetPathFileName);
                    converter.convert(sourceFile).to(targetFile).execute();
                    TargetFileNature targetFileNature =
                            new TargetFileNature();
                    long bytes = targetFile.length();
                    targetFileNature.setPathFileName(targetPathFileName)
                            .setFileBytes(bytes).setFileExtension(extension);
                    log.info("Word/Pdf转换任务成功[word/pdf conversion task " +
                            "finish]");
                    return targetFileNature;
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw new RuntimeException(ex);
                }
            } else {
                log.warn("hash 目录不存在！[hash dir is not exist]");
                throw new RuntimeException("hash dir is not exist!");
            }
        } else {
            log.warn("源文件不存在！[source file is not exist]");
            throw new RuntimeException("source file is not exist!");
        }
    }
}
