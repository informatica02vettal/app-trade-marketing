package ve.com.vettal.trademarketing.features.objetivosvisita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoVisitaSubtipoRequestDto {

	@NotNull(message = "El tipo de objetivo es obligatorio")
	private Long tipoId;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	private Boolean activo;
}
