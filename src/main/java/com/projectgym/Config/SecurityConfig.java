package com.projectgym.Config;

import com.projectgym.service.CustomUserDetailsService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.projectgym.service.MyAppUserService;

import lombok.AllArgsConstructor;


import java.util.Arrays;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final MyAppUserService appUserService;

    @Bean
    public UserDetailsService userDetailsService(){
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Autowired
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.defaultSuccessUrl("/index");
                    httpForm.successHandler(customAuthenticationSuccessHandler);
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/api/vnpay/**").permitAll();
                    registry.requestMatchers("/api/vnpay/create-payment", "/api/vnpay/return").permitAll();
                    registry.requestMatchers("/css2/**", "/js2/**", "/images/**", "/fonts2/**","/css3/**","/css4/**","/js3/**","/js4/**","/img/**","/assets3/**","/assets4/**","/Source/**","/fonts/**").permitAll();
                    registry.requestMatchers("/ws/**").permitAll();
                    registry.requestMatchers("gym/home/**").permitAll();
                    registry.requestMatchers("customer/home/users/user-info").authenticated(); // Chỉ người dùng đã đăng nhập mới có thể truy cập
                    registry.requestMatchers("/admin/home/users/**").hasAnyRole("ADMIN", "TRAINER");
                    registry.requestMatchers("/admin/home/notifications/**").hasAnyRole("ADMIN", "TRAINER");
                    registry.requestMatchers("admin/home/users/user-info1").hasAnyRole("CUSTOMER", "ADMIN");
                    registry.requestMatchers("/customer/home/users/user-info1").hasAnyRole("ADMIN", "CUSTOMER");
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");  // Chỉ cho phép ADMIN truy cập trang này
                    registry.requestMatchers("/trainer/**").hasRole("TRAINER");  // Chỉ cho phép TRAINER truy cập trang này
                    registry.requestMatchers("/customer/**").hasRole("CUSTOMER");  // Chỉ cho phép CUSTOMER truy cập trang này
                   // Cho phép truy cập WebSocket mà không yêu cầu đăng nhập
                    registry.requestMatchers("/req/signup", "/css/**", "/js/**").permitAll();
                    registry.anyRequest().authenticated();
                })

                .build();
    }


}
