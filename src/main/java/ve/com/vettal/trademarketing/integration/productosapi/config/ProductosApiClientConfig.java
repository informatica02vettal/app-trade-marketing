package ve.com.vettal.trademarketing.integration.productosapi.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Cliente HTTP hacia la API de productos del ERP
 * (GET .../api/v1.php?table=lista_prod_pw&action=list&apikey=...),
 * autenticada con un apikey por query param (no HTTP Basic Auth).
 */
@Configuration
public class ProductosApiClientConfig {

	@Value("${app.productos-api.base-url}")
	private String baseUrl;

	@Value("${app.productos-api.connect-timeout-ms}")
	private long connectTimeoutMs;

	@Value("${app.productos-api.read-timeout-ms}")
	private long readTimeoutMs;

	@Bean
	public RestClient productosApiRestClient() {
		HttpClient httpClient = HttpClient.newBuilder()
				.connectTimeout(Duration.ofMillis(connectTimeoutMs))
				.build();

		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
		requestFactory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

		return RestClient.builder()
				.baseUrl(baseUrl)
				.requestFactory(requestFactory)
				.build();
	}
}
