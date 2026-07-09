package ve.com.vettal.trademarketing.features.bancoimagenes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.PlanogramaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.PlanogramaModel;

@Mapper(componentModel = "spring")
public interface PlanogramaMapper {

	PlanogramaResponseDto toDto(PlanogramaModel model);

	List<PlanogramaResponseDto> toDtoList(List<PlanogramaModel> models);
}
