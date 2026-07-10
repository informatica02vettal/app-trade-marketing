package ve.com.vettal.trademarketing.features.instalaciones.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.instalaciones.model.CategoriaInstalacion;
import ve.com.vettal.trademarketing.features.instalaciones.model.EstadoInstalacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionResponseDto {

	private Long id;
	private Long visitaId;
	private String erpClienteId;
	private String clienteNombre;
	private Long marcaId;
	private String marcaNombre;
	private CategoriaInstalacion categoria;
	private Long usuarioId;
	private String usuarioNombre;
	private String observaciones;
	private LocalDate fechaInstalacion;
	private EstadoInstalacion estado;
	private List<InstalacionItemResponseDto> items;
}
