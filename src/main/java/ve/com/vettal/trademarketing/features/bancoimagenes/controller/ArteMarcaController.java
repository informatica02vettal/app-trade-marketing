package ve.com.vettal.trademarketing.features.bancoimagenes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.constants.BancoImagenesConstants;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.ArteMarcaRequestDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.ArteMarcaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.service.ArteMarcaService;

@RestController
@RequestMapping(BancoImagenesConstants.API_BASE_PATH_ARTES_MARCA)
@RequiredArgsConstructor
@Tag(name = "Artes de marca")
@SecurityRequirement(name = "bearerAuth")
public class ArteMarcaController {

	private final ArteMarcaService arteMarcaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<ArteMarcaResponseDto>>> listar(
			@RequestParam(required = false) String marca) {
		List<ArteMarcaResponseDto> artesMarca = arteMarcaService.listar(marca);
		return ResponseEntity.ok(ApiResponseDto.ok(artesMarca, "Artes de marca obtenidos"));
	}

	@PostMapping
	@PreAuthorize(BancoImagenesConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<ArteMarcaResponseDto>> crear(
			@Valid @RequestBody ArteMarcaRequestDto request) {
		ArteMarcaResponseDto arteMarca = arteMarcaService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(arteMarca, "Arte de marca creado"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(BancoImagenesConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<Void>> eliminar(@PathVariable Long id) {
		arteMarcaService.eliminar(id);
		return ResponseEntity.ok(ApiResponseDto.ok(null, "Arte de marca eliminado"));
	}
}
