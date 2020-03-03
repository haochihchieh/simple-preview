package com.luminous.preview.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: haozhijie
 * @Date: 2019/10/23 18:46
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAttribute {

    private String fileType;

    private String fileExtension;

    private String name;

    private String url;

    private String decodedUrl;

}
