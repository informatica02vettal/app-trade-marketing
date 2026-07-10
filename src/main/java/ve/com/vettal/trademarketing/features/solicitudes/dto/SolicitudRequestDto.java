package ve.com.vettal.trademarketing.features.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.solicitudes.model.CategoriaSolicitud;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudRequestDto {

	@NotNull(message = "La visita es obligatoria")
	private Long visitaId;

	@NotNull(message = "La categoría es obligatoria")
	private CategoriaSolicitud categoria;

	@NotNull(message = "La marca es obligatoria")
	private Long marcaId;

	private String observaciones;

	@NotEmpty(message = "La solicitud debe tener al menos un item")
	@Valid
	private List<SolicitudItemRequestDto> items;
}
