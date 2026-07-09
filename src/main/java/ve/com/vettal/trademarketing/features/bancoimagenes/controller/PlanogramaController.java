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
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.PlanogramaRequestDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.PlanogramaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.service.PlanogramaService;

@RestController
@RequestMapping(BancoImagenesConstants.API_BASE_PATH_PLANOGRAMAS)
@RequiredArgsConstructor
@Tag(name = "Planogramas")
@SecurityRequirement(name = "bearerAuth")
public class PlanogramaController {

	private final PlanogramaService planogramaService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<PlanogramaResponseDto>>> listar(
			@RequestParam(required = false) String tipoExhibidor) {
		List<PlanogramaResponseDto> planogramas = planogramaService.listar(tipoExhibidor);
		return ResponseEntity.ok(ApiResponseDto.ok(planogramas, "Planogramas obtenidos"));
	}

	@PostMapping
	@PreAuthorize(BancoImagenesConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<PlanogramaResponseDto>> crear(
			@Valid @RequestBody PlanogramaRequestDto request) {
		PlanogramaResponseDto planograma = planogramaService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(planograma, "Planograma creado"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(BancoImagenesConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<Void>> eliminar(@PathVariable Long id) {
		planogramaService.eliminar(id);
		return ResponseEntity.ok(ApiResponseDto.ok(null, "Planograma eliminado"));
	}
}
