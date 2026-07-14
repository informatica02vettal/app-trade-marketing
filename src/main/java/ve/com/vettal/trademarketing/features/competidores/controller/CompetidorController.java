package ve.com.vettal.trademarketing.features.competidores.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.competidores.dto.CompetidorRequestDto;
import ve.com.vettal.trademarketing.features.competidores.dto.CompetidorResponseDto;
import ve.com.vettal.trademarketing.features.competidores.service.CompetidorService;

@RestController
@RequestMapping("/api/v1/competidores")
@RequiredArgsConstructor
@Tag(name = "Competidores (inteligencia de mercado en eventos y auditorías)")
@SecurityRequirement(name = "bearerAuth")
public class CompetidorController {

	private final CompetidorService competidorService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<CompetidorResponseDto>>> listarPorVisita(@RequestParam Long visitaId) {
		return ResponseEntity.ok(ApiResponseDto.ok(competidorService.listarPorVisita(visitaId), "Competidores obtenidos"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<CompetidorResponseDto>> crear(@Valid @RequestBody CompetidorRequestDto request) {
		CompetidorResponseDto competidor = competidorService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(competidor, "Competidor registrado"));
	}
}
