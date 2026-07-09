package ve.com.vettal.trademarketing.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "App Trade Marketing API",
				version = "1.0.0",
				description = "API de planificación, ejecución y control de trade marketing de campo de Corporación Vettal."
		),
		servers = {
				@Server(url = "http://localhost:8081", description = "Desarrollo"),
				@Server(url = "https://api-trade-marketing.vettal.com.ve", description = "Producción")
		}
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
public class SwaggerConfig {
}
