package ve.com.vettal.trademarketing.features.visitas.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.visitas.model.CategoriaEvidencia;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaFotoResponseDto {

	private Long id;
	private Long visitaId;
	private Long marcaId;
	private CategoriaEvidencia categoria;
	private String url;
	private LocalDateTime createdAt;
}
