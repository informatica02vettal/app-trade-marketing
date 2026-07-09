package ve.com.vettal.trademarketing.features.visitas.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.visitas.dto.EvidenciaFotoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.model.EvidenciaFotoModel;

@Mapper(componentModel = "spring")
public interface EvidenciaFotoMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	EvidenciaFotoResponseDto toDto(EvidenciaFotoModel model);

	List<EvidenciaFotoResponseDto> toDtoList(List<EvidenciaFotoModel> models);
}
