package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * SwaggerConfig
 * @author Administratior
 * 使用Swagger2 UI构建API文档
参考文档
http://blog.csdn.net/u012476983/article/details/54090423


在上面只展示了如何使用，这里将对上面添加的swagger注解进行说明，笔记使用时参考了swagger annotations Api 手册，接下来进行部分常用注解使用说明介绍。
- @ApiIgnore 忽略注解标注的类或者方法，不添加到API文档中

 @ApiOperation 展示每个API基本信息

 value api名称
 notes 备注说明
 @ApiImplicitParam 用于规定接收参数类型、名称、是否必须等信息

 name 对应方法中接收参数名称
 value 备注说明
 required 是否必须 boolean
 paramType 参数类型 body、path、query、header、form中的一种
 body 使用@RequestBody接收数据 POST有效
 path 在url中配置{}的参数
 query 普通查询参数 例如 ?query=q ,jquery ajax中data设置的值也可以，例如 {query:”q”},springMVC中不需要添加注解接收
 header 使用@RequestHeader接收数据
 form 笔者未使用，请查看官方API文档
 dataType 数据类型，如果类型名称相同，请指定全路径，例如 dataType = “java.util.Date”，springfox会自动根据类型生成模型
 @ApiImplicitParams 包含多个@ApiImplicitParam

 @ApiModelProperty 对模型中属性添加说明，例如 上面的PageInfoBeen、BlogArticleBeen这两个类中使用，只能使用在类中。

 value 参数名称
 required 是否必须 boolean
 hidden 是否隐藏 boolean
 其他信息和上面同名属性作用相同，hidden属性对于集合不能隐藏，目前不知道原因
 @ApiParam 对单独某个参数进行说明，使用在类中或者controller方法中都可以。注解中的属性和上面列出的同名属性作用相同

 以上为主要常用的注解介绍，请结合springfox使用查看
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.study.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("程序猿DD")
                .version("1.0")
                .build();
    }

    /**
     * swagger-ui.html路径映射，浏览器中使用/api-docs访问
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-docs","/swagger-ui.html");
    }

}
