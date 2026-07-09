package ve.com.vettal.trademarketing.features.solicitudes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudItemRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudItemResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudModel;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

	@Mapping(target = "solicitanteId", source = "solicitante.id")
	@Mapping(target = "solicitanteNombre", source = "solicitante.nombre")
	@Mapping(target = "aprobadoPorId", source = "aprobadoPor.id")
	@Mapping(target = "aprobadoPorNombre", source = "aprobadoPor.nombre")
	SolicitudResponseDto toDto(SolicitudModel model);

	List<SolicitudResponseDto> toDtoList(List<SolicitudModel> models);

	SolicitudItemResponseDto toItemDto(SolicitudItemModel model);

	List<SolicitudItemResponseDto> toItemDtoList(List<SolicitudItemModel> models);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "solicitud", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	SolicitudItemModel toItemModel(SolicitudItemRequestDto dto);
}
