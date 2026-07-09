package ve.com.vettal.trademarketing.features.mercado.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoMercadoResponseDto {

	private Long id;
	private TipoHallazgo tipo;
	private String erpClienteId;
	private String clienteNombre;
	private String categoriaProducto;
	private String marca;
	private String marcaCompetencia;
	private String oportunidadTexto;
	private String detalle;
	private Long usuarioId;
	private String usuarioNombre;
	private List<HallazgoProductoResponseDto> productos;
	private List<HallazgoMaterialResponseDto> materiales;
	private LocalDateTime createdAt;
}
