package ve.com.vettal.trademarketing.features.catalogos.controller;

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
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaMaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaProductoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;
import ve.com.vettal.trademarketing.features.catalogos.service.CatalogoService;

/**
 * Catálogos de negocio (marcas, competencia, materiales) que antes vivían
 * hardcodeados en el frontend. El frontend debe consumir estos endpoints en
 * vez de sus arreglos fijos, para que Blanca pueda ampliar catálogos sin
 * tocar código.
 */
@RestController
@RequestMapping("/api/v1/catalogos")
@RequiredArgsConstructor
@Tag(name = "Catálogos")
@SecurityRequirement(name = "bearerAuth")
public class CatalogoController {

	private final CatalogoService catalogoService;

	@GetMapping("/marcas")
	public ResponseEntity<ApiResponseDto<List<MarcaResponseDto>>> listarMarcas() {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarMarcas(), "Marcas obtenidas"));
	}

	@GetMapping("/marcas/{marcaId}/competencia")
	public ResponseEntity<ApiResponseDto<List<MarcaCompetenciaResponseDto>>> listarCompetencia(@PathVariable Long marcaId) {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarCompetencia(marcaId), "Competencia obtenida"));
	}

	@GetMapping("/categorias-material")
	public ResponseEntity<ApiResponseDto<List<CategoriaMaterialResponseDto>>> listarCategoriasMaterial(
			@RequestParam(required = false) FamiliaMaterial familia) {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarCategoriasMaterial(familia), "Categorías obtenidas"));
	}

	@GetMapping("/materiales")
	public ResponseEntity<ApiResponseDto<List<MaterialResponseDto>>> listarMateriales(
			@RequestParam Long marcaId,
			@RequestParam(required = false) Long categoriaId) {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarMateriales(marcaId, categoriaId), "Materiales obtenidos"));
	}

	@GetMapping("/categorias-mercado")
	public ResponseEntity<ApiResponseDto<List<CategoriaProductoMercadoResponseDto>>> listarCategoriasProductoMercado() {
		return ResponseEntity.ok(
				ApiResponseDto.ok(catalogoService.listarCategoriasProductoMercado(), "Categorías de mercado obtenidas"));
	}
}
