package ve.com.vettal.trademarketing.features.solicitudes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoRequestDto {

	@NotNull(message = "El estado es obligatorio")
	private EstadoSolicitud estado;
}
