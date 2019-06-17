package com.java.projects.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/oauth/**", "/api/user/register")
                    .permitAll()
//                .antMatchers(HttpMethod.POST, "/user/employee")
//                .hasRole("ADMIN")
//                .antMatchers("/user/changepassword/**", "/current/id")
//                .hasAnyRole("ADMIN", "EMPLOYEE", "USER")
                    .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


}
