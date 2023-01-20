package com.example.login.securityForm.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //build http accept //2022-1-18
    @Bean
   public SecurityFilterChain configure(HttpSecurity http) throws Exception{
    http

               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    return http.build();

    }


    //build login user with password who used passwordEncoder//2022.1/19
    @Bean
    public UserDetailsService userDetailsService(){
         UserDetails MgMyoUser = User.builder()
                .username("MgMyo")
                .password(passwordEncoder.encode("password"))//must be password encode if u dont it will be error☆
                .roles("STUDENT")//ROLE-Name
                 .build();

                 return new InMemoryUserDetailsManager(
                 MgMyoUser);

    }





}
