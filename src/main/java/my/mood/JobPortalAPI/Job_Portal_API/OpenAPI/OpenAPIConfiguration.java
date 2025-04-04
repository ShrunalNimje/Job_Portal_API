package my.mood.JobPortalAPI.Job_Portal_API.OpenAPI;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Portal API")
                        .version("1.0")
                        .description("A RESTful API for managing job listings, applications, and user authentication.")
                        .contact(new Contact().name("Shrunal Nimje").email("shrunalnimje@gmail.com")))
                
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("local"),
                		new Server().url("http://localhost:8082").description("live")))
                
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                
                .components(new Components()
                    .addSecuritySchemes("Bearer Authentication",
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
 
    }
}
