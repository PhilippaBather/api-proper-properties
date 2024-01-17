package com.philippabather.properpropertiesapi.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.philippabather.properpropertiesapi.controller.ProprietorController;
import com.philippabather.properpropertiesapi.security.AuthEntryPointJwt;
import com.philippabather.properpropertiesapi.security.AuthTokenFilter;
import com.philippabather.properpropertiesapi.security.ProprietorUserDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * AppConfig - la clase que define las configuraciones de la aplicación.
 *
 * @author Philippa Bather
 */

@Configuration
public class AppConfig{

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }

    @Value("${key.public}")
    RSAPublicKey key;

    @Value("${key.private}")
    RSAPrivateKey priv;

    @Autowired
    private ProprietorUserDetailsService proprietorUserDetailsService;

    @Autowired
    ProprietorUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        logger.info("AppConfig_authenticationJwtTokenFilter");
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        logger.info("start: AppConfig_authenticationProvider");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        logger.info("end: AppConfig_authenticationProvider");
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        logger.info("AppConfig_authenticationManager");
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("AppConfig_passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("start: AppConfig_filterChain");
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/register").permitAll()
                                .requestMatchers("/token").permitAll()
                                .requestMatchers("/users/proprietors/secured/**").permitAll()
                                .requestMatchers("/properties/rental/**").permitAll()
                                .requestMatchers("/properties/sale/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        logger.info("end: AppConfig_filterChain");
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        logger.info("AppConfig_webSecurityCustomizer");
        return (web) -> web.ignoring().requestMatchers(
                "/users/clients/**", "/users/proprietors/**",
                "/properties/rental", "/properties/count/rental", "/properties/bedrooms/rental/**", "/properties/facilities/rental",
                "properties/sale",
                "/addresses/**");
    }

    @Bean
    JwtDecoder jwtDecoder() {
        logger.info("AppConfig_jwtDecoder");
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        logger.info("start: AppConfig_jwtEncoder");
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        logger.info("end: AppConfig_jwtEncoder");
        return new NimbusJwtEncoder(jwks);
    }


}
