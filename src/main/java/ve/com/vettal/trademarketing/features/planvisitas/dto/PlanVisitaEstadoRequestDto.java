package ve.com.vettal.trademarketing.features.planvisitas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVisitaEstadoRequestDto {

	@NotNull(message = "El estado es obligatorio")
	private EstadoPlanVisita estado;
}
