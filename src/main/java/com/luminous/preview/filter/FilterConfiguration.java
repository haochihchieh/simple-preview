package com.luminous.preview.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: haochihchieh
 * @Date: 2019/10/29 13:36
 * @Description:
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean getChinesePathFilter() {
        SetBaseUrlFilter filter = new SetBaseUrlFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        return registrationBean;
    }
}
