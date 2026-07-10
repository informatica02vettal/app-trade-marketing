package ve.com.vettal.trademarketing.features.catalogos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarcaResponseDto {

	private Long id;
	private String codigo;
	private String nombre;
}
