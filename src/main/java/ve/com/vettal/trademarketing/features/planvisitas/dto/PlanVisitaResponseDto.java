package ve.com.vettal.trademarketing.features.planvisitas.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;
import ve.com.vettal.trademarketing.features.planvisitas.model.TipoVisita;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanVisitaResponseDto {

	private Long id;
	private String erpClienteId;
	private Long sucursalId;
	private String sucursalNombre;
	private String clienteNombre;
	private Long usuarioId;
	private String usuarioNombre;
	private String region;
	private LocalDate fechaProgramada;
	private String horaProgramada;
	private String objetivo;
	private Long objetivoTipoId;
	private String objetivoTipoNombre;
	private Long objetivoSubtipoId;
	private String objetivoSubtipoNombre;
	private TipoVisita tipoVisita;
	private EstadoPlanVisita estado;
}
