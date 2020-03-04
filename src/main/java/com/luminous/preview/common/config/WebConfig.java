package com.luminous.preview.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/23 18:03
 * @Description:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${converter.target}")
    private String target;

    /**
     * 添加访问外部文件配置
     * 为啥子要要添加这个呢？是为了访问 转换后的 .html静态文件、图片等。
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath" +
                ":/static/", "file:" + target + "/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
