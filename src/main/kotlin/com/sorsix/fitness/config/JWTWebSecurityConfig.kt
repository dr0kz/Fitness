package com.sorsix.fitness.config

import com.sorsix.fitness.config.filters.JWTAuthorizationFilter
import com.sorsix.fitness.config.filters.JwtAuthenticationFilter
import com.sorsix.fitness.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class JWTWebSecurityConfig(val authenticationProvider: CustomUsernamePasswordAuthenticationProvider,
                           val passwordEncoder: PasswordEncoder,
                           val userService: UserService) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/home","/login/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(JwtAuthenticationFilter(authenticationManager(), userService, passwordEncoder))
            .addFilter(JWTAuthorizationFilter(authenticationManager(), userService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)


//        http.cors().and().csrf().disable()
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

    }

//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(authenticationProvider);
//    }
}