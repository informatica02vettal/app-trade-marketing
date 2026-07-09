package ve.com.vettal.trademarketing.features.solicitudes.dto;

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
	private String nombre;
	private String medidas;
	private String ubicacion;
	private String fotoUrl;
}
