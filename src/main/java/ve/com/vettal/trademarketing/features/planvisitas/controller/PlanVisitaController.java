package ve.com.vettal.trademarketing.features.planvisitas.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaEstadoRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaResponseDto;
import ve.com.vettal.trademarketing.features.planvisitas.service.PlanVisitaService;

@RestController
@RequestMapping("/api/v1/plan-visitas")
@RequiredArgsConstructor
@Tag(name = "Plan de visitas")
@SecurityRequirement(name = "bearerAuth")
public class PlanVisitaController {

	private final PlanVisitaService planVisitaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<PlanVisitaResponseDto>>> listar(
			@RequestParam(required = false) Long usuarioId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
		List<PlanVisitaResponseDto> planes = planVisitaService.listar(usuarioId, fecha);
		return ResponseEntity.ok(ApiResponseDto.ok(planes, "Plan de visitas obtenido"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<PlanVisitaResponseDto>> crear(
			@Valid @RequestBody PlanVisitaRequestDto request) {
		PlanVisitaResponseDto planVisita = planVisitaService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(planVisita, "Plan de visita creado"));
	}

	@PatchMapping("/{id}/estado")
	public ResponseEntity<ApiResponseDto<PlanVisitaResponseDto>> actualizarEstado(
			@PathVariable Long id,
			@Valid @RequestBody PlanVisitaEstadoRequestDto request) {
		PlanVisitaResponseDto planVisita = planVisitaService.actualizarEstado(id, request);
		return ResponseEntity.ok(ApiResponseDto.ok(planVisita, "Estado del plan de visita actualizado"));
	}
}
