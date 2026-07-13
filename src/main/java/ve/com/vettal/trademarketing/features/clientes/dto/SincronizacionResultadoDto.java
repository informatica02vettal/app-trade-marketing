package ve.com.vettal.trademarketing.features.clientes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SincronizacionResultadoDto {

	private int clientesSincronizados;
	private int sucursalesSincronizadas;
	private LocalDateTime sincronizadoEn;
}
