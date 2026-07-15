package ve.com.vettal.trademarketing.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;

// Todo error que pasa por acá quedaba sin ningún rastro en el log del
// servidor (solo se convertía en respuesta HTTP) — cada handler ahora deja
// constancia de qué pasó y en qué endpoint, para poder diagnosticar sin
// tener que reproducir el error.
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		log.warn("Recurso no encontrado en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
		log.warn("Entidad no encontrada en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
		log.warn("Regla de negocio rechazada en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(ex.getStatus(), ex.getMessage(), request, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		log.warn("Validación fallida en {} {}: {}", request.getMethod(), request.getRequestURI(), errors);
		return build(HttpStatus.BAD_REQUEST, "Error de validación", request, errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
		log.warn("Restricción violada en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
		log.warn("Acceso denegado en {} {}", request.getMethod(), request.getRequestURI());
		return build(HttpStatus.FORBIDDEN, "No tiene permisos para realizar esta acción", request, null);
	}

	// Más específico que AuthenticationException — Spring elige este handler
	// para un usuario desactivado en vez del genérico de abajo, así el login
	// puede distinguir "bloqueado" de "contraseña incorrecta".
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleDisabled(DisabledException ex, HttpServletRequest request) {
		log.warn("Intento de acceso de un usuario bloqueado en {} {}", request.getMethod(), request.getRequestURI());
		return build(HttpStatus.UNAUTHORIZED, "Tu usuario está bloqueado. Contacta a un administrador.", request, null);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
		log.warn("Fallo de autenticación en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(HttpStatus.UNAUTHORIZED, "Credenciales inválidas o token expirado", request, null);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleMaxUploadSize(MaxUploadSizeExceededException ex, HttpServletRequest request) {
		log.warn("Archivo demasiado grande en {} {}", request.getMethod(), request.getRequestURI());
		return build(HttpStatus.BAD_REQUEST, "El archivo excede el tamaño máximo permitido (10MB)", request, null);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
		log.warn("Conflicto de integridad de datos en {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
		return build(HttpStatus.CONFLICT, "Conflicto de integridad de datos", request, null);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
		log.warn("{} en {} {}: {}", ex.getStatusCode(), request.getMethod(), request.getRequestURI(), ex.getReason());
		return build(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), request, null);
	}

	// El único caso que se deja en ERROR con stack trace completo: los demás
	// son rechazos "esperados" (datos inválidos, permisos, recursos
	// inexistentes); llegar hasta acá significa un error no previsto.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception ex, HttpServletRequest request) {
		log.error("Error inesperado en {} {}", request.getMethod(), request.getRequestURI(), ex);
		return build(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error inesperado", request, null);
	}

	private ResponseEntity<ApiResponseDto<Void>> build(HttpStatus status, String message, HttpServletRequest request, Map<String, String> errors) {
		ApiResponseDto<Void> body = ApiResponseDto.<Void>builder()
				.status(status.value())
				.message(message)
				.timestamp(LocalDateTime.now())
				.path(request.getRequestURI())
				.errors(errors)
				.build();
		return ResponseEntity.status(status).body(body);
	}
}
