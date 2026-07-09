package ve.com.vettal.trademarketing.features.mercado.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.mercado.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.model.ClienteProspectoModel;

@Mapper(componentModel = "spring")
public interface ClienteProspectoMapper {

	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	@Mapping(target = "hallazgoId", source = "hallazgo.id")
	ClienteProspectoResponseDto toDto(ClienteProspectoModel model);

	List<ClienteProspectoResponseDto> toDtoList(List<ClienteProspectoModel> models);
}
