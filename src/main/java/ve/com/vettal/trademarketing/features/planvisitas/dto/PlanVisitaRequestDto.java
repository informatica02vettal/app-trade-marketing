package ve.com.vettal.trademarketing.features.planvisitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
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

	@NotBlank(message = "La hora programada es obligatoria")
	private String horaProgramada;

	private Long objetivoTipoId;

	private Long objetivoSubtipoId;

	private String comentario;

	private TipoVisita tipoVisita;

	/** Productos propios del ERP a auditar en esta visita (Auditoría de marca). */
	private List<Long> productoErpIds;
}
