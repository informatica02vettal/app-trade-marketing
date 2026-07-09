package ve.com.vettal.trademarketing.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

/**
 * Resuelve el {@link UsuarioModel} autenticado a partir del email guardado
 * como subject del JWT. Los servicios lo usan para acotar consultas al
 * mercaderista actual (por ejemplo, "mis visitas", "mi plan del día").
 */
@Component
@RequiredArgsConstructor
public class AuthenticatedUserProvider {

	private final UsuarioRepository usuarioRepository;

	public UsuarioModel getUsuarioActual() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario autenticado no encontrado: " + email));
	}

	public boolean esAdminOSupervisor() {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_SUPERVISOR"));
	}
}
