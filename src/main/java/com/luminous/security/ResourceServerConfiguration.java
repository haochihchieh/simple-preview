package com.luminous.security;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
 /**
    * 资源服务器的配置
 　　* @author 郝江波
 　　* @date 2020/3/25 17:55
 　　*/
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private RemoteTokenServices remoteTokenServices;

    @Value("${security.enable}")
    private boolean enableSecurity;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)  {
        //resources.resourceId("order-server");
        resources
                .tokenServices(remoteTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String antPatterns = "/**";
        if(enableSecurity){
            antPatterns ="/version";
        }
        /**
         * 进入nb-order-api的所有请求，哪些要拦截，哪些要放过，在这里配置
         */
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers(
                        antPatterns)
                .permitAll() //放过不拦截
                .anyRequest().authenticated();//其余所有请求都拦截
    }


}
