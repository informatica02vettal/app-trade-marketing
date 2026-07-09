package ve.com.vettal.trademarketing.features.asignaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionClienteResponseDto {

	private Long id;
	private String erpClienteId;
	private String clienteNombre;
	private Long usuarioId;
	private String usuarioNombre;
	private String region;
	private boolean activo;
}
