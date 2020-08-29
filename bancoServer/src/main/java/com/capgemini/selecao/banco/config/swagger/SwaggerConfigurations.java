package com.capgemini.selecao.banco.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.capgemini.selecao.banco.models.User;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations 
{
	/**
	 * Aplication documentation by swagger. 
	 */
	@Bean
	public Docket bancoApi()
	{
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.capgemini.selecao.banco"))
		.paths(PathSelectors.ant("/**"))
		.build()
		.ignoredParameterTypes(User.class, UsernamePasswordAuthenticationToken.class)
		.globalOperationParameters
		(
			Arrays.asList
			(
				new ParameterBuilder()
				.name("Authorization")
				.description("Header for token JWT")
				.modelRef(new ModelRef("string"))
				.parameterType("header")
				.required(false)
				.build()
			)
		)
		;
	}
}
