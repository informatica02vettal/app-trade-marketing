package ve.com.vettal.trademarketing.features.objetivosvisita.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaSubtipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaTipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaSubtipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaTipoModel;

@Mapper(componentModel = "spring")
public interface ObjetivoVisitaMapper {

	ObjetivoVisitaTipoResponseDto toDto(ObjetivoVisitaTipoModel model);

	List<ObjetivoVisitaTipoResponseDto> toTipoDtoList(List<ObjetivoVisitaTipoModel> models);

	@Mapping(target = "tipoId", source = "tipo.id")
	@Mapping(target = "tipoNombre", source = "tipo.nombre")
	ObjetivoVisitaSubtipoResponseDto toDto(ObjetivoVisitaSubtipoModel model);

	List<ObjetivoVisitaSubtipoResponseDto> toSubtipoDtoList(List<ObjetivoVisitaSubtipoModel> models);
}
