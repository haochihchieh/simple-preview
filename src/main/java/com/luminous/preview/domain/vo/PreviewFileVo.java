package com.luminous.preview.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/22 9:39
 * @Description: 预览文件类
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PreviewFileVo implements Serializable {

    @NotBlank
    private String originalUrl;

    @NotBlank
    private String pathFileName;

    @NotNull
    private long fileBytes;

    @NotBlank
    private String fileExtension;
}
