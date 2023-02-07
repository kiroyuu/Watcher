package com.joelhelkala.watcherServer.security.config;

import com.joelhelkala.watcherServer.filter.CustomAuthenticationFilter;
import com.joelhelkala.watcherServer.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        Spring Boot http configuration.
        -------------------------------
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Specify the login path for access
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // login, ping and registration does not need authorization
        http.authorizeRequests().antMatchers("/api/v*/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/ping").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/registration/**").permitAll();

        // ---------------------------
        http.authorizeRequests().antMatchers("/api/v*/nodeData").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/nodeData/**").permitAll();


        // Set the admin endpoint to have ADMIN role
        http.authorizeRequests().antMatchers("/api/v*/appuser/admin").hasAnyAuthority("ADMIN");
        // Every request should be authenticated
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
