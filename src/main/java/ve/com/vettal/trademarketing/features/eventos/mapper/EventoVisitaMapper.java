package ve.com.vettal.trademarketing.features.eventos.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoLeadResponseDto;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoVisitaResponseDto;
import ve.com.vettal.trademarketing.features.eventos.model.EventoEntrevistaModel;
import ve.com.vettal.trademarketing.features.eventos.model.EventoLeadModel;
import ve.com.vettal.trademarketing.features.eventos.model.EventoVisitaModel;

@Mapper(componentModel = "spring")
public abstract class EventoVisitaMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "videosEntrevistaUrls", source = "entrevistas")
	public abstract EventoVisitaResponseDto toDto(EventoVisitaModel model);

	public abstract EventoLeadResponseDto toDto(EventoLeadModel model);

	protected List<String> mapEntrevistas(List<EventoEntrevistaModel> entrevistas) {
		return entrevistas.stream().map(EventoEntrevistaModel::getVideoUrl).toList();
	}
}
