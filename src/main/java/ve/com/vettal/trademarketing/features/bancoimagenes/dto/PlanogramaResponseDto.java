package ve.com.vettal.trademarketing.features.bancoimagenes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanogramaResponseDto {

	private Long id;
	private String tipoExhibidor;
	private String nombre;
	private String imagenUrl;
	private LocalDateTime createdAt;
}
