package ve.com.vettal.trademarketing.features.mercado.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoMercadoRequestDto {

	@NotNull(message = "La visita es obligatoria")
	private Long visitaId;

	@NotNull(message = "El tipo de hallazgo es obligatorio")
	private TipoHallazgo tipo;

	private Long marcaId;

	private Long categoriaProductoId;

	private String marcaCompetencia;

	private String observacionTexto;

	private String detalle;

	private List<HallazgoProductoRequestDto> productos;

	private List<HallazgoMaterialRequestDto> materiales;
}
