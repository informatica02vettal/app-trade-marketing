package ve.com.vettal.trademarketing.features.eventos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoLeadResponseDto {

	private Long id;
	private String nombre;
	private String empresa;
	private String cargo;
	private String telefono;
	private String correo;
}
