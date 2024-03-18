package com.siyamuddin.blog.blogappapis.Config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER="Authorization";

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Blog Application using Spring Boot")
                        .description("This APIs are implementation of a complete blog application.")
                        .version("1.0").contact(contact())
                        .license(ls()))
                .components(new Components()
                        .addSecuritySchemes("api_key",new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER).name("JWT-API-KEY")));
    }

    public Contact contact()
    {
        Contact cnt=new Contact();
        cnt.setName("UDDIN SIYAM");
        cnt.setEmail("siyamuddin177@gmail.com");
        cnt.setUrl("https://siyamuddin.netlify.app/");
        return cnt;
    }
    public License ls()
    {
        License license=new License();
        license.setName("UDDIN SIYAM");
        return license;
    }
}
