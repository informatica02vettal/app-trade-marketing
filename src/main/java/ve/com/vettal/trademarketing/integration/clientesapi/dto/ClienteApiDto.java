package ve.com.vettal.trademarketing.integration.clientesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el contrato real de {@code ClienteBResponseDto} de vettal-backend
 * (GET /api/v1/clientes/search): hoy solo expone id y nombre.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteApiDto {

	private String id;
	private String nombre;
}
