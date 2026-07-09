package ve.com.vettal.trademarketing.features.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.usuarios.model.RolUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")
	private String email;

	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	private String password;

	private String region;

	private String ejecutivoAsociado;

	@NotNull(message = "El rol es obligatorio")
	private RolUsuario rol;

	private Boolean activo;
}
