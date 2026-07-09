package ve.com.vettal.trademarketing.features.kpis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CumplimientoMercaderistaDto {

	private Long usuarioId;
	private String usuarioNombre;
	private int visitasPlanificadas;
	private int visitasEjecutadas;
	private double cumplimientoPct;
}
