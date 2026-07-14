package ve.com.vettal.trademarketing.features.productos.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.productos.dto.ProductoLocalResponseDto;
import ve.com.vettal.trademarketing.features.productos.dto.SincronizacionProductosResultadoDto;
import ve.com.vettal.trademarketing.features.productos.service.ProductoSincronizacionService;
import ve.com.vettal.trademarketing.features.usuarios.constants.UsuarioConstants;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
@Tag(name = "Productos (ERP)")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {

	private final ProductoSincronizacionService productoSincronizacionService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<List<ProductoLocalResponseDto>>> listar() {
		return ResponseEntity.ok(ApiResponseDto.ok(productoSincronizacionService.listarLocales(), "Productos obtenidos"));
	}

	@PostMapping("/sincronizar")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<SincronizacionProductosResultadoDto>> sincronizar() {
		SincronizacionProductosResultadoDto resultado = productoSincronizacionService.sincronizar();
		return ResponseEntity.ok(ApiResponseDto.ok(resultado, "Sincronización de productos completada"));
	}
}
