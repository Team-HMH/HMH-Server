package sopt.org.hmh.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sopt.org.hmh.global.auth.jwt.JwtConstants;

@Configuration
@SecurityScheme(
        name = JwtConstants.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.APIKEY,
        description = "Bearer 토큰은 Bearer~ 를 붙여주세요."
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("HMH Project")
                .description("HMH Project API Document")
                .version("1.0.0");

        return new OpenAPI()
                .info(info);
    }
}