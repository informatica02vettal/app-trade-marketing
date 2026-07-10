package ve.com.vettal.trademarketing.features.solicitudes.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudItemRequestDto {

	@NotNull(message = "El material es obligatorio")
	private Long materialId;

	private String medidas;

	private String ubicacion;

	private List<String> fotos;
}
