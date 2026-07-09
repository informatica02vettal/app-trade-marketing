package ve.com.vettal.trademarketing.features.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.usuarios.model.RolUsuario;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {

	private Long id;
	private String nombre;
	private String email;
	private String region;
	private String ejecutivoAsociado;
	private RolUsuario rol;
	private boolean activo;
}
