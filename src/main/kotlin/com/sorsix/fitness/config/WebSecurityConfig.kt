//package com.sorsix.fitness.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//class WebSecurityConfig(
//    val authenticationProvider: CustomUsernamePasswordAuthenticationProvider
//) : WebSecurityConfigurerAdapter() {
//
//
//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//
//        http.csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/**").permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login").permitAll()
//            .failureUrl("/login?error=BadCredentials")
//            .defaultSuccessUrl("/", true)
//            .and()
//            .logout()
//            .logoutUrl("/logout")
//            .clearAuthentication(true)
//            .invalidateHttpSession(true)
//            .deleteCookies("JSESSIONID")
//            .logoutSuccessUrl("/login")
//            .and()
//            .exceptionHandling()
//            .accessDeniedPage("/access_denied");
//
//    }
//
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//
//
//}