package com.example.msuser.config;

import com.example.msuser.security.AuthEntryPoint;
import com.example.msuser.security.AuthenticationFilter;
import com.example.msuser.security.AuthorizationFilter;
import com.example.msuser.security.JwtService;
import com.example.msuser.service.user.UserService;
import com.example.msuser.util.enums.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthorizationFilter authorizationFilter;
    private final AuthEntryPoint authEntryPoint;
    private final UserService userService;
    private final JwtService jwtService;

    private static final String[] WHITE_LIST = {
            "/**/swagger-ui/**",
            "/**/v3/api-docs/**",
            "/**/swagger-resources/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers(HttpMethod.POST, "/**/users").permitAll()
                .antMatchers(HttpMethod.POST, "/**/users/courier")
                .hasAnyAuthority(RoleType.ADMIN.name())
                .anyRequest()
                .authenticated();
        http.httpBasic();
        http.addFilter(new AuthenticationFilter(
                userService,
                authenticationManager(new AuthenticationConfiguration()),
                jwtService));
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
