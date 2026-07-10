package ve.com.vettal.trademarketing.features.visitas.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.visitas.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.model.ClienteProspectoModel;

@Mapper(componentModel = "spring")
public interface ClienteProspectoMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	ClienteProspectoResponseDto toDto(ClienteProspectoModel model);

	List<ClienteProspectoResponseDto> toDtoList(List<ClienteProspectoModel> models);
}
