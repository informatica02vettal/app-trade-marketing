package ve.com.vettal.trademarketing.features.solicitudes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudItemRequestDto {

	@NotBlank(message = "El nombre del item es obligatorio")
	private String nombre;

	private String medidas;

	private String ubicacion;

	private String fotoUrl;
}
