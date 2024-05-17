package com.siyamuddin.blog.blogappapis.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@OpenAPIDefinition(
        info = @Info(
                title = "UniBlog",
                description = "This a backend of a complete blogging web application named UniBlog.",
                termsOfService = "Terms of Service",
                contact = @Contact(
                        name = "UDDIN SIYAM",
                        email = "siyamuddin177@gmail.com",
                        url = "https://siyamuddin.netlify.app"
                )

        ),
        servers = {@Server(
                description = "Poduction Env",
                url = "https://uniblogs-production.up.railway.app/"

        )}
)
@SecurityScheme(
        name = "JWT-Auth",
        description = "JWT Authentication Description",
        scheme ="bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in= SecuritySchemeIn.QUERY
        )
public class OpenApiConfiguration {
}
