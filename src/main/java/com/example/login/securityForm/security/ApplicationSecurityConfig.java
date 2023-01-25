package com.example.login.securityForm.security;


import com.example.login.securityForm.student.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.login.securityForm.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //build http accept //2022-1-18
    @Bean
   public SecurityFilterChain configure(HttpSecurity http) throws Exception{
    http
//               .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//               .and()
               .csrf().disable()
               .authorizeRequests()
               .antMatchers("/","index","/css/*","/js/*").permitAll()
               .antMatchers("/api/**").hasRole(STUDENT.name())//http permission for userROle only
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()// form base auth
               .loginPage("/login").permitAll()// customize login 画面
               .defaultSuccessUrl("/courses",true)//redirect http
            .and()
            .rememberMe()//default remember for 2week
               .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))// remember me for 2 weeks cookies
               .key("somethingVerySecured")
            .and()
            .logout()
               .logoutUrl("/logout")
               .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
               .clearAuthentication(true)
               .invalidateHttpSession(true)
               .deleteCookies("JSESSIONID","remember-me","Idea-6668d779")
               .logoutSuccessUrl("/login");
              // .httpBasic();//for use form base authorization
    return http.build();

    }


    //build login user with password who used passwordEncoder//2022.1/19
    @Bean
    public UserDetailsService userDetailsService(){
         UserDetails MgMyoUser = User.builder() //create student role for permission(23.1.20)
                .username("MgMyo")
                .password(passwordEncoder.encode("password"))//must be password encode if u dont it will be error☆
//                .roles(ApplicationUserRole.STUDENT.name())//ROLE-Name//ROle --student
                 .authorities(STUDENT.getGrantedAuthorities())
                 .build();

         UserDetails KoKoUser = User.builder()  //create Admin role for permission(23.1.20)
                 .username("KoKo")
                 .password(passwordEncoder.encode("password123"))
//                 .roles(ApplicationUserRole.ADMIN.name())//ROLE-Admin
                 .authorities(ADMIN.getGrantedAuthorities())
                 .build();

         UserDetails TheRockUser = User.builder()
                 .username("TheRock")
                 .password(passwordEncoder.encode("password123"))
//                 .roles(ApplicationUserRole.ADMINTRAINEE.name())//ROLE-AdminTrainee
                 .authorities(ADMINTRAINEE.getGrantedAuthorities())
                 .build();
         return new InMemoryUserDetailsManager(
                 MgMyoUser, KoKoUser,TheRockUser);

    }





}
