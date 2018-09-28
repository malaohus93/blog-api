package com.blog.api.config;

import com.blog.api.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    UserDetailsService customUserService() {
//        return new UserService();
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService());
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated()
//    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String finalPassword = "{bcrypt}"+bCryptPasswordEncoder.encode("123456");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password(finalPassword).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(finalPassword).authorities("USER").build());
        return manager;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

}
