package com.powernode.config;

import com.powernode.config.filter.TokenVerifyFilter;
import com.powernode.config.handler.MyAccessDeniedHandler;
import com.powernode.config.handler.MyAuthenticationFailureHandler;
import com.powernode.config.handler.MyAuthenticationSuccessHandler;
import com.powernode.config.handler.MyLogoutSuccessHandler;
import com.powernode.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableMethodSecurity  // 开启方法级别的权限检查
@Configuration
public class SecurityConfig {
    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private TokenVerifyFilter tokenVerifyFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource) throws Exception {
        // 禁止跨站请求
        return httpSecurity
                .formLogin( (formLogin) -> {
                    formLogin.loginProcessingUrl(Constants.LOGIN_URI)  //登录处理地址，不需要写Controller
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(myAuthenticationSuccessHandler)
                            .failureHandler(myAuthenticationFailureHandler);
                })

                .authorizeHttpRequests( (authorize) -> {
                    authorize.requestMatchers(Constants.LOGIN_URI).permitAll()
                            .anyRequest().authenticated(); //其它任何请求都需要登录后才能访问
                })

                .csrf(AbstractHttpConfigurer :: disable) //方法引用，禁用跨站请求伪造

                //支持跨域请求
                .cors( (cors) -> {
                    cors.configurationSource(corsConfigurationSource);
                })

                // 禁用session
                .sessionManagement( (session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                // 添加过滤器
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)

                // 退出登录
                .logout((logout) -> {
                    logout.logoutUrl("/api/logout")
                            .logoutSuccessHandler(myLogoutSuccessHandler);
                })

                // 权限拒绝，没有权限访问时触发
                .exceptionHandling((exceptionHandling) -> {
                    exceptionHandling.accessDeniedHandler(myAccessDeniedHandler);
                })

                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));  // 允许任何请求来源
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));  // 允许任何请求方法
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));  // 允许任何请求头
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
