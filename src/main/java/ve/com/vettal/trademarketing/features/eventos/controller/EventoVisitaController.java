package ve.com.vettal.trademarketing.features.eventos.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoVisitaRequestDto;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoVisitaResponseDto;
import ve.com.vettal.trademarketing.features.eventos.service.EventoVisitaService;

@RestController
@RequestMapping("/api/v1/eventos-visita")
@RequiredArgsConstructor
@Tag(name = "Eventos de visita")
@SecurityRequirement(name = "bearerAuth")
public class EventoVisitaController {

	private final EventoVisitaService eventoVisitaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<EventoVisitaResponseDto>> obtenerPorVisita(@RequestParam Long visitaId) {
		return ResponseEntity.ok(ApiResponseDto.ok(eventoVisitaService.obtenerPorVisita(visitaId), "Evento obtenido"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<EventoVisitaResponseDto>> crear(@Valid @RequestBody EventoVisitaRequestDto request) {
		EventoVisitaResponseDto evento = eventoVisitaService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(evento, "Evento registrado"));
	}
}
