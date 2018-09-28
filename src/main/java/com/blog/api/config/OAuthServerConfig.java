package com.blog.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
public class OAuthServerConfig {

    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/order")
                    .authenticated();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;

        @Override
        public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123");
            clients.inMemory()
                    .withClient("client_one")
                    .authorizedGrantTypes("password")
                    .scopes("select")
                    .authorities("oauth2")
                    .secret(finalSecret);
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

    }

}
