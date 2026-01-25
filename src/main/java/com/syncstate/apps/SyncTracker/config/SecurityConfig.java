package com.syncstate.apps.SyncTracker.config;


import com.syncstate.apps.SyncTracker.providers.SmartBankingAuthenticationProvider;
import com.syncstate.apps.SyncTracker.providers.TokenProvider;
import com.syncstate.apps.SyncTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private SmartBankingAuthenticationProvider authProvider;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserService userService;


    @Autowired
    private ApplicationContext applicationContext;

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider);
//    }
@Bean
public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(authProvider);

    return authenticationManagerBuilder.build();
}

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web
//                .ignoring().requestMatchers("/api/v1/user/create-new-user", "/api/v1/user/login");
//    }

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/api/v1/user/login").permitAll();
//                    auth.anyRequest().authenticated();
//                })
//
//                .formLogin(Customizer.withDefaults()).build();
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        return http
                .sessionManagement(sm -> {
                    sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                })

//                .apply(new JwtConfigurer(tokenProvider, userService))
                .csrf(cs -> {
                    cs.disable().apply(new JwtConfigurer(tokenProvider, userService));
                })

                .cors(crs -> {
                    crs.disable().apply(new JwtConfigurer(tokenProvider, userService));
                })
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers("/api/v1/user/login").permitAll(); //"/api/v1/acquirers/create-user",
                    //auth.requestMatchers( "/api/v1/banking/funds-transfer").access();
                    auth.requestMatchers("/test/**").permitAll();
                    auth.requestMatchers("/api/v1/user/create-new-user").permitAll();
                    auth.requestMatchers("/api/vi/client/create-client").permitAll();
                    auth.requestMatchers("/api/vi/client/get-client-by-domain/**").permitAll();
                    auth.anyRequest().authenticated();


                })
                .authenticationProvider(authProvider)
                .build();

    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
