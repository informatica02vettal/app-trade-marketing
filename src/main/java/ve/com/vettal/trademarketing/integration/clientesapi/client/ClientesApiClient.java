package ve.com.vettal.trademarketing.integration.clientesapi.client;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.integration.clientesapi.dto.ClienteApiDto;
import ve.com.vettal.trademarketing.integration.clientesapi.dto.ClientesApiEnvelopeDto;

/**
 * Consume el endpoint REST de clientes ya expuesto por vettal-backend
 * (GET /api/v1/clientes/search), autenticado con HTTP Basic Auth.
 * app-trade-marketing no tiene una conexión de base de datos al ERP.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ClientesApiClient {

	private static final String CLIENTES_SEARCH_PATH = "/api/v1/clientes/search";

	private final RestClient clientesApiRestClient;

	public List<ClienteApiDto> buscar(String id, String nombre) {
		try {
			ClientesApiEnvelopeDto<List<ClienteApiDto>> respuesta = clientesApiRestClient.get()
					.uri(uriBuilder -> uriBuilder
							.path(CLIENTES_SEARCH_PATH)
							.queryParamIfPresent("id", Optional.ofNullable(id))
							.queryParamIfPresent("nombre", Optional.ofNullable(nombre))
							.build())
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
						throw new BusinessException(
								"La API de clientes rechazó la solicitud (HTTP " + response.getStatusCode().value() + ")");
					})
					.onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
						throw new BusinessException("La API de clientes no está disponible en este momento");
					})
					.body(new ParameterizedTypeReference<ClientesApiEnvelopeDto<List<ClienteApiDto>>>() {
					});

			return respuesta != null && respuesta.getData() != null ? respuesta.getData() : List.of();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Error consultando la API de clientes", ex);
			throw new BusinessException("No fue posible consultar el servicio de clientes");
		}
	}
}
