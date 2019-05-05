package com.project.flashcards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.flashcards.Repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    private AppuserRepository appuserRepository;
    public AppuserRepository getAppuserRepository() {
        return appuserRepository;
    }
    private ObjectMapper objectMapper= new ObjectMapper();
    public void setAppuserRepository(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/","/login","/signup","/remember","/reset", "/flashcard","/statistics","/statistics/**"
                ,"/*.js","/*.css","/*.jsx","/public/**","/favouriteflashcard","/favouriteflashcard/**").permitAll()
                .antMatchers("/account/**","/achievement","http://localhost:3000/achievement/**").permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable()
                .formLogin().loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("pswd")
                .successHandler(this::loginSuccessHandler)
                .failureHandler(this::loginFailureHandler)
                .and()
                .logout().permitAll().logoutSuccessUrl("http://localhost:3000/").
                invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response,
                                     Authentication authentication) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), "logged in!");
    }

    private void loginFailureHandler(HttpServletRequest request,HttpServletResponse response,
                                     AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(),"Wrong data");

    }
/*
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }*/
}
