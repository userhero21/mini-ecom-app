package com.company.miniecom.configs.security;

import com.company.miniecom.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    public static final String[] WHITE_LIST = new String[]{
            "/",
            "/products/**",
            "/product",
            "/about",
            "/blog",
            "/contact",
            "/signin",
            "/signup",
            "/showCover",
            "/resources/**",
            "/static/**",
            "/css/**",
            "/js/**",
            "/img/**",
            "/manager/**",
            "/fonts/**"
    };

//    @Value("${spring.security.rememberme.secret.key}")
//    public String SECRET_KEY;

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;


//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers(WHITE_LIST);
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

//        csrf().disable()

        http.csrf().disable()
                .authorizeRequests(
                        expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                                .antMatchers(WHITE_LIST).permitAll()
                                .anyRequest()
                                .authenticated())

                .formLogin(
                        httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                .loginPage("/signin")
                                .loginProcessingUrl("/signin")
                                .defaultSuccessUrl("/", true))

//                .rememberMe(
//                        httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
//                                .rememberMeParameter("rememberMe")
//                                .rememberMeCookieName("remember")
//                                .tokenValiditySeconds(10 * 86400)
//                                .key(SECRET_KEY))

                .logout(
                        httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                                .logoutRequestMatcher(new AntPathRequestMatcher(
                                        "/logout",
                                        "POST",
                                        true))
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID", "remember")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/")
                );
//                        .authenticationEntryPoint(authenticationEntryPoint);
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authService)
                .passwordEncoder(passwordEncoder);
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
