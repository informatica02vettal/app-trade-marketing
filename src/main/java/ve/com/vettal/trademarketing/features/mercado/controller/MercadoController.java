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
import ve.com.vettal.trademarketing.features.mercado.dto.ClienteProspectoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.service.ClienteProspectoService;
import ve.com.vettal.trademarketing.features.mercado.service.HallazgoMercadoService;

@RestController
@RequestMapping(MercadoConstants.API_BASE_PATH_MERCADO)
@RequiredArgsConstructor
@Tag(name = "Inteligencia de Mercado")
@SecurityRequirement(name = "bearerAuth")
public class MercadoController {

	private final HallazgoMercadoService hallazgoMercadoService;
	private final ClienteProspectoService clienteProspectoService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<HallazgoMercadoResponseDto>>> listar(
			@RequestParam(required = false) TipoHallazgo tipo,
			@RequestParam(required = false) Long usuarioId) {
		List<HallazgoMercadoResponseDto> hallazgos = hallazgoMercadoService.listar(tipo, usuarioId);
		return ResponseEntity.ok(ApiResponseDto.ok(hallazgos, "Hallazgos de mercado obtenidos"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<HallazgoMercadoResponseDto>> crear(
			@Valid @RequestBody HallazgoMercadoRequestDto request) {
		HallazgoMercadoResponseDto hallazgo = hallazgoMercadoService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(hallazgo, "Hallazgo de mercado creado"));
	}

	@GetMapping(MercadoConstants.PATH_CLIENTES_PROSPECTO)
	public ResponseEntity<ApiResponseDto<List<ClienteProspectoResponseDto>>> listarClientesProspecto() {
		List<ClienteProspectoResponseDto> prospectos = clienteProspectoService.listar();
		return ResponseEntity.ok(ApiResponseDto.ok(prospectos, "Clientes prospecto obtenidos"));
	}

	@PostMapping(MercadoConstants.PATH_CLIENTES_PROSPECTO)
	public ResponseEntity<ApiResponseDto<ClienteProspectoResponseDto>> crearClienteProspecto(
			@Valid @RequestBody ClienteProspectoRequestDto request) {
		ClienteProspectoResponseDto prospecto = clienteProspectoService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(prospecto, "Cliente prospecto registrado"));
	}
}
