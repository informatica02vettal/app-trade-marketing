package ve.com.vettal.trademarketing.features.mercado.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoProductoResponseDto {

	private Long id;
	private String marca;
	private String modelo;
	private BigDecimal precio;
	private String fotoUrl;
}
