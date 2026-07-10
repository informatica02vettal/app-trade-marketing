package ve.com.vettal.trademarketing.features.solicitudes.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.catalogos.model.FamiliaMaterial;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaRepository;
import ve.com.vettal.trademarketing.features.catalogos.repository.MaterialRepository;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudItemRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.mapper.SolicitudMapper;
import ve.com.vettal.trademarketing.features.solicitudes.model.CategoriaSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemFotoModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudModel;
import ve.com.vettal.trademarketing.features.solicitudes.repository.SolicitudRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
	private final VisitaRepository visitaRepository;
	private final MarcaRepository marcaRepository;
	private final MaterialRepository materialRepository;
	private final SolicitudMapper solicitudMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<SolicitudResponseDto> listar(EstadoSolicitud estado, Long solicitanteId) {
		Long solicitanteIdFiltro = solicitanteId;
		if (!authenticatedUserProvider.esAdminOSupervisor() && solicitanteIdFiltro == null) {
			solicitanteIdFiltro = authenticatedUserProvider.getUsuarioActual().getId();
		}

		List<SolicitudModel> solicitudes;
		if (estado != null && solicitanteIdFiltro != null) {
			solicitudes = solicitudRepository.findByEstadoAndSolicitanteId(estado, solicitanteIdFiltro);
		} else if (estado != null) {
			solicitudes = solicitudRepository.findByEstado(estado);
		} else if (solicitanteIdFiltro != null) {
			solicitudes = solicitudRepository.findBySolicitanteId(solicitanteIdFiltro);
		} else {
			solicitudes = solicitudRepository.findAll();
		}

		return solicitudMapper.toDtoList(solicitudes);
	}

	@Transactional(readOnly = true)
	public SolicitudResponseDto buscarPorId(Long id) {
		return solicitudMapper.toDto(obtenerSolicitud(id));
	}

	@Transactional
	public SolicitudResponseDto crear(SolicitudRequestDto request) {
		if (request.getItems() == null || request.getItems().isEmpty()) {
			throw new BusinessException("La solicitud debe tener al menos un item");
		}

		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));
		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se pueden crear solicitudes en visitas en curso");
		}

		MarcaModel marca = marcaRepository.findById(request.getMarcaId())
				.orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id " + request.getMarcaId()));

		UsuarioModel solicitante = authenticatedUserProvider.getUsuarioActual();

		SolicitudModel solicitud = SolicitudModel.builder()
				.visita(visita)
				.categoria(request.getCategoria())
				.marca(marca)
				.observaciones(request.getObservaciones())
				.solicitante(solicitante)
				.estado(EstadoSolicitud.PENDIENTE_APROBACION)
				.build();

		for (SolicitudItemRequestDto itemDto : request.getItems()) {
			solicitud.addItem(construirItem(itemDto, request.getCategoria()));
		}

		return solicitudMapper.toDto(solicitudRepository.save(solicitud));
	}

	private SolicitudItemModel construirItem(SolicitudItemRequestDto itemDto, CategoriaSolicitud categoria) {
		MaterialModel material = materialRepository.findById(itemDto.getMaterialId())
				.orElseThrow(() -> new ResourceNotFoundException("Material no encontrado con id " + itemDto.getMaterialId()));

		FamiliaMaterial familiaEsperada = categoria == CategoriaSolicitud.PUBLICIDAD
				? FamiliaMaterial.PUBLICIDAD
				: FamiliaMaterial.TRADE_MARKETING;
		if (material.getCategoria().getFamilia() != familiaEsperada) {
			throw new BusinessException("El material '" + material.getNombre() + "' no pertenece a la categoría " + categoria);
		}

		int cantidadFotos = itemDto.getFotos() == null ? 0 : itemDto.getFotos().size();
		if (cantidadFotos < material.getMinimoFotos()) {
			throw new BusinessException(
					"El material '" + material.getNombre() + "' requiere al menos " + material.getMinimoFotos() + " foto(s)");
		}
		if (material.isRequiereMedidas() && (itemDto.getMedidas() == null || itemDto.getMedidas().isBlank())) {
			throw new BusinessException("El material '" + material.getNombre() + "' requiere indicar las medidas");
		}
		if (material.isRequiereUbicacion() && (itemDto.getUbicacion() == null || itemDto.getUbicacion().isBlank())) {
			throw new BusinessException("El material '" + material.getNombre() + "' requiere indicar la ubicación");
		}

		SolicitudItemModel item = SolicitudItemModel.builder()
				.material(material)
				.medidas(itemDto.getMedidas())
				.ubicacion(itemDto.getUbicacion())
				.build();

		if (itemDto.getFotos() != null) {
			itemDto.getFotos().forEach(url -> item.addFoto(SolicitudItemFotoModel.builder().url(url).build()));
		}

		return item;
	}

	@Transactional
	public SolicitudResponseDto cambiarEstado(Long id, EstadoSolicitud nuevoEstado) {
		SolicitudModel solicitud = obtenerSolicitud(id);
		solicitud.setEstado(nuevoEstado);

		if (nuevoEstado == EstadoSolicitud.APROBADO || nuevoEstado == EstadoSolicitud.RECHAZADO) {
			solicitud.setAprobadoPor(authenticatedUserProvider.getUsuarioActual());
		}

		return solicitudMapper.toDto(solicitudRepository.save(solicitud));
	}

	private SolicitudModel obtenerSolicitud(Long id) {
		return solicitudRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con id " + id));
	}
}
