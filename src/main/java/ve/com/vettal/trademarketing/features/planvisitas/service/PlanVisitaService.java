package ve.com.vettal.trademarketing.features.planvisitas.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.clientes.model.SucursalClienteErpModel;
import ve.com.vettal.trademarketing.features.clientes.repository.SucursalClienteErpRepository;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaSubtipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaTipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.repository.ObjetivoVisitaSubtipoRepository;
import ve.com.vettal.trademarketing.features.objetivosvisita.repository.ObjetivoVisitaTipoRepository;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaEstadoRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaRequestDto;
import ve.com.vettal.trademarketing.features.planvisitas.dto.PlanVisitaResponseDto;
import ve.com.vettal.trademarketing.features.planvisitas.mapper.PlanVisitaMapper;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;
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
	private final SucursalClienteErpRepository sucursalClienteErpRepository;
	private final ObjetivoVisitaTipoRepository objetivoVisitaTipoRepository;
	private final ObjetivoVisitaSubtipoRepository objetivoVisitaSubtipoRepository;
	private final PlanVisitaMapper planVisitaMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<PlanVisitaResponseDto> listar(Long usuarioId, LocalDate fechaDesde, LocalDate fechaHasta, EstadoPlanVisita estado) {
		Long usuarioIdEfectivo = usuarioId;
		if (!authenticatedUserProvider.esAdminOSupervisor() && usuarioIdEfectivo == null) {
			usuarioIdEfectivo = authenticatedUserProvider.getUsuarioActual().getId();
		}

		return planVisitaMapper.toDtoList(
				planVisitaRepository.buscar(usuarioIdEfectivo, fechaDesde, fechaHasta, estado));
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

		SucursalClienteErpModel sucursal = null;
		if (request.getSucursalId() != null) {
			sucursal = sucursalClienteErpRepository.findById(request.getSucursalId())
					.orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con id " + request.getSucursalId()));
		}

		ObjetivoVisitaTipoModel objetivoTipo = null;
		if (request.getObjetivoTipoId() != null) {
			objetivoTipo = objetivoVisitaTipoRepository.findById(request.getObjetivoTipoId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Tipo de objetivo no encontrado con id " + request.getObjetivoTipoId()));
		}

		ObjetivoVisitaSubtipoModel objetivoSubtipo = null;
		if (request.getObjetivoSubtipoId() != null) {
			objetivoSubtipo = objetivoVisitaSubtipoRepository.findById(request.getObjetivoSubtipoId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Subtipo de objetivo no encontrado con id " + request.getObjetivoSubtipoId()));
		}

		String objetivo = objetivoTipo != null
				? objetivoTipo.getNombre() + (objetivoSubtipo != null ? " · " + objetivoSubtipo.getNombre() : "")
				: null;

		PlanVisitaModel planVisita = PlanVisitaModel.builder()
				.erpClienteId(request.getErpClienteId())
				.sucursal(sucursal)
				.clienteNombre(request.getClienteNombre())
				.usuario(usuario)
				.region(request.getRegion())
				.fechaProgramada(request.getFechaProgramada())
				.horaProgramada(request.getHoraProgramada())
				.objetivo(objetivo)
				.objetivoTipo(objetivoTipo)
				.objetivoSubtipo(objetivoSubtipo)
				.comentario(request.getComentario())
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
