package ve.com.vettal.trademarketing.features.visitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.visitas.model.CategoriaEvidencia;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaFotoRequestDto {

	@NotNull(message = "La categoria de la evidencia es obligatoria")
	private CategoriaEvidencia categoria;

	@NotBlank(message = "La URL de la foto es obligatoria")
	private String url;
}
