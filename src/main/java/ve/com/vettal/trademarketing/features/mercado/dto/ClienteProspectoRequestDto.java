package ve.com.vettal.trademarketing.features.mercado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteProspectoRequestDto {

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "El RIF es obligatorio")
	private String rif;

	@NotBlank(message = "El WhatsApp es obligatorio")
	private String whatsapp;

	private String telefono;

	@NotNull(message = "La latitud GPS es obligatoria")
	private BigDecimal gpsLat;

	@NotNull(message = "La longitud GPS es obligatoria")
	private BigDecimal gpsLng;

	@NotBlank(message = "La foto de la fachada es obligatoria")
	private String fotoFachadaUrl;

	@NotBlank(message = "La foto del interior es obligatoria")
	private String fotoInteriorUrl;

	private String marcasCompetencia;
}
