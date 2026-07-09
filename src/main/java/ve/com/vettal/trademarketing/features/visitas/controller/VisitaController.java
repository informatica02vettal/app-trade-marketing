package ve.com.vettal.trademarketing.features.visitas.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.visitas.constants.VisitaConstants;
import ve.com.vettal.trademarketing.features.visitas.dto.EvidenciaFotoRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.EvidenciaFotoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaCheckinRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaCheckoutRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaResponseDto;
import ve.com.vettal.trademarketing.features.visitas.service.VisitaService;

@RestController
@RequestMapping(VisitaConstants.API_BASE_PATH_VISITAS)
@RequiredArgsConstructor
@Tag(name = "Visitas")
@SecurityRequirement(name = "bearerAuth")
public class VisitaController {

	private final VisitaService visitaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<VisitaResponseDto>>> listar(
			@RequestParam(required = false) Long usuarioId) {
		List<VisitaResponseDto> visitas = visitaService.listar(usuarioId);
		return ResponseEntity.ok(ApiResponseDto.ok(visitas, "Visitas obtenidas"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<VisitaResponseDto>> checkin(
			@Valid @RequestBody VisitaCheckinRequestDto request) {
		VisitaResponseDto visita = visitaService.checkin(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(visita, "Check-in registrado"));
	}

	@PostMapping("/{id}/fotos")
	public ResponseEntity<ApiResponseDto<EvidenciaFotoResponseDto>> agregarFoto(
			@PathVariable Long id,
			@Valid @RequestBody EvidenciaFotoRequestDto request) {
		EvidenciaFotoResponseDto foto = visitaService.agregarFoto(id, request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(foto, "Evidencia fotografica agregada"));
	}

	@PatchMapping("/{id}/checkout")
	public ResponseEntity<ApiResponseDto<VisitaResponseDto>> checkout(
			@PathVariable Long id,
			@RequestBody(required = false) VisitaCheckoutRequestDto request) {
		VisitaCheckoutRequestDto body = request != null ? request : new VisitaCheckoutRequestDto();
		VisitaResponseDto visita = visitaService.checkout(id, body);
		return ResponseEntity.ok(ApiResponseDto.ok(visita, "Check-out registrado"));
	}
}
