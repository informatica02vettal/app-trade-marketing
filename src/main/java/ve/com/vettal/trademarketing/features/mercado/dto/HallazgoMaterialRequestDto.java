package ve.com.vettal.trademarketing.features.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoMaterialRequestDto {

	private String material;

	private String marca;

	private String fotoUrl;
}
