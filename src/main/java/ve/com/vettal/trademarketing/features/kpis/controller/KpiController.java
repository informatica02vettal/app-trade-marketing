package ve.com.vettal.trademarketing.features.kpis.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.kpis.dto.KpiDashboardResponseDto;
import ve.com.vettal.trademarketing.features.kpis.service.KpiService;

@RestController
@RequestMapping("/api/v1/kpis")
@RequiredArgsConstructor
@Tag(name = "KPIs")
@SecurityRequirement(name = "bearerAuth")
public class KpiController {

	private final KpiService kpiService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<KpiDashboardResponseDto>> obtenerDashboard() {
		return ResponseEntity.ok(ApiResponseDto.ok(kpiService.obtenerDashboard(), "KPIs obtenidos"));
	}
}
