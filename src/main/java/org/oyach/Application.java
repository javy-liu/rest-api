package org.oyach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * description
 *
 * @author oyach
 * @since 0.0.1
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableConfigurationProperties
@ComponentScan
public class Application {


    @Configuration
    protected static class AppMvcConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
            configurer.defaultContentType(MediaType.APPLICATION_JSON);
            configurer.ignoreAcceptHeader(true);

        }
    }

//    @Configuration
//    public static class AppRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {
//
//    }


//    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    protected static class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Autowired
        private SecurityProperties security;


        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication().//
                    withUser(security.getUser().getName())
                    .password(security.getUser().getPassword())
                    .roles(security.getUser().getRole().toArray(new String[security.getUser().getRole().size()]))
                    .and().//
                    withUser("oyach").password("123456").roles("USER").and().//
                    withUser("admin").password("123456").roles("USER", "ADMIN");


        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.httpBasic().realmName(security.getBasic().getRealm()).and().authorizeRequests().//
                    antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN").//
                    antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN").//
                    antMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN").//
                    antMatchers(HttpMethod.PATCH, "/users/**").hasRole("ADMIN").and().//
                    csrf().disable();
        }
    }


}

