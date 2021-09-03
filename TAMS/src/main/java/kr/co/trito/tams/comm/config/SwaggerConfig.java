package kr.co.trito.tams.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    private final String apiBasePackage = "kr.co.trito.tams";
    private final String apiDescription = "Trito TAMS API Docs";
    private String version;
    private String title;
        
    @Bean
    public Docket api() {
    	version = "Api";
    	title = "Trito TAMS";
    	
        return new Docket(DocumentationType.SWAGGER_2)
        		.useDefaultResponseMessages(false)
        		.groupName(version)
                .select()        		
                .apis(RequestHandlerSelectors.basePackage(apiBasePackage))
                .paths(PathSelectors.any())
                .build()
        		.apiInfo(apiInfo(title, version));
    }

    public ApiInfo apiInfo(String title, String version) {
		return new ApiInfoBuilder()
			.title(title)
			.version(version)
			.description(apiDescription)
			.build();
    }    
}