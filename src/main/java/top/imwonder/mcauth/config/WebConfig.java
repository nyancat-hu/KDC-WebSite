/*
 * @Author: Wonder2019 
 * @Date: 2020-05-01 22:09:23 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-16 13:00:21
 */
package top.imwonder.mcauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

// import top.imwonder.myblog.Env;
// import top.imwonder.myblog.enumeration.EnumConverterFactory;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // 处理静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/static/assets/robots.txt");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/assets/img/favicon.ico");
        registry.addResourceHandler("/sitemap.xml").addResourceLocations("file:./sitemap.xml");
    }

    // 枚举转换器
    // @Override
    // public void addFormatters(FormatterRegistry registry) {
    //     registry.addConverterFactory(new EnumConverterFactory());
    // }

    // 视图解析器
    @Bean
    public ViewResolver beanNameViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        return resolver;
    }

    // json视图
    @Bean
    public View json() {
        return new MappingJackson2JsonView();
    }

    // gson工具
    // @Bean
    // public Gson gson() {
    //     return new Gson();
    // }

    // 通用http客户端
    // @Bean
    // public RestTemplate restTemplate() {
    // return new RestTemplate();
    // }

    // @Bean
    // public ServletListenerRegistrationBean<HttpSessionListener>
    // sessionListener(UsageHttpSessionListener usageHttpSessionListener) {
    // return new
    // ServletListenerRegistrationBean<HttpSessionListener>(usageHttpSessionListener);
    // }

}
