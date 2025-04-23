package com.example.novel_app.configuration;

import com.example.novel_app.filter.CacheControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CacheControlFilter> cacheControlFilter() {
        FilterRegistrationBean<CacheControlFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CacheControlFilter());
        registrationBean.addUrlPatterns("/api/v1/*");  // Áp dụng cho các URL bắt đầu với /api/
        return registrationBean;
    }
}
