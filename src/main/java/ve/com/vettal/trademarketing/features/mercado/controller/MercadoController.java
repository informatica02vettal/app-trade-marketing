package ve.com.vettal.trademarketing.features.mercado.controller;

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
import ve.com.vettal.trademarketing.features.mercado.constants.MercadoConstants;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.service.HallazgoMercadoService;

/**
 * Inteligencia de mercado: precios de competencia, nuevos productos, material
 * publicitario de competencia y observaciones. El flujo de "cliente no
 * registrado" vive en el módulo Visitas, y "Actividad promocional" se cubre
 * en Solicitudes — ninguno de los dos pertenece a Mercado.
 */
@RestController
@RequestMapping(MercadoConstants.API_BASE_PATH_MERCADO)
@RequiredArgsConstructor
@Tag(name = "Inteligencia de Mercado")
@SecurityRequirement(name = "bearerAuth")
public class MercadoController {

	private final HallazgoMercadoService hallazgoMercadoService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<HallazgoMercadoResponseDto>>> listar(
			@RequestParam(required = false) TipoHallazgo tipo,
			@RequestParam(required = false) Long usuarioId,
			@RequestParam(required = false) Long marcaId,
			@RequestParam(required = false) Long visitaId) {
		List<HallazgoMercadoResponseDto> hallazgos = hallazgoMercadoService.listar(tipo, usuarioId, marcaId, visitaId);
		return ResponseEntity.ok(ApiResponseDto.ok(hallazgos, "Hallazgos de mercado obtenidos"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<HallazgoMercadoResponseDto>> crear(
			@Valid @RequestBody HallazgoMercadoRequestDto request) {
		HallazgoMercadoResponseDto hallazgo = hallazgoMercadoService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(hallazgo, "Hallazgo de mercado creado"));
	}
}
