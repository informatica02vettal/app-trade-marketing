package ve.com.vettal.trademarketing.features.clientes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Listado completo de clientes activos con datos de ubicación, obtenido de
 * vettal-backend (GET /api/v1/clientes/listado). Se enriquece con la
 * asignación de mercaderista, propia de app-trade-marketing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteListadoResponseDto {

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
	private Long mercaderistaAsignadoId;
	private String mercaderistaAsignadoNombre;
}
