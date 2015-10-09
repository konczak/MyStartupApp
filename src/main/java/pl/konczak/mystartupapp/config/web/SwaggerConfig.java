package pl.konczak.mystartupapp.config.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.*;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * This configuration is responsible for configuring Swagger to generate REST API documentation.
 *
 * @author piotr.konczak
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"pl.konczak.mystartupapp"})
public class SwaggerConfig {

   @Autowired
   @Value("${info.build.name}")
   private String name;

   @Autowired
   @Value("${info.build.version}")
   private String version;

   private ApiInfo apiInfo(String title) {
      return new ApiInfoBuilder()
         .title(name + " " + title)
         .description("Description TBD")
         .termsOfServiceUrl("Terms of service URL TBD")
         .contact("piotr.konczak@cybercom.com")
         .license("Licence Type TBD")
         .licenseUrl("License URL TBD")
         .version(version)
         .build();
   }

   @Bean
   public Docket businessApiDocumentationGroup() {
      String title = "Business-API";
      return new Docket(DocumentationType.SWAGGER_2)
         .groupName(title)
         .select()
         .paths(and(not(adminPaths()), regex("/api/.*")))
         .build()
         .apiInfo(apiInfo(title))
         .protocols(Sets.newHashSet("http", "https"))
         .securitySchemes(newArrayList(apiKey()))
         .securityContexts(newArrayList(securityContext()));
   }

   @Bean
   public Docket adminApiDocumentationGroup() {
      String title = "Admin-API";
      return new Docket(DocumentationType.SWAGGER_2)
         .groupName(title)
         .select()
         .paths(adminPaths())
         .build()
         .apiInfo(apiInfo(title))
         .protocols(Sets.newHashSet("http", "https"))
         .securitySchemes(newArrayList(apiKey()))
         .securityContexts(newArrayList(securityContext()));
   }

   /**
    * How we should configure it properly for our up?
    */
   private ApiKey apiKey() {
      return new ApiKey("mykey", "api_key", "header");
   }

   /**
    * How we should configure it properly for our up?
    */
   private SecurityContext securityContext() {
      return SecurityContext.builder().securityReferences(defaultAuth())
         .forPaths(PathSelectors.regex("/anyPath.*")).build();
   }

   /**
    * How we should configure it properly for our up?
    */
   private List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      return newArrayList(new SecurityReference("mykey", authorizationScopes));
   }

   private Predicate<String> adminPaths() {
      return or(regex("/api/admin/.*"),
         regex("/api/javascript.*"));
   }

}
