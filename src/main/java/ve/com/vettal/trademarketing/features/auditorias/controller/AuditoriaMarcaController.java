package ve.com.vettal.trademarketing.features.auditorias.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.auditorias.dto.AuditoriaMarcaRequestDto;
import ve.com.vettal.trademarketing.features.auditorias.dto.AuditoriaMarcaResponseDto;
import ve.com.vettal.trademarketing.features.auditorias.service.AuditoriaMarcaService;

@RestController
@RequestMapping("/api/v1/auditorias")
@RequiredArgsConstructor
@Tag(name = "Auditorías de marca")
@SecurityRequirement(name = "bearerAuth")
public class AuditoriaMarcaController {

	private final AuditoriaMarcaService auditoriaMarcaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<AuditoriaMarcaResponseDto>>> listar(
			@RequestParam Long visitaId) {
		List<AuditoriaMarcaResponseDto> auditorias = auditoriaMarcaService.listarPorVisita(visitaId);
		return ResponseEntity.ok(ApiResponseDto.ok(auditorias, "Auditorías de marca obtenidas"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<AuditoriaMarcaResponseDto>> crear(
			@Valid @RequestBody AuditoriaMarcaRequestDto request) {
		AuditoriaMarcaResponseDto auditoria = auditoriaMarcaService.crear(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.created(auditoria, "Auditoría de marca registrada"));
	}
}
