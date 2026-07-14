package ve.com.vettal.trademarketing.features.catalogos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarcaCompetenciaResponseDto {

	private Long id;
	private Long marcaId;
	private String nombre;
	private boolean activo;
}
