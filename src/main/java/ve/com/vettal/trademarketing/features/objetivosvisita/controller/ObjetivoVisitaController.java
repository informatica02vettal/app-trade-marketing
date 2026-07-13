package ve.com.vettal.trademarketing.features.objetivosvisita.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.constants.ObjetivoVisitaConstants;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaSubtipoRequestDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaSubtipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaTipoRequestDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaTipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.service.ObjetivoVisitaService;

@RestController
@RequestMapping("/api/v1/objetivos-visita")
@RequiredArgsConstructor
@Tag(name = "Objetivos de visita")
@SecurityRequirement(name = "bearerAuth")
public class ObjetivoVisitaController {

	private final ObjetivoVisitaService objetivoVisitaService;

	@GetMapping("/tipos")
	public ResponseEntity<ApiResponseDto<List<ObjetivoVisitaTipoResponseDto>>> listarTipos() {
		return ResponseEntity.ok(ApiResponseDto.ok(objetivoVisitaService.listarTipos(), "Tipos de objetivo obtenidos"));
	}

	@PostMapping("/tipos")
	@PreAuthorize(ObjetivoVisitaConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<ObjetivoVisitaTipoResponseDto>> crearTipo(
			@Valid @RequestBody ObjetivoVisitaTipoRequestDto request) {
		ObjetivoVisitaTipoResponseDto tipo = objetivoVisitaService.crearTipo(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(tipo, "Tipo de objetivo creado"));
	}

	@PutMapping("/tipos/{id}")
	@PreAuthorize(ObjetivoVisitaConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<ObjetivoVisitaTipoResponseDto>> actualizarTipo(
			@PathVariable Long id, @Valid @RequestBody ObjetivoVisitaTipoRequestDto request) {
		return ResponseEntity.ok(ApiResponseDto.ok(objetivoVisitaService.actualizarTipo(id, request), "Tipo de objetivo actualizado"));
	}

	@GetMapping("/subtipos")
	public ResponseEntity<ApiResponseDto<List<ObjetivoVisitaSubtipoResponseDto>>> listarSubtipos(
			@RequestParam(required = false) Long tipoId) {
		return ResponseEntity.ok(ApiResponseDto.ok(objetivoVisitaService.listarSubtipos(tipoId), "Subtipos de objetivo obtenidos"));
	}

	@PostMapping("/subtipos")
	@PreAuthorize(ObjetivoVisitaConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<ObjetivoVisitaSubtipoResponseDto>> crearSubtipo(
			@Valid @RequestBody ObjetivoVisitaSubtipoRequestDto request) {
		ObjetivoVisitaSubtipoResponseDto subtipo = objetivoVisitaService.crearSubtipo(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(subtipo, "Subtipo de objetivo creado"));
	}

	@PutMapping("/subtipos/{id}")
	@PreAuthorize(ObjetivoVisitaConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<ObjetivoVisitaSubtipoResponseDto>> actualizarSubtipo(
			@PathVariable Long id, @Valid @RequestBody ObjetivoVisitaSubtipoRequestDto request) {
		return ResponseEntity.ok(
				ApiResponseDto.ok(objetivoVisitaService.actualizarSubtipo(id, request), "Subtipo de objetivo actualizado"));
	}
}
