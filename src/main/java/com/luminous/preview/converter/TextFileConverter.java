package com.luminous.preview.converter;

import com.luminous.preview.common.utils.FileCharsetDetector;
import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.common.utils.SHAUtil;
import com.luminous.preview.common.utils.UUIDUtil;
import com.luminous.preview.domain.dto.TargetFileNature;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.mozilla.intl.chardet.nsPSMDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/21 16:34
 * @Description: 文档文件转换
 */

@Slf4j
@Component
public class TextFileConverter {

    @Value("${converter.temp}")
    private String temp;

    @Value("${converter.target}")
    private String target;


    @Autowired(required = false)
    private DocumentConverter converter;

    /**
     * txt文件转换后更改文件编码格式为utf-8
     *
     * @param sourcePathFileName
     */

    public TargetFileNature conventer(String sourcePathFileName) {

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
                String utf8PathFileName =
                        temp + File.separator + UUIDUtil.getUUID() + "-utf8.txt";
                try {
                    //获得文件编码
                    FileCharsetDetector.Observer oCharset = FileCharsetDetector.guessFileEncoding(
                            sourceFile, nsPSMDetector.CHINESE);
                    String charset = oCharset.getEncoding();
                    // 纯文本一律先转化为UTF-8txt文件再进行转码
                    // 将文本文件按照utf-8编码复制到临时目录中
                    FileUtil.copyFile(sourcePathFileName, charset, utf8PathFileName, "UTF-8");
                    //转换text文件为html文件，存在目标目录中
                    File utf8File = new File(utf8PathFileName);
                    String targetPathFileName =
                            targetDir + File.separator + pureName + ".html";
                    File targetFile = new File(targetPathFileName);
                    converter.convert(utf8File).to(targetFile).execute();
                    TargetFileNature targetFileNature =
                            new TargetFileNature();
                    targetFileNature.setPathFileName(targetPathFileName)
                            .setFileBytes(targetFile.length()).setFileExtension(extension);
                    log.info("文本转换任务成功[text conversion task finish]");
                    return targetFileNature;
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw new RuntimeException(ex);
                } finally {
                    File utf8File = new File(utf8PathFileName);
                    if (utf8File.exists()) {
                        utf8File.delete();
                    }
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
