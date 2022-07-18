package com.gilang.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.Context;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private Environment env;

    @Autowired
    private JWTAuthenticationEntryPoint unauthorizedHandler;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception{ return super.authenticationManagerBean(); }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

   /* @Bean
    public BaseLdapPathContextSource contextSource() throws Exception {
        logger.info("------ ! contextSource >> PRODUCTION");
        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("localhost:8080");

        contextSource.setUserDn(env.getProperty("ldap.username"));
        contextSource.setPassword(env.getProperty("ldap.password"));
        contextSource.setReferral("ignore");
        contextSource.afterPropertiesSet();
        contextSource.setPooled(Boolean.valueOf(env.getProperty("ldap.pooled")));

        Map<String, Object> environment = new HashMap<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put("com.sun.jndi.ldap.connect.timeout", env.getProperty("ldap.timeout"));
        environment.put("com.sun.jndi.ldap.read.timeout", env.getProperty("ldap.read.timeout"));
        contextSource.setBaseEnvironmentProperties(environment);

        return contextSource;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .antMatchers(
                        "/api/auth/signin",
                        "/api/auth/refreshToken"
                )
                .permitAll()
                .antMatchers("/api/whitelist/**").access("hasIpAddress('127.0.0.1')")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("karyawan").password("{noop}password").roles("MAKER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN")
                .and()
                .withUser("manager").password("{noop}password").roles("APPROVER")
                .and()
                .withUser("karyawati").password("{noop}password").roles("MAKER")
        ;
    }
}
