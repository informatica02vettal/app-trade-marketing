package ve.com.vettal.trademarketing.integration.productosapi.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.integration.productosapi.dto.ProductoApiDto;
import ve.com.vettal.trademarketing.integration.productosapi.dto.ProductosApiEnvelopeDto;

/**
 * Consume la API de productos del ERP
 * (GET .../api/v1.php?table=lista_prod_pw&action=list&apikey=...).
 * app-trade-marketing no tiene una conexión de base de datos al ERP.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductosApiClient {

	private static final String ACTION_LIST = "list";

	private final RestClient productosApiRestClient;

	@Value("${app.productos-api.api-key}")
	private String apiKey;

	@Value("${app.productos-api.tabla:lista_prod_pw}")
	private String tabla;

	public List<ProductoApiDto> listarTodos() {
		try {
			ProductosApiEnvelopeDto<List<ProductoApiDto>> respuesta = productosApiRestClient.get()
					.uri(uriBuilder -> uriBuilder
							.queryParam("table", tabla)
							.queryParam("action", ACTION_LIST)
							.queryParam("apikey", apiKey)
							.build())
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
						throw new BusinessException(
								"La API de productos rechazó la solicitud (HTTP " + response.getStatusCode().value() + ")");
					})
					.onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
						throw new BusinessException("La API de productos no está disponible en este momento");
					})
					.body(new ParameterizedTypeReference<ProductosApiEnvelopeDto<List<ProductoApiDto>>>() {
					});

			return respuesta != null && respuesta.getData() != null ? respuesta.getData() : List.of();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Error consultando la API de productos", ex);
			throw new BusinessException("No fue posible consultar el servicio de productos");
		}
	}
}
