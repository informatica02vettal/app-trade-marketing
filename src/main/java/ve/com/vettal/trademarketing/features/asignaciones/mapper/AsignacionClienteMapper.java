package ve.com.vettal.trademarketing.features.asignaciones.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.asignaciones.dto.AsignacionClienteResponseDto;
import ve.com.vettal.trademarketing.features.asignaciones.model.AsignacionClienteModel;

@Mapper(componentModel = "spring")
public interface AsignacionClienteMapper {

	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	AsignacionClienteResponseDto toDto(AsignacionClienteModel model);

	List<AsignacionClienteResponseDto> toDtoList(List<AsignacionClienteModel> models);
}
