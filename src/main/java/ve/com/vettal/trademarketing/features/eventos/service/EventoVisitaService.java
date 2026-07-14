package ve.com.vettal.trademarketing.features.eventos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoVisitaRequestDto;
import ve.com.vettal.trademarketing.features.eventos.dto.EventoVisitaResponseDto;
import ve.com.vettal.trademarketing.features.eventos.mapper.EventoVisitaMapper;
import ve.com.vettal.trademarketing.features.eventos.model.EventoEntrevistaModel;
import ve.com.vettal.trademarketing.features.eventos.model.EventoLeadModel;
import ve.com.vettal.trademarketing.features.eventos.model.EventoVisitaModel;
import ve.com.vettal.trademarketing.features.eventos.repository.EventoVisitaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class EventoVisitaService {

	private final EventoVisitaRepository eventoVisitaRepository;
	private final VisitaRepository visitaRepository;
	private final EventoVisitaMapper eventoVisitaMapper;

	@Transactional(readOnly = true)
	public EventoVisitaResponseDto obtenerPorVisita(Long visitaId) {
		return eventoVisitaRepository.findByVisitaId(visitaId)
				.map(eventoVisitaMapper::toDto)
				.orElse(null);
	}

	@Transactional
	public EventoVisitaResponseDto crear(EventoVisitaRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));

		if (eventoVisitaRepository.findByVisitaId(request.getVisitaId()).isPresent()) {
			throw new BusinessException("Esta visita ya tiene un evento registrado");
		}

		EventoVisitaModel evento = EventoVisitaModel.builder()
				.visita(visita)
				.motivo(request.getMotivo())
				.motivoOtroDetalle(request.getMotivoOtroDetalle())
				.nombreEvento(request.getNombreEvento())
				.ciudad(request.getCiudad())
				.estado(request.getEstado())
				.lugarRealizacion(request.getLugarRealizacion())
				.fechaEvento(request.getFechaEvento())
				.horaInicio(request.getHoraInicio())
				.horaFin(request.getHoraFin())
				.organizador(request.getOrganizador())
				.objetivoParticipacion(request.getObjetivoParticipacion())
				.participacionVettal(request.getParticipacionVettal())
				.cantidadAsistentesEstimada(request.getCantidadAsistentesEstimada())
				.build();

		if (request.getLeads() != null) {
			request.getLeads().forEach(lead -> evento.addLead(
					EventoLeadModel.builder()
							.nombre(lead.getNombre())
							.empresa(lead.getEmpresa())
							.cargo(lead.getCargo())
							.telefono(lead.getTelefono())
							.correo(lead.getCorreo())
							.build()));
		}

		if (request.getVideosEntrevistaUrls() != null) {
			request.getVideosEntrevistaUrls().forEach(url -> evento.addEntrevista(
					EventoEntrevistaModel.builder().videoUrl(url).build()));
		}

		return eventoVisitaMapper.toDto(eventoVisitaRepository.save(evento));
	}
}
