package com.luhan.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RedirectAttributesMethodArgumentResolver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author luHan
 * @create 2019-08-29 18:27
 * @since 1.0.0
 */
@EnableWebMvc
@Configuration
public class WebmvcConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("currentLimitingInterceptor")
    private CurrentLimitingInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/").
                setCacheControl(CacheControl.maxAge(1,TimeUnit.DAYS ).cachePrivate());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RedirectAttributesMethodArgumentResolver());
    }
}
