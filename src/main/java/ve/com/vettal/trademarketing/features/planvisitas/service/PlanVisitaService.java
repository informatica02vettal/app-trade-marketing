package ve.com.vettal.trademarketing.features.planvisitas.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaEstadoRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaResponseDto;
import ve.com.vettal.trademarketing.features.planvisitas.mapper.PlanVisitaMapper;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;
import ve.com.vettal.trademarketing.features.planvisitas.model.TipoVisita;
import ve.com.vettal.trademarketing.features.planvisitas.repository.PlanVisitaRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class PlanVisitaService {

	private final PlanVisitaRepository planVisitaRepository;
	private final UsuarioRepository usuarioRepository;
	private final PlanVisitaMapper planVisitaMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<PlanVisitaResponseDto> listar(Long usuarioId, LocalDate fecha) {
		Long usuarioIdEfectivo = usuarioId;
		if (!authenticatedUserProvider.esAdminOSupervisor() && usuarioIdEfectivo == null) {
			usuarioIdEfectivo = authenticatedUserProvider.getUsuarioActual().getId();
		}
		LocalDate fechaEfectiva = fecha != null ? fecha : LocalDate.now();

		if (usuarioIdEfectivo == null) {
			return List.of();
		}

		return planVisitaMapper.toDtoList(
				planVisitaRepository.findByUsuarioIdAndFechaProgramada(usuarioIdEfectivo, fechaEfectiva));
	}

	@Transactional
	public PlanVisitaResponseDto crear(PlanVisitaRequestDto request) {
		UsuarioModel usuario;
		if (request.getUsuarioId() != null) {
			usuario = usuarioRepository.findById(request.getUsuarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + request.getUsuarioId()));
		} else {
			usuario = authenticatedUserProvider.getUsuarioActual();
		}

		PlanVisitaModel planVisita = PlanVisitaModel.builder()
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.usuario(usuario)
				.region(request.getRegion())
				.fechaProgramada(request.getFechaProgramada())
				.horaProgramada(request.getHoraProgramada())
				.objetivo(request.getObjetivo())
				.tipoVisita(request.getTipoVisita() != null ? request.getTipoVisita() : TipoVisita.PLANIFICADA)
				.build();

		return planVisitaMapper.toDto(planVisitaRepository.save(planVisita));
	}

	@Transactional
	public PlanVisitaResponseDto actualizarEstado(Long id, PlanVisitaEstadoRequestDto request) {
		PlanVisitaModel planVisita = planVisitaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan de visita no encontrado con id " + id));

		planVisita.setEstado(request.getEstado());

		return planVisitaMapper.toDto(planVisitaRepository.save(planVisita));
	}
}
