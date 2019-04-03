package com.btc.springConfig.web;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.btc.springConfig.filter.SecureCheckFilter;
import com.btc.springConfig.filter.UserSecurityInterceptor;
/**
 * web 容器统一配置
 * @author admin
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
     private UserSecurityInterceptor securityInterceptor;
	/**
	 * 过滤器配置
	 * @param myFilter
	 * @return
	 */
	@Bean  
    public FilterRegistrationBean filterRegistrationBean(SecureCheckFilter myFilter){  
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
        filterRegistrationBean.setFilter(myFilter);  
        filterRegistrationBean.setEnabled(true); //是否过滤
        filterRegistrationBean.addUrlPatterns("/front/auth/*");  
        return filterRegistrationBean;  
    }
	 

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor).
        addPathPatterns("/admin/**").
        excludePathPatterns("/admin/login/**");//配置登录拦截器拦截路径
    }
    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10MB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

	
	 // 用于处理编码问题  
    @Bean  
    public Filter characterEncodingFilter() {  
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();  
        characterEncodingFilter.setEncoding("UTF-8");  
        characterEncodingFilter.setForceEncoding(true);  
        return characterEncodingFilter;  
    }  
    /**
     * session超时时间设置
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override 
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(2, TimeUnit.HOURS);
            }
        };
    }
    /**
     * 跨域
     */
    @Override  
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**")
        		.allowedMethods("*")
                .allowedOrigins("*")  
                .allowCredentials(true)  
                .allowedMethods("GET", "POST", "DELETE", "PUT")  
                .maxAge(3600);  
    } 
    
}
