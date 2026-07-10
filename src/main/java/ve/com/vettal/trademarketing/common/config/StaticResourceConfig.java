package ve.com.vettal.trademarketing.common.config;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ve.com.vettal.trademarketing.features.archivos.constants.ArchivoConstants;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

	@Value("${app.storage.base-path}")
	private String basePathConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = Path.of(basePathConfig).toAbsolutePath().normalize().toUri().toString();
		registry.addResourceHandler(ArchivoConstants.RESOURCE_HANDLER_PATTERN)
				.addResourceLocations(location);
	}
}
