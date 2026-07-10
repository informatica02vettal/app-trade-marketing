package ve.com.vettal.trademarketing.features.visitas.controller;

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
import ve.com.vettal.trademarketing.features.visitas.constants.VisitaConstants;
import ve.com.vettal.trademarketing.features.visitas.dto.ClienteProspectoRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.service.ClienteProspectoService;

/**
 * Cliente detectado en campo que aún no existe en el ERP. Se registra desde
 * una visita ya en curso (Ruta) — GPS y nombre provisional ya quedaron en la
 * visita, aquí solo se completan RIF/WhatsApp/fotos.
 */
@RestController
@RequestMapping(VisitaConstants.API_BASE_PATH_VISITAS + "/clientes-prospecto")
@RequiredArgsConstructor
@Tag(name = "Clientes no registrados")
@SecurityRequirement(name = "bearerAuth")
public class ClienteProspectoController {

	private final ClienteProspectoService clienteProspectoService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<ClienteProspectoResponseDto>>> listar(
			@RequestParam Long visitaId) {
		List<ClienteProspectoResponseDto> prospectos = clienteProspectoService.listarPorVisita(visitaId);
		return ResponseEntity.ok(ApiResponseDto.ok(prospectos, "Clientes prospecto obtenidos"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<ClienteProspectoResponseDto>> crear(
			@Valid @RequestBody ClienteProspectoRequestDto request) {
		ClienteProspectoResponseDto prospecto = clienteProspectoService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(prospecto, "Cliente prospecto registrado"));
	}
}
