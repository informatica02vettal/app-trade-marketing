package ve.com.vettal.trademarketing.features.clientes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.clientes.dto.SucursalLocalResponseDto;
import ve.com.vettal.trademarketing.features.clientes.model.SucursalClienteErpModel;

@Mapper(componentModel = "spring")
public interface SucursalClienteErpMapper {

	SucursalLocalResponseDto toDto(SucursalClienteErpModel model);

	List<SucursalLocalResponseDto> toDtoList(List<SucursalClienteErpModel> models);
}
