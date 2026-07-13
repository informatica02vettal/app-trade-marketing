package ve.com.vettal.trademarketing.features.clientes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.clientes.dto.ClienteLocalResponseDto;
import ve.com.vettal.trademarketing.features.clientes.model.ClienteErpModel;

@Mapper(componentModel = "spring")
public interface ClienteErpMapper {

	ClienteLocalResponseDto toDto(ClienteErpModel model);

	List<ClienteLocalResponseDto> toDtoList(List<ClienteErpModel> models);
}
