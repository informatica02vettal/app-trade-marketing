package ve.com.vettal.trademarketing.features.planvisitas.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaResponseDto;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;

@Mapper(componentModel = "spring")
public interface PlanVisitaMapper {

	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	PlanVisitaResponseDto toDto(PlanVisitaModel model);

	List<PlanVisitaResponseDto> toDtoList(List<PlanVisitaModel> models);
}
