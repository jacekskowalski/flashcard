package com.project.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CustomFilter   {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(authFilter);
        filterRegistrationBean.addUrlPatterns("/flashcard","/achievement/*","/statistics","/favouriteflashcard","/favouriteflashcard/*","/account/*","/discovered");
        return filterRegistrationBean;
    }

    public AuthFilter getAuthFilter() {
        return authFilter;
    }

    public void setAuthFilter(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }
}
