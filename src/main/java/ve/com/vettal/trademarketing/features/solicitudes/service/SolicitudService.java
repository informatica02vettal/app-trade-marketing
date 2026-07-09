package ve.com.vettal.trademarketing.features.solicitudes.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudItemRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudRequestDto;
import ve.com.vettal.trademarketing.features.solicitudes.dto.SolicitudResponseDto;
import ve.com.vettal.trademarketing.features.solicitudes.mapper.SolicitudMapper;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudItemModel;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudModel;
import ve.com.vettal.trademarketing.features.solicitudes.repository.SolicitudRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
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

		UsuarioModel solicitante = authenticatedUserProvider.getUsuarioActual();

		SolicitudModel solicitud = SolicitudModel.builder()
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.categoria(request.getCategoria())
				.marca(request.getMarca())
				.observaciones(request.getObservaciones())
				.solicitante(solicitante)
				.estado(EstadoSolicitud.PENDIENTE_APROBACION)
				.build();

		for (SolicitudItemRequestDto itemDto : request.getItems()) {
			SolicitudItemModel item = solicitudMapper.toItemModel(itemDto);
			solicitud.addItem(item);
		}

		return solicitudMapper.toDto(solicitudRepository.save(solicitud));
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
