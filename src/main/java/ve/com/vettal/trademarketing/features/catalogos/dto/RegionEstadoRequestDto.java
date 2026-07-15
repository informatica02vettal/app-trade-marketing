package ve.com.vettal.trademarketing.features.catalogos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionEstadoRequestDto {

	@NotNull(message = "El estado activo/inactivo es obligatorio")
	private Boolean activo;
}
