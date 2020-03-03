package com.luminous.preview.service.fileconverter.impl;

import com.luminous.preview.common.utils.FileUtil;
import com.luminous.preview.converter.OfficeFileConverter;
import com.luminous.preview.converter.PdfFileConverter;
import com.luminous.preview.domain.dto.TargetFileNature;
import com.luminous.preview.converter.TextFileConverter;
import com.luminous.preview.service.fileconverter.FileConverterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/22 10:49
 * @Description:
 */

@Service
@Slf4j
public class FileConverterServiceImpl implements FileConverterService {

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

    @Autowired(required = false)
    private OfficeFileConverter officeFileConverter;

    @Autowired(required = false)
    private PdfFileConverter pdfFileConverter;

    @Autowired(required = false)
    private TextFileConverter textFileConverter;

    @Override
    public TargetFileNature converter(String sourcePathFileName) {
        if (sourcePathFileName == null && StringUtils.isBlank(sourcePathFileName)) {
            throw new RuntimeException("source path filename is empty!");
        }
        try {
            log.info("开始进行文件格式转换：" + sourcePathFileName);
            String extension = FileUtil.getFileExtension(sourcePathFileName);
            if (OFFICE_WORD_TYPE.contains(extension.toLowerCase())
                    || OFFICE_PPT_TYPE.contains(extension.toLowerCase())) {
                //word ppt 文件转换成pdf
                return officeFileConverter.converterToPdf(sourcePathFileName);
            } else if (OFFICE_EXCEL_TYPE.contains(extension.toLowerCase())) {
                //excel 文件转成html文件
                return officeFileConverter.converterToHtml(sourcePathFileName);
            } else if (PDF_TYPE.contains(extension.toLowerCase())) {
                return pdfFileConverter.conventer(sourcePathFileName);
            } else if (TEXT_TYPE.contains(extension.toLowerCase())) {
                return textFileConverter.conventer(sourcePathFileName);
            } else {
                throw new RuntimeException("The [" + extension + "] type " +
                        "file's conversion is not supported ");
            }
        } catch (Exception ex) {
            log.error("文件转换失败" + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
