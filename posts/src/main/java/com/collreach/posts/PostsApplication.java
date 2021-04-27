package com.collreach.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@SpringBootApplication
@EnableSwagger2
public class PostsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}

	@Bean
	public Docket userProfileApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.
						basePackage("com.collreach.posts.controller")).
						build()
				.apiInfo(apiInfo())
				.securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("REST API Document")
				.description("description for api")
				.termsOfServiceUrl("localhost")
				.version("1.0")
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header"); //`apiKey` is the name of the APIKey, `Authorization` is the key in the request header
	}
}
