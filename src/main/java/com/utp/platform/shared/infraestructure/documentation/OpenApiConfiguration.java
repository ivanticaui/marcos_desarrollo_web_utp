package com.utp.platform.shared.infraestructure.documentation;
import org.springframework.context.annotation.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
@Configuration
public class OpenApiConfiguration {
    @Bean public OpenAPI learningPlatformOpenAPI() {
        return new OpenAPI().info(new Info().title("Learning Platform API").version("v1").description("Student, course and enrollment management using DDD and CQRS"));
    }
}
