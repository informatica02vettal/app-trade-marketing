package ve.com.vettal.trademarketing.features.auditorias.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.auditorias.dto.AuditoriaMarcaResponseDto;
import ve.com.vettal.trademarketing.features.auditorias.model.AuditoriaMarcaModel;

@Mapper(componentModel = "spring")
public interface AuditoriaMarcaMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "marcaId", source = "marca.id")
	@Mapping(target = "marcaNombre", source = "marca.nombre")
	AuditoriaMarcaResponseDto toDto(AuditoriaMarcaModel model);

	List<AuditoriaMarcaResponseDto> toDtoList(List<AuditoriaMarcaModel> models);
}
