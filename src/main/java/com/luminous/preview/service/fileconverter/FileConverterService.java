package com.luminous.preview.service.fileconverter;

import com.luminous.preview.domain.dto.TargetFileNature;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 10:49
 * @Description:
 */
public interface FileConverterService {

    TargetFileNature converter(String sourcePathFileName);

}
