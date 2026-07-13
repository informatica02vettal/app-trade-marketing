package ve.com.vettal.trademarketing.features.planvisitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVisitaReprogramarRequestDto {

	@NotNull(message = "La fecha programada es obligatoria")
	private LocalDate fechaProgramada;

	@NotBlank(message = "La hora programada es obligatoria")
	private String horaProgramada;
}
