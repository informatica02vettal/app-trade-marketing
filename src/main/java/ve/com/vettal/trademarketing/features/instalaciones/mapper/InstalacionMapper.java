package ve.com.vettal.trademarketing.features.instalaciones.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionItemResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionEjecucionModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemModel;

@Mapper(componentModel = "spring")
public interface InstalacionMapper {

	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	InstalacionResponseDto toDto(InstalacionEjecucionModel model);

	List<InstalacionResponseDto> toDtoList(List<InstalacionEjecucionModel> models);

	InstalacionItemResponseDto toItemDto(InstalacionItemModel model);

	List<InstalacionItemResponseDto> toItemDtoList(List<InstalacionItemModel> models);
}
