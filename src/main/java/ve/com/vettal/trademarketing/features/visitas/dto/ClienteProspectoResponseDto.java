package ve.com.vettal.trademarketing.features.visitas.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteProspectoResponseDto {

	private Long id;
	private Long visitaId;
	private String nombre;
	private String rif;
	private String whatsapp;
	private String telefono;
	private String fotoFachadaUrl;
	private String fotoInteriorUrl;
	private String marcasCompetencia;
	private Long usuarioId;
	private String usuarioNombre;
	private LocalDateTime createdAt;
}
