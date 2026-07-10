package ve.com.vettal.trademarketing.features.catalogos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialResponseDto {

	private Long id;
	private String nombre;
	private Long categoriaId;
	private String categoriaNombre;
	private Long marcaId;
	private boolean requiereMedidas;
	private boolean requiereUbicacion;
	private int minimoFotos;
}
