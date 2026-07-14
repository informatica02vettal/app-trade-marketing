package ve.com.vettal.trademarketing.features.competidores.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.competidores.dto.CompetidorResponseDto;
import ve.com.vettal.trademarketing.features.competidores.model.CompetidorModel;

@Mapper(componentModel = "spring")
public interface CompetidorMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "fotosStand", ignore = true)
	@Mapping(target = "fotosMaterialPublicitario", ignore = true)
	CompetidorResponseDto toDto(CompetidorModel model);

	List<CompetidorResponseDto> toDtoList(List<CompetidorModel> models);
}
