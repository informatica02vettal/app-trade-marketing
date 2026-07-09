package ve.com.vettal.trademarketing.features.mercado.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoProductoRequestDto {

	private String marca;

	private String modelo;

	private BigDecimal precio;

	private String fotoUrl;
}
