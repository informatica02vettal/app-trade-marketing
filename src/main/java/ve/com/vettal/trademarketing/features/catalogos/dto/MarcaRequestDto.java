package ve.com.vettal.trademarketing.features.catalogos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRequestDto {

	@NotBlank(message = "El código es obligatorio")
	private String codigo;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
}
