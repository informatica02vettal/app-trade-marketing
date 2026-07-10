package ve.com.vettal.trademarketing.features.instalaciones.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionItemRequestDto {

	@NotNull(message = "El material es obligatorio")
	private Long materialId;

	private List<String> fotos;
}
