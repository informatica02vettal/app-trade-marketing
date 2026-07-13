package ve.com.vettal.trademarketing.features.planvisitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.planvisitas.model.TipoVisita;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVisitaRequestDto {

	private String erpClienteId;

	private Long sucursalId;

	@NotBlank(message = "El nombre del cliente es obligatorio")
	private String clienteNombre;

	private Long usuarioId;

	private String region;

	@NotNull(message = "La fecha programada es obligatoria")
	private LocalDate fechaProgramada;

	private String horaProgramada;

	private Long objetivoTipoId;

	private Long objetivoSubtipoId;

	private TipoVisita tipoVisita;
}
