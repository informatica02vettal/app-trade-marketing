package ve.com.vettal.trademarketing.features.productos.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SincronizacionProductosResultadoDto {

	private int productosSincronizados;
	private LocalDateTime sincronizadoEn;
}
