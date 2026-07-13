package ve.com.vettal.trademarketing.features.clientes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalLocalResponseDto {

	private Long id;
	private Integer erpId;
	private String codigoCliente;
	private String idVendedor;
	private String nombreSucursal;
	private String direccionSucursal;
	private String estado;
	private String ciudad;
	private LocalDateTime sincronizadoEn;
}
