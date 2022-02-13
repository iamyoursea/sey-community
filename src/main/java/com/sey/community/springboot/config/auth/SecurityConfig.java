package com.sey.community.springboot.config.auth;

import com.sey.community.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                    .headers().frameOptions().disable()

                .and()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/", "/css/**", "/img/**", "/js/**", "/h2/**", "/h2-console/**", "/images/**","/favicon.ico").permitAll()
                    .antMatchers("/profile").permitAll()
                    .antMatchers("/notice").permitAll()
                    .antMatchers("/games").permitAll()
                    .antMatchers("/games/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .antMatchers("/posts/view/**").permitAll()
                    .antMatchers("/notice/view/**").permitAll()
                    .antMatchers("/notice/update/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .antMatchers(HttpMethod.GET,"/api/v1/notice").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/notice/**").hasRole(Role.ADMIN.name())
                    .antMatchers(HttpMethod.PUT,"/api/v1/notice/**").hasRole(Role.ADMIN.name())
                    .antMatchers(HttpMethod.DELETE,"/api/v1/notice/**").hasRole(Role.ADMIN.name())
                    .antMatchers(HttpMethod.GET,"/api/v1/notice/**").permitAll()
                    .antMatchers("/api/v1/posts/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .antMatchers(HttpMethod.GET,"/api/v1/log/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/comments").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .antMatchers(HttpMethod.POST,"/api/v1/comments/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login().loginPage("/login")
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
}
