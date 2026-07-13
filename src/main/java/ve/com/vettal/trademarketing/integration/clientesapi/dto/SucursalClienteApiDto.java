package ve.com.vettal.trademarketing.integration.clientesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el contrato de {@code SucursalClienteResponseDto} de vettal-backend
 * (GET /api/v1/clientes/sucursales): sucursales asociadas a un cliente.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SucursalClienteApiDto {

	private Integer id;
	private String idVendedor;
	private String idCliente;
	private String nombreSucursal;
	private String direccionSucursal;
	private String estado;
	private String ciudad;
}
