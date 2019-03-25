package com.btc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
	@Value("${SWAGGER.ENABLE}")
	private boolean isShow;

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).enable(isShow)
//				.apiInfo(apiInfo()).useDefaultResponseMessages(false).select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.regex("^(?!auth).*$")).build()
//				.securitySchemes(securitySchemes())
//				.securityContexts(securityContexts());
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("兴业炒股大赛文档").description("")
//				.termsOfServiceUrl("").version("1.0").build();
//	}
//
//	private List<ApiKey> securitySchemes() {
//		ApiKey ApiKey = new ApiKey("Authorization", "Authorization", "header");
//		List<ApiKey> list = new ArrayList<ApiKey>();
//		list.add(ApiKey);
//		return list;
//	}
//
//	private List<SecurityContext> securityContexts() {
//		SecurityContext securityContext = SecurityContext.builder()
//				.securityReferences(defaultAuth())
//				.forPaths(PathSelectors.regex("^(?!auth).*$")).build();
//		List<SecurityContext> list = new ArrayList<SecurityContext>();
//		list.add(securityContext);
//		return list;
//	}
//
//	private List<SecurityReference> defaultAuth() {
//		AuthorizationScope authorizationScope = new AuthorizationScope(
//				"global", "accessEverything");
//		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//		authorizationScopes[0] = authorizationScope;
//		List<SecurityReference> list = new ArrayList<SecurityReference>();
//		list.add(new SecurityReference("Authorization", authorizationScopes));
//		return list;
//	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(isShow)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.btc.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("兴业炒股大赛文档").description("")
				.termsOfServiceUrl("http://localhost:8999/")
				.termsOfServiceUrl("").version("1.0").build();
	}
}