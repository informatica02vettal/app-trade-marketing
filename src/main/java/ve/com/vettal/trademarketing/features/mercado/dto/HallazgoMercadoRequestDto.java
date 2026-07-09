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

	@NotNull(message = "El tipo de hallazgo es obligatorio")
	private TipoHallazgo tipo;

	private String erpClienteId;

	private String clienteNombre;

	private String categoriaProducto;

	private String marca;

	private String marcaCompetencia;

	private String oportunidadTexto;

	private String detalle;

	private List<HallazgoProductoRequestDto> productos;

	private List<HallazgoMaterialRequestDto> materiales;
}
