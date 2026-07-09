package ve.com.vettal.trademarketing.features.instalaciones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionItemRequestDto {

	@NotBlank(message = "El material es obligatorio")
	private String material;

	private String fotoUrl;
}
