package ve.com.vettal.trademarketing.features.instalaciones.dto;

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
	private String material;
	private String fotoUrl;
}
