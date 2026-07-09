package ve.com.vettal.trademarketing.integration.clientesapi.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP hacia el endpoint de clientes de vettal-backend
 * (GET /api/v1/clientes/search), autenticado con HTTP Basic Auth.
 * Esta app no abre ninguna conexión de base de datos al ERP.
 */
@Configuration
public class ClientesApiClientConfig {

	@Value("${app.clientes-api.base-url}")
	private String baseUrl;

	@Value("${app.clientes-api.username}")
	private String username;

	@Value("${app.clientes-api.password}")
	private String password;

	@Value("${app.clientes-api.connect-timeout-ms}")
	private long connectTimeoutMs;

	@Value("${app.clientes-api.read-timeout-ms}")
	private long readTimeoutMs;

	@Bean
	public RestClient clientesApiRestClient() {
		HttpClient httpClient = HttpClient.newBuilder()
				.connectTimeout(Duration.ofMillis(connectTimeoutMs))
				.build();

		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
		requestFactory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

		return RestClient.builder()
				.baseUrl(baseUrl)
				.requestFactory(requestFactory)
				.requestInterceptor(new BasicAuthenticationInterceptor(username, password))
				.build();
	}
}
