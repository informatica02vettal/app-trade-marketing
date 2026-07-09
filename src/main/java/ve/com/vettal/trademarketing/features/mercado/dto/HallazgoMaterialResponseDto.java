package ve.com.vettal.trademarketing.features.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoMaterialResponseDto {

	private Long id;
	private String material;
	private String marca;
	private String fotoUrl;
}
