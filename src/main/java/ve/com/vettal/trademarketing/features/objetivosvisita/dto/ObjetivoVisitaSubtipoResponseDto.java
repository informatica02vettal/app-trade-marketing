package ve.com.vettal.trademarketing.features.objetivosvisita.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoVisitaSubtipoResponseDto {

	private Long id;
	private Long tipoId;
	private String tipoNombre;
	private String nombre;
	private boolean activo;
}
