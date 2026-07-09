package ve.com.vettal.trademarketing.features.bancoimagenes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanogramaRequestDto {

	@NotBlank(message = "El tipo de exhibidor es obligatorio")
	private String tipoExhibidor;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "La URL de la imagen es obligatoria")
	private String imagenUrl;
}
