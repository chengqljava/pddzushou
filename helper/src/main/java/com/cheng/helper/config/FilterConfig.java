package com.cheng.helper.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheng.helper.filter.LoginFilter;
@Configuration
public class FilterConfig {
	   @Bean
	    public FilterRegistrationBean memberFilterRegistration() {
	        FilterRegistrationBean registration = new FilterRegistrationBean();
	        //注入过滤器
	        registration.setFilter(new LoginFilter());
	        //拦截规则
	        registration.addUrlPatterns("/*");
	        //过滤器名称
	        registration.setName("loginFilter");
	        //过滤器排序
	        //registration.setOrder(4);
	        return registration;
	    }
}
