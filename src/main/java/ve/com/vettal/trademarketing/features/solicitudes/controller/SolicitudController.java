package ve.com.vettal.trademarketing.features.solicitudes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.constants.SolicitudConstants;
import ve.com.vettal.trademarketing.features.solicitudes.dto.CambiarEstadoRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.service.SolicitudService;

@RestController
@RequestMapping("/api/v1/solicitudes")
@RequiredArgsConstructor
@Tag(name = "Solicitudes")
@SecurityRequirement(name = "bearerAuth")
public class SolicitudController {

	private final SolicitudService solicitudService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<SolicitudResponseDto>>> listar(
			@RequestParam(required = false) EstadoSolicitud estado,
			@RequestParam(required = false) Long solicitanteId,
			@RequestParam(required = false) Long visitaId) {
		List<SolicitudResponseDto> solicitudes = solicitudService.listar(estado, solicitanteId, visitaId);
		return ResponseEntity.ok(ApiResponseDto.ok(solicitudes, "Solicitudes obtenidas"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> buscarPorId(@PathVariable Long id) {
		SolicitudResponseDto solicitud = solicitudService.buscarPorId(id);
		return ResponseEntity.ok(ApiResponseDto.ok(solicitud, "Solicitud obtenida"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> crear(
			@Valid @RequestBody SolicitudRequestDto request) {
		SolicitudResponseDto solicitud = solicitudService.crear(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.created(solicitud, "Solicitud creada"));
	}

	@PatchMapping("/{id}/estado")
	@PreAuthorize(SolicitudConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<SolicitudResponseDto>> cambiarEstado(
			@PathVariable Long id,
			@Valid @RequestBody CambiarEstadoRequestDto request) {
		SolicitudResponseDto solicitud = solicitudService.cambiarEstado(id, request.getEstado());
		return ResponseEntity.ok(ApiResponseDto.ok(solicitud, "Estado de la solicitud actualizado"));
	}
}
