package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests() // configuram daca e nevoie de autentificare sau nu pentru a accesa
                                        // anumite endpoint-uri
                .antMatchers("/login").permitAll() // nu e nevoie de autentificare pentru a accesa functionalitatea de login
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()// pentru orice alta cerere e nevie de autentificare
                .and()
                .logout()
                .logoutUrl("/logout") //configuram care endpoint este folosit pentru logout
                .logoutSuccessUrl("/") // se face redirectionare spre index page dupa logout
                .invalidateHttpSession(true) //sesiunea curenta devine inactiva
                .deleteCookies("*"); // si se sterg toate cookie-urile
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
