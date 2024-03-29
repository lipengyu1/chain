package com.zxn.chain.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.zxn.chain.common.JacksonObjectMapper;
import com.zxn.chain.filter.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Slf4j
@Configuration
@EnableKnife4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始静态资源映射");
//        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
//        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 扩展mvc框架的消息转换器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0, messageConverter);
    }

    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zxn.chain.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("连锁店管理系统")
                .version("1.0")
                .description("系统接口文档")
                .build();
    }

    /**
     * 添加jwt拦截器，并指定拦截路径
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(
                "/member/login",
                "/member/add",
                "/back/login",
                "/back/register",
                "/back/sendMsg",
                "/back/find",
                "/back/logout",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs",
                "/common/upload",
                "/common/download",
                "/storemanager/add",
                "/user/add"
        );
    }

    /**
     * jwt拦截器
     * */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
