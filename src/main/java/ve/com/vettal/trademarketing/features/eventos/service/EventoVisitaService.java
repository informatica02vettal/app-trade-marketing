package ve.com.vettal.trademarketing.features.eventos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

		// El mercaderista puede volver atrás y reconsiderar el motivo/detalle del
		// evento antes de terminar la visita, así que este endpoint actualiza el
		// evento existente de la visita en vez de rechazar el segundo envío.
		EventoVisitaModel evento = eventoVisitaRepository.findByVisitaId(request.getVisitaId())
				.orElseGet(() -> EventoVisitaModel.builder().visita(visita).build());

		evento.setMotivo(request.getMotivo());
		evento.setMotivoOtroDetalle(request.getMotivoOtroDetalle());
		evento.setNombreEvento(request.getNombreEvento());
		evento.setCiudad(request.getCiudad());
		evento.setEstado(request.getEstado());
		evento.setLugarRealizacion(request.getLugarRealizacion());
		evento.setFechaEvento(request.getFechaEvento());
		evento.setHoraInicio(request.getHoraInicio());
		evento.setHoraFin(request.getHoraFin());
		evento.setOrganizador(request.getOrganizador());
		evento.setObjetivoParticipacion(request.getObjetivoParticipacion());
		evento.setParticipacionVettal(request.getParticipacionVettal());
		evento.setCantidadAsistentesEstimada(request.getCantidadAsistentesEstimada());

		evento.getLeads().clear();
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

		evento.getEntrevistas().clear();
		if (request.getVideosEntrevistaUrls() != null) {
			request.getVideosEntrevistaUrls().forEach(url -> evento.addEntrevista(
					EventoEntrevistaModel.builder().videoUrl(url).build()));
		}

		return eventoVisitaMapper.toDto(eventoVisitaRepository.save(evento));
	}
}
