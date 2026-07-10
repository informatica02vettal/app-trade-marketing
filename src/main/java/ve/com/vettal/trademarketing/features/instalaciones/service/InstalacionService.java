package ve.com.vettal.trademarketing.features.instalaciones.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MaterialRepository;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionItemRequestDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionRequestDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.mapper.InstalacionMapper;
import ve.com.vettal.trademarketing.features.instalaciones.model.EstadoInstalacion;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionEjecucionModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemFotoModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemModel;
import ve.com.vettal.trademarketing.features.instalaciones.repository.InstalacionEjecucionRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class InstalacionService {

	private final InstalacionEjecucionRepository instalacionEjecucionRepository;
	private final VisitaRepository visitaRepository;
	private final MarcaRepository marcaRepository;
	private final MaterialRepository materialRepository;
	private final InstalacionMapper instalacionMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<InstalacionResponseDto> listar(Long usuarioId, Long visitaId) {
		Long usuarioIdEfectivo = usuarioId;
		if (!authenticatedUserProvider.esAdminOSupervisor() && usuarioIdEfectivo == null) {
			usuarioIdEfectivo = authenticatedUserProvider.getUsuarioActual().getId();
		}

		List<InstalacionEjecucionModel> instalaciones;
		if (usuarioIdEfectivo != null && visitaId != null) {
			instalaciones = instalacionEjecucionRepository.findByUsuarioIdAndVisitaId(usuarioIdEfectivo, visitaId);
		} else if (usuarioIdEfectivo != null) {
			instalaciones = instalacionEjecucionRepository.findByUsuarioId(usuarioIdEfectivo);
		} else if (visitaId != null) {
			instalaciones = instalacionEjecucionRepository.findByVisitaId(visitaId);
		} else {
			instalaciones = instalacionEjecucionRepository.findAll();
		}

		return instalacionMapper.toDtoList(instalaciones);
	}

	@Transactional(readOnly = true)
	public InstalacionResponseDto obtener(Long id) {
		return instalacionMapper.toDto(buscarPorId(id));
	}

	public InstalacionResponseDto crear(InstalacionRequestDto request) {
		if (request.getItems() == null || request.getItems().isEmpty()) {
			throw new BusinessException("Debe registrar al menos un ítem de material instalado");
		}

		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));
		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se pueden registrar instalaciones en visitas en curso");
		}

		MarcaModel marca = marcaRepository.findById(request.getMarcaId())
				.orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id " + request.getMarcaId()));

		UsuarioModel usuarioActual = authenticatedUserProvider.getUsuarioActual();

		InstalacionEjecucionModel instalacion = InstalacionEjecucionModel.builder()
				.visita(visita)
				.marca(marca)
				.categoria(request.getCategoria())
				.usuario(usuarioActual)
				.observaciones(request.getObservaciones())
				.fechaInstalacion(request.getFechaInstalacion() != null ? request.getFechaInstalacion() : LocalDate.now())
				.estado(EstadoInstalacion.INSTALADO)
				.build();

		for (InstalacionItemRequestDto itemRequest : request.getItems()) {
			instalacion.addItem(construirItem(itemRequest));
		}

		return instalacionMapper.toDto(instalacionEjecucionRepository.save(instalacion));
	}

	private InstalacionItemModel construirItem(InstalacionItemRequestDto itemRequest) {
		MaterialModel material = materialRepository.findById(itemRequest.getMaterialId())
				.orElseThrow(() -> new ResourceNotFoundException("Material no encontrado con id " + itemRequest.getMaterialId()));

		int cantidadFotos = itemRequest.getFotos() == null ? 0 : itemRequest.getFotos().size();
		if (cantidadFotos < material.getMinimoFotos()) {
			throw new BusinessException(
					"El material '" + material.getNombre() + "' requiere al menos " + material.getMinimoFotos() + " foto(s)");
		}

		InstalacionItemModel item = InstalacionItemModel.builder()
				.material(material)
				.build();

		if (itemRequest.getFotos() != null) {
			itemRequest.getFotos().forEach(url -> item.addFoto(InstalacionItemFotoModel.builder().url(url).build()));
		}

		return item;
	}

	private InstalacionEjecucionModel buscarPorId(Long id) {
		return instalacionEjecucionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Instalación no encontrada con id " + id));
	}
}
