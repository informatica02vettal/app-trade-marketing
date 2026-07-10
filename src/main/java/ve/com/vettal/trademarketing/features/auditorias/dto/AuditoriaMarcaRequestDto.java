package ve.com.vettal.trademarketing.features.auditorias.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.auditorias.model.EstadoExhibidor;
import ve.com.vettal.trademarketing.features.auditorias.model.EstadoPop;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaMarcaRequestDto {

	@NotNull(message = "El id de la visita es obligatorio")
	private Long visitaId;

	@NotNull(message = "La marca es obligatoria")
	private Long marcaId;

	@NotNull(message = "El porcentaje de presencia es obligatorio")
	@Min(value = 0, message = "El porcentaje de presencia no puede ser negativo")
	@Max(value = 100, message = "El porcentaje de presencia no puede superar 100")
	private Integer presenciaPct;

	@NotNull(message = "Los frentes de la marca son obligatorios")
	@Min(value = 0, message = "Los frentes de la marca no pueden ser negativos")
	private Integer frentesVettal;

	@NotNull(message = "Los frentes totales son obligatorios")
	@Min(value = 0, message = "Los frentes totales no pueden ser negativos")
	private Integer frentesTotales;

	private boolean exhibidorMarca;

	private boolean productoExhibidor;

	private boolean productoAnaquel;

	private boolean avisoFachada;

	private boolean avisoPared;

	private boolean banderines;

	private boolean rotulado;

	private boolean empleadosUniforme;

	@NotNull(message = "El estado de los exhibidores es obligatorio")
	private EstadoExhibidor estadoExhibidores;

	@NotNull(message = "El estado del material POP es obligatorio")
	private EstadoPop estadoPop;

	private String competenciaDetectada;

	private String oportunidad;
}
