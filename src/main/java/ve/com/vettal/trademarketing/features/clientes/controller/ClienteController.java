package ve.com.vettal.trademarketing.features.clientes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.clientes.dto.ClienteResponseDto;
import ve.com.vettal.trademarketing.features.clientes.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes (ERP vía vettal-backend)")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

	private final ClienteService clienteService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<ClienteResponseDto>>> buscar(
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String nombre) {
		List<ClienteResponseDto> clientes = clienteService.buscar(id, nombre);
		return ResponseEntity.ok(ApiResponseDto.ok(clientes, "Clientes obtenidos"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<ClienteResponseDto>> obtener(@PathVariable String id) {
		return ResponseEntity.ok(ApiResponseDto.ok(clienteService.obtener(id), "Cliente obtenido"));
	}
}
