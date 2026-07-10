package ve.com.vettal.trademarketing.features.solicitudes.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudItemResponseDto {

	private Long id;
	private Long materialId;
	private String materialNombre;
	private String medidas;
	private String ubicacion;
	private List<String> fotos;
}
