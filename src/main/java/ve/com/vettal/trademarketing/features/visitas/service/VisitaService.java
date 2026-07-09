package ve.com.vettal.trademarketing.features.visitas.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;
import ve.com.vettal.trademarketing.features.planvisitas.repository.PlanVisitaRepository;
import ve.com.vettal.trademarketing.features.visitas.dto.EvidenciaFotoRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.EvidenciaFotoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaCheckinRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaCheckoutRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.VisitaResponseDto;
import ve.com.vettal.trademarketing.features.visitas.mapper.EvidenciaFotoMapper;
import ve.com.vettal.trademarketing.features.visitas.mapper.VisitaMapper;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.EvidenciaFotoModel;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.EvidenciaFotoRepository;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class VisitaService {

	private final VisitaRepository visitaRepository;
	private final EvidenciaFotoRepository evidenciaFotoRepository;
	private final PlanVisitaRepository planVisitaRepository;
	private final VisitaMapper visitaMapper;
	private final EvidenciaFotoMapper evidenciaFotoMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<VisitaResponseDto> listar(Long usuarioId) {
		Long usuarioIdEfectivo = usuarioId;
		if (usuarioIdEfectivo == null && !authenticatedUserProvider.esAdminOSupervisor()) {
			usuarioIdEfectivo = authenticatedUserProvider.getUsuarioActual().getId();
		}

		List<VisitaModel> visitas = usuarioIdEfectivo != null
				? visitaRepository.findByUsuarioId(usuarioIdEfectivo)
				: visitaRepository.findAll();

		return visitas.stream().map(this::toResponseDto).toList();
	}

	@Transactional
	public VisitaResponseDto checkin(VisitaCheckinRequestDto request) {
		PlanVisitaModel plan = null;
		if (request.getPlanId() != null) {
			plan = planVisitaRepository.findById(request.getPlanId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Plan de visita no encontrado con id " + request.getPlanId()));
		}

		VisitaModel visita = VisitaModel.builder()
				.plan(plan)
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.region(request.getRegion())
				.usuario(authenticatedUserProvider.getUsuarioActual())
				.ejecutivoVentas(request.getEjecutivoVentas())
				.checkinAt(LocalDateTime.now())
				.checkinGpsLat(request.getCheckinGpsLat())
				.checkinGpsLng(request.getCheckinGpsLng())
				.estado(EstadoVisita.EN_CURSO)
				.build();

		return toResponseDto(visitaRepository.save(visita));
	}

	@Transactional
	public EvidenciaFotoResponseDto agregarFoto(Long visitaId, EvidenciaFotoRequestDto request) {
		VisitaModel visita = visitaRepository.findById(visitaId)
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + visitaId));

		if (visita.getEstado() == EstadoVisita.COMPLETADA) {
			throw new BusinessException("No se pueden agregar fotos a una visita ya completada");
		}

		EvidenciaFotoModel foto = EvidenciaFotoModel.builder()
				.visita(visita)
				.categoria(request.getCategoria())
				.url(request.getUrl())
				.build();

		return evidenciaFotoMapper.toDto(evidenciaFotoRepository.save(foto));
	}

	@Transactional
	public VisitaResponseDto checkout(Long visitaId, VisitaCheckoutRequestDto request) {
		VisitaModel visita = visitaRepository.findById(visitaId)
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + visitaId));

		if (visita.getEstado() == EstadoVisita.COMPLETADA) {
			throw new BusinessException("La visita ya fue cerrada");
		}

		LocalDateTime ahora = LocalDateTime.now();
		visita.setCheckoutAt(ahora);
		visita.setPermanenciaMin((int) Duration.between(visita.getCheckinAt(), ahora).toMinutes());
		visita.setObservaciones(request.getObservaciones());
		visita.setEstado(EstadoVisita.COMPLETADA);

		VisitaModel guardada = visitaRepository.save(visita);

		if (guardada.getPlan() != null) {
			PlanVisitaModel plan = guardada.getPlan();
			plan.setEstado(EstadoPlanVisita.EJECUTADA);
			planVisitaRepository.save(plan);
		}

		return toResponseDto(guardada);
	}

	private VisitaResponseDto toResponseDto(VisitaModel model) {
		VisitaResponseDto dto = visitaMapper.toDto(model);
		dto.setCantidadFotos(evidenciaFotoRepository.countByVisitaId(model.getId()));
		return dto;
	}
}
