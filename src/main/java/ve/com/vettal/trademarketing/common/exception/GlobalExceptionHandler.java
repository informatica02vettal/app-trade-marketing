package ve.com.vettal.trademarketing.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
		return build(ex.getStatus(), ex.getMessage(), request, null);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return build(HttpStatus.BAD_REQUEST, "Error de validación", request, errors);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
		return build(HttpStatus.FORBIDDEN, "No tiene permisos para realizar esta acción", request, null);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
		return build(HttpStatus.UNAUTHORIZED, "Credenciales inválidas o token expirado", request, null);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
		return build(HttpStatus.CONFLICT, "Conflicto de integridad de datos", request, null);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiResponseDto<Void>> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
		return build(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), request, null);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception ex, HttpServletRequest request) {
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
