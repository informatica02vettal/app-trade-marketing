package ve.com.vettal.trademarketing.features.asignaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.asignaciones.dto.AsignacionClienteRequestDto;
import ve.com.vettal.trademarketing.features.asignaciones.dto.AsignacionClienteResponseDto;
import ve.com.vettal.trademarketing.features.asignaciones.service.AsignacionClienteService;
import ve.com.vettal.trademarketing.features.usuarios.constants.UsuarioConstants;

@RestController
@RequestMapping("/api/v1/asignaciones")
@RequiredArgsConstructor
@Tag(name = "Asignaciones de clientes")
@SecurityRequirement(name = "bearerAuth")
public class AsignacionClienteController {

	private final AsignacionClienteService asignacionClienteService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<AsignacionClienteResponseDto>>> listar(
			@RequestParam(required = false) Long usuarioId,
			@RequestParam(required = false) String erpClienteId) {
		List<AsignacionClienteResponseDto> asignaciones;
		if (usuarioId != null) {
			asignaciones = asignacionClienteService.listarPorUsuario(usuarioId);
		} else if (erpClienteId != null) {
			asignaciones = asignacionClienteService.listarPorCliente(erpClienteId);
		} else {
			asignaciones = List.of();
		}
		return ResponseEntity.ok(ApiResponseDto.ok(asignaciones, "Asignaciones obtenidas"));
	}

	@PostMapping
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<AsignacionClienteResponseDto>> asignar(
			@Valid @RequestBody AsignacionClienteRequestDto request) {
		AsignacionClienteResponseDto asignacion = asignacionClienteService.asignar(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(asignacion, "Cliente asignado"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<Void>> desactivar(@PathVariable Long id) {
		asignacionClienteService.desactivar(id);
		return ResponseEntity.ok(ApiResponseDto.ok(null, "Asignación desactivada"));
	}
}
