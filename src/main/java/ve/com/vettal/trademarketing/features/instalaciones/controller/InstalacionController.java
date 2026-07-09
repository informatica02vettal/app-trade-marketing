package ve.com.vettal.trademarketing.features.instalaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionRequestDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.service.InstalacionService;

@RestController
@RequestMapping("/api/v1/instalaciones")
@RequiredArgsConstructor
@Tag(name = "Instalación y Ejecución")
@SecurityRequirement(name = "bearerAuth")
public class InstalacionController {

	private final InstalacionService instalacionService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<InstalacionResponseDto>>> listar(
			@RequestParam(required = false) Long usuarioId,
			@RequestParam(required = false) String erpClienteId) {
		List<InstalacionResponseDto> instalaciones = instalacionService.listar(usuarioId, erpClienteId);
		return ResponseEntity.ok(ApiResponseDto.ok(instalaciones, "Instalaciones obtenidas"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDto<InstalacionResponseDto>> obtener(@PathVariable Long id) {
		InstalacionResponseDto instalacion = instalacionService.obtener(id);
		return ResponseEntity.ok(ApiResponseDto.ok(instalacion, "Instalación obtenida"));
	}

	@PostMapping
	public ResponseEntity<ApiResponseDto<InstalacionResponseDto>> crear(
			@Valid @RequestBody InstalacionRequestDto request) {
		InstalacionResponseDto instalacion = instalacionService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(instalacion, "Instalación registrada"));
	}
}
