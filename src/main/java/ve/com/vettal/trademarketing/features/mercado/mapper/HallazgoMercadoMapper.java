package ve.com.vettal.trademarketing.features.mercado.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMaterialResponseDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoProductoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMaterialModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoProductoModel;

@Mapper(componentModel = "spring")
public interface HallazgoMercadoMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "categoriaProductoId", source = "categoriaProducto.id")
	@Mapping(target = "categoriaProductoNombre", source = "categoriaProducto.nombre")
	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	HallazgoMercadoResponseDto toDto(HallazgoMercadoModel model);

	List<HallazgoMercadoResponseDto> toDtoList(List<HallazgoMercadoModel> models);

	HallazgoProductoResponseDto toDto(HallazgoProductoModel model);

	HallazgoMaterialResponseDto toDto(HallazgoMaterialModel model);
}
