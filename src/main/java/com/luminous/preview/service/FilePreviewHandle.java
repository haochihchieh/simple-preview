package com.luminous.preview.service;

import com.luminous.preview.domain.dto.TargetFileNature;
import com.luminous.preview.domain.vo.PreviewFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/23 19:08
 * @Description: 文件预览处理
 */


@Component
@Slf4j
public class FilePreviewHandle {

    @Value("${converter.target}")
    private String target;

    /*word文件格式*/
    private static String OFFICE_WORD_TYPE = "doc|docx";
    /*excel文件格式*/
    private static String OFFICE_EXCEL_TYPE = "xls|xlsx";
    /*ppt文件格式*/
    private static String OFFICE_PPT_TYPE = "ppt|pptx";
    /*pdf文件格式*/
    private static String PDF_TYPE = "pdf";
    /*文本文件格式*/
    private static String TEXT_TYPE = "txt|sql|java|js|html|xml|html|css|cs";

    /**
     * 去除target路径并 格式化转义反斜杠
     *
     * @param pathFileName
     * @return
     */
    private String subtractTargetAndFormat(String pathFileName) {
        String finalUrl =
                pathFileName.substring(target.length()).replaceAll("\\\\", "/");
        return finalUrl;
    }

    public String charge(TargetFileNature targetFileNature, Model model) {
        String extension = targetFileNature.getFileExtension();
        if (OFFICE_WORD_TYPE.contains(extension.toLowerCase())
                || OFFICE_PPT_TYPE.contains(extension.toLowerCase())
                || PDF_TYPE.contains(extension.toLowerCase())) {
            //word ppt pdf 格式都转换成pdf显示
            if (targetFileNature.getPathFileName().startsWith(target)) {
                String ordinaryUrl =
                        subtractTargetAndFormat(targetFileNature.getPathFileName());
                model.addAttribute("ordinaryUrl", ordinaryUrl);
                log.info("Target方式预览【" + targetFileNature.getPathFileName() +
                        "】/pdf");
                return "pdf";
            }
            throw new RuntimeException("file location error");
        } else if (OFFICE_EXCEL_TYPE.contains(extension.toLowerCase())
                || TEXT_TYPE.contains(extension.toLowerCase())) {
            //excel text文本 格式都转换成html显示
            if (targetFileNature.getPathFileName().startsWith(target)) {
                String ordinaryUrl =
                        subtractTargetAndFormat(targetFileNature.getPathFileName());
                model.addAttribute("ordinaryUrl", ordinaryUrl);
                log.info("Target方式预览【" + targetFileNature.getPathFileName() +
                        "】/html");
                return "html";
            }
            throw new RuntimeException("file location error");

        } else {
            throw new RuntimeException("The [" + extension + "] type " +
                    "file's conversion is not supported ");
        }
    }

    public String charge(PreviewFileVo previewFileVo, Model model) {
        String extension = previewFileVo.getFileExtension();
        if (OFFICE_WORD_TYPE.contains(extension.toLowerCase())
                || OFFICE_PPT_TYPE.contains(extension.toLowerCase())
                || PDF_TYPE.contains(extension.toLowerCase())) {
            //word ppt pdf 格式都转换成pdf显示
            if (previewFileVo.getPathFileName().startsWith(target)) {
                String ordinaryUrl =
                        subtractTargetAndFormat(previewFileVo.getPathFileName());
                model.addAttribute("ordinaryUrl", ordinaryUrl);
                log.info("Preview方式预览【" + previewFileVo.getPathFileName() +
                        "】/pdf");
                return "pdf";
            }
            throw new RuntimeException("file location error");

        } else if (OFFICE_EXCEL_TYPE.contains(extension.toLowerCase())
                || TEXT_TYPE.contains(extension.toLowerCase())) {
            //excel text文本 格式都转换成html显示
            if (previewFileVo.getPathFileName().startsWith(target)) {
                String ordinaryUrl =
                        subtractTargetAndFormat(previewFileVo.getPathFileName());
                model.addAttribute("ordinaryUrl", ordinaryUrl);
                log.info("Preview方式预览【" + previewFileVo.getPathFileName() +
                        "】/html");
                return "html";
            }
            throw new RuntimeException("file location error");

        } else {
            log.warn("预览进行失败！【" + extension + "】暂不支持。");
            throw new RuntimeException("The [" + extension + "] type " +
                    "file's conversion is not supported ");
        }
    }
}
