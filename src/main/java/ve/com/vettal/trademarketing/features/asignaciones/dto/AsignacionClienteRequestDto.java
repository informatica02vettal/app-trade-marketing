package ve.com.vettal.trademarketing.features.asignaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionClienteRequestDto {

	@NotBlank(message = "El id del cliente ERP es obligatorio")
	private String erpClienteId;

	private String clienteNombre;

	@NotNull(message = "El usuario mercaderista es obligatorio")
	private Long usuarioId;

	private String region;
}
