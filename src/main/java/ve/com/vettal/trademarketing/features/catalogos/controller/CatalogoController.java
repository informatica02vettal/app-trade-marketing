package ve.com.vettal.trademarketing.features.catalogos.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaMaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaProductoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaEstadoRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.RegionEstadoRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.RegionRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.RegionResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;
import ve.com.vettal.trademarketing.features.catalogos.service.CatalogoService;
import ve.com.vettal.trademarketing.features.usuarios.constants.UsuarioConstants;

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

	@PostMapping("/marcas")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<MarcaResponseDto>> crearMarca(@Valid @RequestBody MarcaRequestDto request) {
		MarcaResponseDto marca = catalogoService.crearMarca(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(marca, "Marca creada"));
	}

	@PatchMapping("/marcas/{id}/estado")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<MarcaResponseDto>> cambiarEstadoMarca(
			@PathVariable Long id, @Valid @RequestBody MarcaEstadoRequestDto request) {
		MarcaResponseDto marca = catalogoService.cambiarEstadoMarca(id, request.getActivo());
		String mensaje = request.getActivo() ? "Marca activada" : "Marca desactivada";
		return ResponseEntity.ok(ApiResponseDto.ok(marca, mensaje));
	}

	@GetMapping("/marcas/{marcaId}/competencia")
	public ResponseEntity<ApiResponseDto<List<MarcaCompetenciaResponseDto>>> listarCompetencia(@PathVariable Long marcaId) {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarCompetencia(marcaId), "Competencia obtenida"));
	}

	// Sin @PreAuthorize a propósito: el mercaderista debe poder registrar en
	// el momento una marca de competencia que no esté todavía en el catálogo
	// mientras hace la auditoría de marca (cualquier rol autenticado puede).
	@PostMapping("/marcas/{marcaId}/competencia")
	public ResponseEntity<ApiResponseDto<MarcaCompetenciaResponseDto>> crearCompetencia(
			@PathVariable Long marcaId, @Valid @RequestBody MarcaCompetenciaRequestDto request) {
		MarcaCompetenciaResponseDto competencia = catalogoService.crearCompetencia(marcaId, request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(competencia, "Marca de competencia creada"));
	}

	@PutMapping("/competencia/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<MarcaCompetenciaResponseDto>> actualizarCompetencia(
			@PathVariable Long id, @Valid @RequestBody MarcaCompetenciaRequestDto request) {
		MarcaCompetenciaResponseDto competencia = catalogoService.actualizarCompetencia(id, request);
		return ResponseEntity.ok(ApiResponseDto.ok(competencia, "Marca de competencia actualizada"));
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

	@GetMapping("/regiones")
	public ResponseEntity<ApiResponseDto<List<RegionResponseDto>>> listarRegiones() {
		return ResponseEntity.ok(ApiResponseDto.ok(catalogoService.listarRegiones(), "Regiones obtenidas"));
	}

	@PostMapping("/regiones")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<RegionResponseDto>> crearRegion(@Valid @RequestBody RegionRequestDto request) {
		RegionResponseDto region = catalogoService.crearRegion(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(region, "Región creada"));
	}

	@PutMapping("/regiones/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<RegionResponseDto>> actualizarRegion(
			@PathVariable Long id, @Valid @RequestBody RegionRequestDto request) {
		RegionResponseDto region = catalogoService.actualizarRegion(id, request);
		return ResponseEntity.ok(ApiResponseDto.ok(region, "Región actualizada"));
	}

	@PatchMapping("/regiones/{id}/estado")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<RegionResponseDto>> cambiarEstadoRegion(
			@PathVariable Long id, @Valid @RequestBody RegionEstadoRequestDto request) {
		RegionResponseDto region = catalogoService.cambiarEstadoRegion(id, request.getActivo());
		String mensaje = request.getActivo() ? "Región activada" : "Región desactivada";
		return ResponseEntity.ok(ApiResponseDto.ok(region, mensaje));
	}
}
