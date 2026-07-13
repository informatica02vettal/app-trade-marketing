package ve.com.vettal.trademarketing.integration.clientesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el contrato de {@code ClienteListadoResponseDto} de vettal-backend
 * (GET /api/v1/clientes/listado): listado completo de clientes activos con
 * datos de ubicación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteListadoApiDto {

	private String codigoCliente;
	private String rif;
	private String nombreFiscal;
	private String nombreComercial;
	private String direccionFiscal;
	private String telefonoPrincipal;
	private String celular;
	private String email;
	private String estado;
	private String ciudad;
	private String municipio;
	private LocalDateTime creacion;
}
