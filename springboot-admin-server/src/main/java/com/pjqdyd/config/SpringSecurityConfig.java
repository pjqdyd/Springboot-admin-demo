package com.pjqdyd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**   
 * @Description:  [服务端访问安全配置]
 * @Author:       pjqdyd
 * @Version:      [v1.0.0]
 */

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login") //登录登出认证页面都使用Springboot admin提供的, 如果不配置就是默认security提供的
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 打开跨站点请求保护 Cookies
                //取消跨站点请求保护 "/instances"，方便 Admin 客户端注册
                //取消跨站点请求保护 "/actuator/**"，可以让 Admin 监控到 Actuator 的相关接口
                .ignoringAntMatchers("/instances", "/actuator/**");
    }

}
