package ve.com.vettal.trademarketing.features.auditorias.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.auditorias.model.EstadoExhibidor;
import ve.com.vettal.trademarketing.features.auditorias.model.EstadoPop;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaMarcaResponseDto {

	private Long id;
	private Long visitaId;
	private Long marcaId;
	private String marcaNombre;
	private Integer presenciaPct;
	private Integer anaquelPct;
	private Integer frentesVettal;
	private Integer frentesTotales;
	private boolean exhibidorMarca;
	private boolean productoExhibidor;
	private boolean productoAnaquel;
	private boolean avisoFachada;
	private boolean avisoPared;
	private boolean banderines;
	private boolean rotulado;
	private boolean empleadosUniforme;
	private EstadoExhibidor estadoExhibidores;
	private EstadoPop estadoPop;
	private String competenciaDetectada;
	private String oportunidad;
	private boolean completa;
	private LocalDateTime createdAt;
}
