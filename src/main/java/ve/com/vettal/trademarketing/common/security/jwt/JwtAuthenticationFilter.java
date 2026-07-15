package ve.com.vettal.trademarketing.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(AUTH_HEADER);

		if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(BEARER_PREFIX.length());

		try {
			String username = jwtService.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// El usuario pudo ser desactivado después de emitido este token
				// (JWT sin estado: no se invalida solo al desactivar la cuenta).
				// Se corta aquí mismo para que el bloqueo aplique de inmediato en
				// la siguiente petición, en vez de esperar a que el token expire.
				if (!userDetails.isEnabled()) {
					SecurityContextHolder.clearContext();
					log.warn("Petición rechazada: usuario bloqueado '{}' en {} {}", username, request.getMethod(), request.getRequestURI());
					responderUsuarioBloqueado(request, response);
					return;
				}

				if (jwtService.isTokenValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
			log.warn("Token inválido o expirado en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
	}

	// Mismo estilo que JwtAuthenticationEntryPoint: JSON armado a mano para no
	// depender de si el proyecto usa Jackson 2 o 3.
	private void responderUsuarioBloqueado(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = """
				{"status":401,"message":"Tu usuario está bloqueado. Contacta a un administrador.","timestamp":"%s","path":"%s"}"""
				.formatted(LocalDateTime.now(), escapeJson(request.getRequestURI()));

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(body);
	}

	private String escapeJson(String value) {
		return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
