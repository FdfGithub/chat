package com.chat;

import com.chat.conf.HttpInterceptor;
import com.chat.conf.LoginInterceptor;
import com.chat.conf.RegisterInterceptor;
import com.chat.conf.StringToDateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.chat.dao")
public class ChatApplication extends WebMvcConfigurationSupport {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RegisterInterceptor()).addPathPatterns("/register.html");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns(
                "/user/login",
                "/user/register",
                "/user/{account}/isExist",
                "/user/{account}/register",
                "/user/inspect/register",
                "/",
                "/login.html",
                "/register.html",
                "/safe.html",
                "/css/**",
                "/img/**",
                "/js/**",
                "/upload/**"
        );
        //拦截获取session对象
        registry.addInterceptor(new HttpInterceptor())
                .addPathPatterns("/**").excludePathPatterns(
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/*.html",
                        "/",
                        "/upload/**"
        );
    }

    @Value("${local_pre}")
    private String localPre;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+localPre);
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        //添加字符串转换Date的自定义转换器
        registry.addConverter(new StringToDateConverter());
    }


}
