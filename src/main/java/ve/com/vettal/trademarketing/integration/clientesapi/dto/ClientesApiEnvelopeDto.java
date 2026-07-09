package ve.com.vettal.trademarketing.integration.clientesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el wrapper {@code ApiResponseDto} de vettal-backend para poder
 * deserializar su respuesta ({@code status}, {@code message}, {@code data}).
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientesApiEnvelopeDto<T> {

	private int status;
	private String message;
	private T data;
}
