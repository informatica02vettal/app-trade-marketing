package ve.com.vettal.trademarketing.features.kpis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoberturaRegionDto {

	private String region;
	private int visitasPlanificadas;
	private int visitasEjecutadas;
	private double cumplimientoPct;
}
