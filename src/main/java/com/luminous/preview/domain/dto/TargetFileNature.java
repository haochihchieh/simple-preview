package com.luminous.preview.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 13:57
 * @Description: 转换后文件实体类
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class TargetFileNature {

    /**
     * 转化后文件路径名称
     */
    private String pathFileName;

    /**
     * 文件字节大小
     */
    private long fileBytes;

    /**
     * 转换后文件格式
     */
    private String fileExtension;

}
