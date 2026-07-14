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
	private Long visitaId;
	private TipoHallazgo tipo;
	private Long marcaId;
	private String marcaNombre;
	private Long productoErpId;
	private String productoErpCodigo;
	private String productoErpNombre;
	private Long categoriaProductoId;
	private String categoriaProductoNombre;
	private String marcaCompetencia;
	private String observacionTexto;
	private String detalle;
	private Long usuarioId;
	private String usuarioNombre;
	private List<HallazgoProductoResponseDto> productos;
	private List<HallazgoMaterialResponseDto> materiales;
	private LocalDateTime createdAt;
}
