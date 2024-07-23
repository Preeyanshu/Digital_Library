package com.example.minorproject1.Configurations;

import com.example.minorproject1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .csrf().disable()       // Only for testing purpose, should not be used real time application
                .authorizeHttpRequests()
                .antMatchers("/student/post/**").permitAll()
                .antMatchers("/student/details/**").hasAuthority("get-student-profile")
                .antMatchers("/student/delete/**").hasAuthority("update-student-account")
                .antMatchers("/student/update/**").hasAuthority("update-student-account")
                .antMatchers("/student/get/**").hasAuthority("get-student-details")

//                .antMatchers("/admin/post/**").permitAll()        //wrong

                .antMatchers("/book/post/**").hasAuthority("update-book")
                .antMatchers("/book/delete/**").hasAuthority("update-book")
                .antMatchers(HttpMethod.GET,"/book/**").hasAuthority("get-book-details")

                .antMatchers("/transaction/**").hasAuthority("book-transaction")

//                .antMatchers("/admin/post/**").hasAuthority("add-admin")
                .antMatchers("/admin/post/**").permitAll()
                .and()
                .formLogin();
    }



    /**
     * ---> CSRF ( Cross Site Request Forgery ) : it is an attack that forces authenticated users to submit a request
     *                                              to a Web application against which they are currently authenticated
     *
     *      ** Since we don't have this capability now,  for the time being we will disable CSRF capability
     * **/


    @Bean
    PasswordEncoder getPE() {
        return new BCryptPasswordEncoder();
    }

}
