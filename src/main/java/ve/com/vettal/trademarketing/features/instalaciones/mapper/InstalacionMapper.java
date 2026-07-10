package ve.com.vettal.trademarketing.features.instalaciones.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionItemResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionEjecucionModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemFotoModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemModel;

@Mapper(componentModel = "spring")
public interface InstalacionMapper {

	@Mapping(target = "visitaId", source = "visita.id")
	@Mapping(target = "erpClienteId", source = "visita.erpClienteId")
	@Mapping(target = "clienteNombre", source = "visita.clienteNombre")
	@Mapping(target = "marcaId", source = "marca.id")
	@Mapping(target = "marcaNombre", source = "marca.nombre")
	@Mapping(target = "usuarioId", source = "usuario.id")
	@Mapping(target = "usuarioNombre", source = "usuario.nombre")
	InstalacionResponseDto toDto(InstalacionEjecucionModel model);

	List<InstalacionResponseDto> toDtoList(List<InstalacionEjecucionModel> models);

	@Mapping(target = "materialId", source = "material.id")
	@Mapping(target = "materialNombre", source = "material.nombre")
	@Mapping(target = "fotos", source = "fotos")
	InstalacionItemResponseDto toItemDto(InstalacionItemModel model);

	List<InstalacionItemResponseDto> toItemDtoList(List<InstalacionItemModel> models);

	default String fotoToUrl(InstalacionItemFotoModel foto) {
		return foto.getUrl();
	}
}
