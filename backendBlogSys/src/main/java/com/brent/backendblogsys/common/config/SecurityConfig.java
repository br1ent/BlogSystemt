package com.brent.backendblogsys.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 暂时禁用 CSRF，方便 Postman 测试
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 允许匿名访问 / 下的接口
                        .anyRequest().authenticated()            // 其他所有请求仍需登录
                )
                .formLogin(form -> form.permitAll());       // 允许使用表单登录

        return http.build();
    }
}