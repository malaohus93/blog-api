package com.blog.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


@Configuration
public class OAuthServerConfig {

    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
        }

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

        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws InternalAuthenticationServiceException {
            endpoints.tokenStore(tokenStore())
                    .authenticationManager(authenticationManager).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
                    .allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123");
            clients.inMemory()
                    .withClient("client_one")
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("read", "write", "trust")
                    .authorities("oauth2")
                    .secret(passwordEncoder.encode("test123"));
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

    }

}
