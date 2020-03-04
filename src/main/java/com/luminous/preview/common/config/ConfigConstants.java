package com.luminous.preview.common.config;

import org.springframework.stereotype.Component;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/29 13:36
 * @Description: 当前访问页面的url
 */

@Component
public class ConfigConstants {

    private static String baseUrl;

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        // 不以'/'结尾的，加上'/'
        ConfigConstants.baseUrl = baseUrl.concat("/");
    }

}
