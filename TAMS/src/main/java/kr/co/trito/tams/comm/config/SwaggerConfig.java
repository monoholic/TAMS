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

	private final String title = "TAMS(Trito Asset Management System)";
	private final String version = "1.0";
	private final String apiBasePackage = "kr.co.trito.tams";

	private String apiDescription = "Trito TAMS API Docs";
	private String groupName = "";

	@Bean
	public Docket common() {
		groupName = "Common";
		apiDescription = "공통모듈 Docs";

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiBasePackage.concat(".comm")))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo(title, version));
	}
	
	 
	@Bean
	public Docket asset() {
		groupName = "Asset";
		apiDescription = "자산모듈 Docs";

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiBasePackage.concat(".web.asset")))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo(title, version));
	}	

	@Bean
	public Docket standard() {
		groupName = "Standard";
		apiDescription = "기준정보 Docs";

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiBasePackage.concat(".web.standard")))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo(title, version));
	}		
	
	@Bean
	public Docket system() {
		groupName = "System";
		apiDescription = "시스쳄관리 Docs";

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(groupName)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiBasePackage.concat(".web.system")))
				.paths(PathSelectors.any())
				.build().apiInfo(apiInfo(title, version));
	}		
	
	public ApiInfo apiInfo(String title, String version) {
		return new ApiInfoBuilder().title(title).version(version).description(apiDescription).build();
	}
}