package ve.com.vettal.trademarketing.features.catalogos.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaMaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaProductoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.mapper.CatalogoMapper;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaMaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;
import ve.com.vettal.trademarketing.features.catalogos.repository.CategoriaMaterialRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.CategoriaProductoMercadoRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaCompetenciaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MaterialRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogoService {

	private final MarcaRepository marcaRepository;
	private final MarcaCompetenciaRepository marcaCompetenciaRepository;
	private final CategoriaMaterialRepository categoriaMaterialRepository;
	private final MaterialRepository materialRepository;
	private final CategoriaProductoMercadoRepository categoriaProductoMercadoRepository;
	private final CatalogoMapper catalogoMapper;

	public List<MarcaResponseDto> listarMarcas() {
		return catalogoMapper.toMarcaDtoList(marcaRepository.findByActivoTrue());
	}

	public List<MarcaCompetenciaResponseDto> listarCompetencia(Long marcaId) {
		return catalogoMapper.toCompetenciaDtoList(marcaCompetenciaRepository.findByMarcaIdAndActivoTrue(marcaId));
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
}
