package ve.com.vettal.trademarketing.features.visitas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteProspectoRequestDto {

	@NotNull(message = "La visita es obligatoria")
	private Long visitaId;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "El RIF es obligatorio")
	private String rif;

	@NotBlank(message = "El WhatsApp es obligatorio")
	private String whatsapp;

	private String telefono;

	@NotBlank(message = "La foto de la fachada es obligatoria")
	private String fotoFachadaUrl;

	@NotBlank(message = "La foto del interior es obligatoria")
	private String fotoInteriorUrl;

	private String marcasCompetencia;
}
