package ve.com.vettal.trademarketing.features.visitas.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitaCheckinRequestDto {

	private Long planId;

	private String erpClienteId;

	@NotBlank(message = "El nombre del cliente es obligatorio")
	private String clienteNombre;

	private String region;

	private String ejecutivoVentas;

	private BigDecimal checkinGpsLat;

	private BigDecimal checkinGpsLng;
}
