package com.pjqdyd.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**   
 * @Description:  [访问安全配置(如果更改了Springboot admin应用的的context-path访问路径, 就使用此配置, 否则使用1)]
 * @Author:       pjqdyd
 * @Version:      [v1.0.0]
 */

//@Configuration
public class SpringSecurityConfig2 extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;  //Springboot admin应用内容路径(如果没改就是默认"", 使用配置1)

    public SpringSecurityConfig2(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录登出认证页面都使用Springboot admin提供的
        http.authorizeRequests()
                .antMatchers(adminContextPath + "/login").permitAll()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler())
                .and()
                .logout().logoutUrl(adminContextPath + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 打开跨站点请求保护 Cookies
                //取消跨站点请求保护 "/instances"，方便 Admin 客户端注册
                //取消跨站点请求保护 "/actuator/**"，可以让 Admin 监控到 Actuator 的相关接口
                .ignoringAntMatchers("/instances", "/actuator/**");
    }

    //@Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandler(){
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/"); //认证成功跳转的路径(跳转到admin应用内容路径下)
        return successHandler;
    }

}

