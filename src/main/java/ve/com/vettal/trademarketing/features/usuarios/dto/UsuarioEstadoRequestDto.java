package ve.com.vettal.trademarketing.features.usuarios.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEstadoRequestDto {

	@NotNull(message = "El estado activo/inactivo es obligatorio")
	private Boolean activo;
}
