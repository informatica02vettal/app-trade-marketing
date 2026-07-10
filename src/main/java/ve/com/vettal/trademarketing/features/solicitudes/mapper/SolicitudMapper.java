package ve.com.vettal.trademarketing.features.solicitudes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudItemResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemFotoModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudModel;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "erpClienteId", source = "visita.erpClienteId")
	@Mapping(target = "clienteNombre", source = "visita.clienteNombre")
	@Mapping(target = "marcaId", source = "marca.id")
	@Mapping(target = "marcaNombre", source = "marca.nombre")
	@Mapping(target = "solicitanteId", source = "solicitante.id")
	@Mapping(target = "solicitanteNombre", source = "solicitante.nombre")
	@Mapping(target = "aprobadoPorId", source = "aprobadoPor.id")
	@Mapping(target = "aprobadoPorNombre", source = "aprobadoPor.nombre")
	SolicitudResponseDto toDto(SolicitudModel model);

	List<SolicitudResponseDto> toDtoList(List<SolicitudModel> models);

	@Mapping(target = "materialId", source = "material.id")
	@Mapping(target = "materialNombre", source = "material.nombre")
	@Mapping(target = "fotos", source = "fotos")
	SolicitudItemResponseDto toItemDto(SolicitudItemModel model);

	List<SolicitudItemResponseDto> toItemDtoList(List<SolicitudItemModel> models);

	default String fotoToUrl(SolicitudItemFotoModel foto) {
		return foto.getUrl();
	}
}
