package ve.com.vettal.trademarketing.features.productos.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.productos.dto.ProductoLocalResponseDto;
import ve.com.vettal.trademarketing.features.productos.model.ProductoErpModel;

@Mapper(componentModel = "spring")
public interface ProductoErpMapper {

	ProductoLocalResponseDto toDto(ProductoErpModel model);

	List<ProductoLocalResponseDto> toDtoList(List<ProductoErpModel> models);
}
