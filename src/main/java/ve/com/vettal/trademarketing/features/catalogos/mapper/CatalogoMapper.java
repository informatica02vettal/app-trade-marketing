package ve.com.vettal.trademarketing.features.catalogos.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaMaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.CategoriaProductoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaCompetenciaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MarcaResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.dto.MaterialResponseDto;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaMaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaProductoMercadoModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaCompetenciaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;

@Mapper(componentModel = "spring")
public interface CatalogoMapper {

	MarcaResponseDto toDto(MarcaModel model);

	List<MarcaResponseDto> toMarcaDtoList(List<MarcaModel> models);

	MarcaCompetenciaResponseDto toDto(MarcaCompetenciaModel model);

	List<MarcaCompetenciaResponseDto> toCompetenciaDtoList(List<MarcaCompetenciaModel> models);

	CategoriaMaterialResponseDto toDto(CategoriaMaterialModel model);

	List<CategoriaMaterialResponseDto> toCategoriaMaterialDtoList(List<CategoriaMaterialModel> models);

	@Mapping(target = "categoriaId", source = "categoria.id")
	@Mapping(target = "categoriaNombre", source = "categoria.nombre")
	@Mapping(target = "marcaId", source = "marca.id")
	MaterialResponseDto toDto(MaterialModel model);

	List<MaterialResponseDto> toMaterialDtoList(List<MaterialModel> models);

	@Mapping(target = "marcaId", source = "marca.id")
	CategoriaProductoMercadoResponseDto toDto(CategoriaProductoMercadoModel model);

	List<CategoriaProductoMercadoResponseDto> toCategoriaProductoDtoList(List<CategoriaProductoMercadoModel> models);
}
