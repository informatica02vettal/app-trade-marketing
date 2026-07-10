package ve.com.vettal.trademarketing.features.catalogos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaMaterialResponseDto {

	private Long id;
	private FamiliaMaterial familia;
	private String nombre;
}
