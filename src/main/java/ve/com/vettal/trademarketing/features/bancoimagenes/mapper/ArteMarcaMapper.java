package ve.com.vettal.trademarketing.features.bancoimagenes.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.ArteMarcaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.ArteMarcaModel;

@Mapper(componentModel = "spring")
public interface ArteMarcaMapper {

	ArteMarcaResponseDto toDto(ArteMarcaModel model);

	List<ArteMarcaResponseDto> toDtoList(List<ArteMarcaModel> models);
}
