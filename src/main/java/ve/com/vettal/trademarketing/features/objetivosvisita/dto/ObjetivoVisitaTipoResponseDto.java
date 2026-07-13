package ve.com.vettal.trademarketing.features.objetivosvisita.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoVisitaTipoResponseDto {

	private Long id;
	private String nombre;
	private boolean activo;
}
