package ve.com.vettal.trademarketing.common.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Se construye el JSON a mano (en vez de inyectar un ObjectMapper) para no
 * acoplarse a si el proyecto usa Jackson 2 o Jackson 3 — Spring Boot 4 usa
 * Jackson 3 (tools.jackson.*) por defecto, mientras que jjwt-jackson trae
 * Jackson 2 (com.fasterxml.jackson.*) como dependencia transitiva aparte.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException {
		String body = """
				{"status":401,"message":"No autenticado: se requiere un token válido","timestamp":"%s","path":"%s"}"""
				.formatted(LocalDateTime.now(), escapeJson(request.getRequestURI()));

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(body);
	}

	private String escapeJson(String value) {
		return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
