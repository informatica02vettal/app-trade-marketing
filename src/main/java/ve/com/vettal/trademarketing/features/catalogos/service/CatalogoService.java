package ve.com.vettal.trademarketing.features.catalogos.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaMaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaProductoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaEstadoRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.RegionRequestDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.RegionResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.mapper.CatalogoMapper;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaMaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaCompetenciaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.RegionModel;
import ve.com.vettal.trademarketing.features.catalogos.repository.CategoriaMaterialRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.CategoriaProductoMercadoRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaCompetenciaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MaterialRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.RegionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogoService {

	private final MarcaRepository marcaRepository;
	private final MarcaCompetenciaRepository marcaCompetenciaRepository;
	private final CategoriaMaterialRepository categoriaMaterialRepository;
	private final MaterialRepository materialRepository;
	private final CategoriaProductoMercadoRepository categoriaProductoMercadoRepository;
	private final RegionRepository regionRepository;
	private final CatalogoMapper catalogoMapper;

	// Devuelve todas las marcas (activas e inactivas): el admin necesita ver
	// y poder reactivar una marca desactivada. Quien solo debe ver marcas
	// activas (la app de mercaderistas) filtra del lado del cliente, igual
	// que ya se hace con las marcas de competencia.
	public List<MarcaResponseDto> listarMarcas() {
		return catalogoMapper.toMarcaDtoList(marcaRepository.findAllByOrderByNombreAsc());
	}

	@Transactional
	public MarcaResponseDto crearMarca(MarcaRequestDto request) {
		if (marcaRepository.existsByCodigoIgnoreCase(request.getCodigo())) {
			throw new BusinessException("Ya existe una marca con ese código");
		}

		MarcaModel marca = MarcaModel.builder()
				.codigo(request.getCodigo())
				.nombre(request.getNombre())
				.build();

		return catalogoMapper.toDto(marcaRepository.save(marca));
	}

	// Solo cambia activo/inactivo, sin revalidar código/nombre — evita el
	// mismo problema que tuvimos con usuarios: reactivar una marca nunca
	// debe depender de que el resto de sus datos siga pasando validación.
	@Transactional
	public MarcaResponseDto cambiarEstadoMarca(Long id, boolean activo) {
		MarcaModel marca = marcaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id " + id));
		marca.setActivo(activo);
		return catalogoMapper.toDto(marcaRepository.save(marca));
	}

	public List<MarcaCompetenciaResponseDto> listarCompetencia(Long marcaId) {
		return catalogoMapper.toCompetenciaDtoList(marcaCompetenciaRepository.findByMarcaId(marcaId));
	}

	@Transactional
	public MarcaCompetenciaResponseDto crearCompetencia(Long marcaId, MarcaCompetenciaRequestDto request) {
		MarcaModel marca = marcaRepository.findById(marcaId)
				.orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id " + marcaId));

		MarcaCompetenciaModel competencia = MarcaCompetenciaModel.builder()
				.marca(marca)
				.nombre(request.getNombre())
				.activo(request.getActivo() == null || request.getActivo())
				.build();

		return guardarCompetencia(competencia);
	}

	@Transactional
	public MarcaCompetenciaResponseDto actualizarCompetencia(Long id, MarcaCompetenciaRequestDto request) {
		MarcaCompetenciaModel competencia = marcaCompetenciaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Marca de competencia no encontrada con id " + id));

		competencia.setNombre(request.getNombre());
		if (request.getActivo() != null) {
			competencia.setActivo(request.getActivo());
		}

		return guardarCompetencia(competencia);
	}

	private MarcaCompetenciaResponseDto guardarCompetencia(MarcaCompetenciaModel competencia) {
		try {
			return catalogoMapper.toDto(marcaCompetenciaRepository.save(competencia));
		} catch (DataIntegrityViolationException ex) {
			throw new BusinessException("Ya existe una marca de competencia con ese nombre para esta marca");
		}
	}

	public List<CategoriaMaterialResponseDto> listarCategoriasMaterial(FamiliaMaterial familia) {
		List<CategoriaMaterialModel> categorias = familia == null
				? categoriaMaterialRepository.findByActivoTrue()
				: categoriaMaterialRepository.findByFamiliaAndActivoTrue(familia);
		return catalogoMapper.toCategoriaMaterialDtoList(categorias);
	}

	public List<MaterialResponseDto> listarMateriales(Long marcaId, Long categoriaId) {
		return catalogoMapper.toMaterialDtoList(materialRepository.buscarAplicables(marcaId, categoriaId));
	}

	public List<CategoriaProductoMercadoResponseDto> listarCategoriasProductoMercado() {
		return catalogoMapper.toCategoriaProductoDtoList(categoriaProductoMercadoRepository.findByActivoTrue());
	}

	public List<RegionResponseDto> listarRegiones() {
		return catalogoMapper.toRegionDtoList(regionRepository.findAllByOrderByNombreAsc());
	}

	@Transactional
	public RegionResponseDto crearRegion(RegionRequestDto request) {
		if (regionRepository.existsByNombreIgnoreCase(request.getNombre())) {
			throw new BusinessException("Ya existe una región con ese nombre");
		}

		RegionModel region = RegionModel.builder()
				.nombre(request.getNombre())
				.detalles(request.getDetalles())
				.build();

		return catalogoMapper.toDto(regionRepository.save(region));
	}

	@Transactional
	public RegionResponseDto actualizarRegion(Long id, RegionRequestDto request) {
		RegionModel region = regionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id " + id));

		if (!region.getNombre().equalsIgnoreCase(request.getNombre())
				&& regionRepository.existsByNombreIgnoreCase(request.getNombre())) {
			throw new BusinessException("Ya existe una región con ese nombre");
		}

		region.setNombre(request.getNombre());
		region.setDetalles(request.getDetalles());

		return catalogoMapper.toDto(regionRepository.save(region));
	}

	// Solo cambia activo/inactivo, igual que UsuarioService.cambiarEstado —
	// nunca borra la región, así una vez usada como región de un cliente o
	// mercaderista no desaparece su historial ni el nombre queda huérfano.
	@Transactional
	public RegionResponseDto cambiarEstadoRegion(Long id, boolean activo) {
		RegionModel region = regionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Región no encontrada con id " + id));
		region.setActivo(activo);
		return catalogoMapper.toDto(regionRepository.save(region));
	}
}
