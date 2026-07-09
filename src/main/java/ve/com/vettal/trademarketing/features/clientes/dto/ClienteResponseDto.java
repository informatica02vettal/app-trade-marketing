package ve.com.vettal.trademarketing.features.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refleja el contrato actual del endpoint de clientes de vettal-backend
 * (id + nombre). Se enriquece con la asignación de mercaderista, que es
 * propia de app-trade-marketing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {

	private String id;
	private String nombre;
	private Long mercaderistaAsignadoId;
	private String mercaderistaAsignadoNombre;
}
