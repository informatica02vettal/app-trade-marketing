package ve.com.vettal.trademarketing.features.visitas.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaResponseDto;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Mapper(componentModel = "spring")
public interface VisitaMapper {

	@Mapping(target = "planId", source = "plan.id")
	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	@Mapping(target = "cantidadFotos", ignore = true)
	VisitaResponseDto toDto(VisitaModel model);

	List<VisitaResponseDto> toDtoList(List<VisitaModel> models);
}
