package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Check https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * for additional config
 * 
 * TODO customize
 * 
 * @author ferdinando.villa
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		
		/*
		 * ADD vendor extensions for all components and TOS
		 */
		
		return new ApiInfo("k.LAB REST API", "This page documents the k.LAB API known to this server.", "API TOS",
				"Terms of service",
				new Contact("Integrated Modelling Partnership", "www.integratedmodelling.org",
						"info@integratemodelling.org"),
				"AGPL v3.0", "https://www.gnu.org/licenses/agpl-3.0.en.html", Collections.emptyList());
	}
}
