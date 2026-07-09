package ve.com.vettal.trademarketing.features.bancoimagenes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.TipoArchivo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArteMarcaResponseDto {

	private Long id;
	private String marca;
	private String nombre;
	private String archivoUrl;
	private TipoArchivo tipoArchivo;
	private LocalDateTime createdAt;
}
