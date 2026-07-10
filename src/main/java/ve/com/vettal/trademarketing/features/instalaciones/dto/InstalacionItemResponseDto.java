package ve.com.vettal.trademarketing.features.instalaciones.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionItemResponseDto {

	private Long id;
	private Long materialId;
	private String materialNombre;
	private List<String> fotos;
}
