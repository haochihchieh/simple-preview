package com.luminous.preview.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/22 9:09
 * @Description:
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RedisVo {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

}
