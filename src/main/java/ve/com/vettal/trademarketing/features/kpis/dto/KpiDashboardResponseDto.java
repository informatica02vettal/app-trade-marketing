package ve.com.vettal.trademarketing.features.kpis.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiDashboardResponseDto {

	private LocalDate fecha;
	private int visitasPlanificadas;
	private int visitasEjecutadas;
	private double cumplimientoVisitasPct;
	private int clientesAtendidos;
	private double presenciaMarcaPct;
	private double participacionAnaquelPct;
	private int oportunidadesDetectadas;
	private int solicitudesGeneradas;
	private List<CoberturaRegionDto> coberturaPorRegion;
	private List<CumplimientoMercaderistaDto> cumplimientoPorMercaderista;
}
