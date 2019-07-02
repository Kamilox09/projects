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
                    .antMatchers("/oauth/**", "/api/user/register", "/api/test")
                    .permitAll()
                .antMatchers( "/api/user/current")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/api/project")
                .hasRole("ADMIN")
                .antMatchers("/api/users")
                .hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


}
