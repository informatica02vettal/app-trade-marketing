package ve.com.vettal.trademarketing.features.eventos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoLeadRequestDto {

	@NotBlank(message = "El nombre del lead es obligatorio")
	private String nombre;

	private String empresa;

	private String cargo;

	private String telefono;

	private String correo;
}
