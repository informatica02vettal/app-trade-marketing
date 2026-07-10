package ve.com.vettal.trademarketing.features.visitas.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.visitas.dto.ClienteProspectoRequestDto;
import ve.com.vettal.trademarketing.features.visitas.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.visitas.mapper.ClienteProspectoMapper;
import ve.com.vettal.trademarketing.features.visitas.model.ClienteProspectoModel;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.ClienteProspectoRepository;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class ClienteProspectoService {

	private final ClienteProspectoRepository clienteProspectoRepository;
	private final VisitaRepository visitaRepository;
	private final ClienteProspectoMapper clienteProspectoMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<ClienteProspectoResponseDto> listarPorVisita(Long visitaId) {
		return clienteProspectoMapper.toDtoList(clienteProspectoRepository.findByVisitaId(visitaId));
	}

	@Transactional
	public ClienteProspectoResponseDto crear(ClienteProspectoRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));
		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se puede registrar un cliente no registrado en visitas en curso");
		}

		ClienteProspectoModel prospecto = ClienteProspectoModel.builder()
				.visita(visita)
				.nombre(request.getNombre())
				.rif(request.getRif())
				.whatsapp(request.getWhatsapp())
				.telefono(request.getTelefono())
				.fotoFachadaUrl(request.getFotoFachadaUrl())
				.fotoInteriorUrl(request.getFotoInteriorUrl())
				.marcasCompetencia(request.getMarcasCompetencia())
				.usuario(authenticatedUserProvider.getUsuarioActual())
				.build();

		return clienteProspectoMapper.toDto(clienteProspectoRepository.save(prospecto));
	}
}
