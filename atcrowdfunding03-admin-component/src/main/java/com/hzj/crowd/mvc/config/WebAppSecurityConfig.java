package com.hzj.crowd.mvc.config;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// 启用全局方法权限控制功能，并且设置 prePostEnabled = true。
// 保证@PreAuthority、 @PostAuthority、@PreFilter、@PostFilter 生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CrowdUserService crowdUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(crowdUserService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
security
    .authorizeRequests()
        .antMatchers("/admin/to/login/page.html")
        .permitAll()
        .antMatchers("/bootstrap/**")
        .permitAll()
        .antMatchers("/crowd/**")
        .permitAll()
        .antMatchers("/css/**")
        .permitAll()
        .antMatchers("/fonts/**")
        .permitAll()
        .antMatchers("/img/**")
        .permitAll()
        .antMatchers("/jquery/**")
        .permitAll()
        .antMatchers("/layer/**")
        .permitAll()
        .antMatchers("/script/**")
        .permitAll()
        .antMatchers("/ztree/**")
        .permitAll()
        .and()
    .formLogin()
        .loginPage("/admin/to/login/page.html")
        .loginProcessingUrl("/security/do/login.html")
        .defaultSuccessUrl("/admin/to/main/page.html")
        .usernameParameter("loginAcct")
        .passwordParameter("userPswd")
        .and()
        .csrf()
        .disable()
    .logout()
        .logoutUrl("/security/do/logout.html")
        .logoutSuccessUrl("/admin/to/login/page.html")
  ;

    }
}