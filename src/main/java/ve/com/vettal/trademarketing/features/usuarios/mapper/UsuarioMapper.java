package ve.com.vettal.trademarketing.features.usuarios.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioResponseDto toDto(UsuarioModel model);

	List<UsuarioResponseDto> toDtoList(List<UsuarioModel> models);
}
