package ve.com.vettal.trademarketing.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Deja un rastro de cada petición HTTP (método, ruta, usuario autenticado,
 * status de respuesta y duración) en el log de la aplicación. Al vivir en la
 * cadena de filtros, cubre automáticamente todos los módulos/controladores
 * sin necesidad de instrumentar cada uno por separado.
 *
 * Necesita quedar ANTES que el filtro de Spring Security (que se registra
 * como un único filtro a nivel de servlet, muy temprano en la cadena): sin
 * este @Order, quedaba detrás de él y las peticiones rechazadas por
 * seguridad (401/403 antes de llegar a un controlador) no dejaban rastro.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		long inicio = System.currentTimeMillis();
		try {
			filterChain.doFilter(request, response);
		} finally {
			if (!esRutaSilenciosa(request.getRequestURI())) {
				long duracionMs = System.currentTimeMillis() - inicio;
				log.info("{} {} -> {} ({} ms) [{}]", request.getMethod(), request.getRequestURI(),
						response.getStatus(), duracionMs, usuarioActual());
			}
		}
	}

	// Los archivos servidos como imágenes (vía <img src>) y los recursos de
	// Swagger/OpenAPI se piden muy seguido y no aportan valor de negocio —
	// se excluyen para no ahogar el log con ruido.
	private boolean esRutaSilenciosa(String uri) {
		return uri.startsWith("/files/") || uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs");
	}

	private String usuarioActual() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null && auth.isAuthenticated() ? auth.getName() : "anónimo";
	}
}
