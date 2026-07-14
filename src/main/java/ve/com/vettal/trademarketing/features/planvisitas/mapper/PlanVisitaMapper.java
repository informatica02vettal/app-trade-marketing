package ve.com.vettal.trademarketing.features.planvisitas.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaResponseDto;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;
import ve.com.vettal.trademarketing.features.productos.dto.ProductoResumenDto;
import ve.com.vettal.trademarketing.features.productos.model.ProductoErpModel;

@Mapper(componentModel = "spring")
public interface PlanVisitaMapper {

	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	@Mapping(target = "sucursalId", source = "sucursal.id")
	@Mapping(target = "sucursalNombre", source = "sucursal.nombreSucursal")
	@Mapping(target = "objetivoTipoId", source = "objetivoTipo.id")
	@Mapping(target = "objetivoTipoNombre", source = "objetivoTipo.nombre")
	@Mapping(target = "objetivoSubtipoId", source = "objetivoSubtipo.id")
	@Mapping(target = "objetivoSubtipoNombre", source = "objetivoSubtipo.nombre")
	PlanVisitaResponseDto toDto(PlanVisitaModel model);

	List<PlanVisitaResponseDto> toDtoList(List<PlanVisitaModel> models);

	ProductoResumenDto toResumenDto(ProductoErpModel model);
}
