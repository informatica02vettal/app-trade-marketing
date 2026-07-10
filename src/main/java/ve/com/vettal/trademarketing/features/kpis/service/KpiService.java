package ve.com.vettal.trademarketing.features.kpis.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.features.auditorias.model.AuditoriaMarcaModel;
import ve.com.vettal.trademarketing.features.auditorias.repository.AuditoriaMarcaRepository;
import ve.com.vettal.trademarketing.features.kpis.dto.CoberturaRegionDto;
import ve.com.vettal.trademarketing.features.kpis.dto.CumplimientoMercaderistaDto;
import ve.com.vettal.trademarketing.features.kpis.dto.KpiDashboardResponseDto;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.repository.HallazgoMercadoRepository;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;
import ve.com.vettal.trademarketing.features.planvisitas.repository.PlanVisitaRepository;
import ve.com.vettal.trademarketing.features.solicitudes.repository.SolicitudRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

/**
 * Agrega datos de varios módulos para el dashboard gerencial. Los cálculos se
 * hacen en memoria sobre los resultados de cada repositorio: es una primera
 * versión razonable para el volumen actual; si el volumen de datos crece
 * mucho conviene mover estos cálculos a consultas nativas agregadas en BD.
 */
@Service
@RequiredArgsConstructor
public class KpiService {

	private final PlanVisitaRepository planVisitaRepository;
	private final VisitaRepository visitaRepository;
	private final AuditoriaMarcaRepository auditoriaMarcaRepository;
	private final HallazgoMercadoRepository hallazgoMercadoRepository;
	private final SolicitudRepository solicitudRepository;

	@Transactional(readOnly = true)
	public KpiDashboardResponseDto obtenerDashboard() {
		List<PlanVisitaModel> planes = planVisitaRepository.findAll();
		List<VisitaModel> visitas = visitaRepository.findAll();
		List<AuditoriaMarcaModel> auditorias = auditoriaMarcaRepository.findAll();

		int visitasPlanificadas = planes.size();
		int visitasEjecutadas = contarEjecutadas(planes);
		double cumplimientoVisitasPct = calcularCumplimiento(visitasPlanificadas, visitasEjecutadas);

		long clientesAtendidos = visitas.stream()
				.filter(v -> v.getEstado() == EstadoVisita.COMPLETADA && v.getErpClienteId() != null)
				.map(VisitaModel::getErpClienteId)
				.distinct()
				.count();

		double presenciaMarcaPct = auditorias.stream()
				.mapToInt(AuditoriaMarcaModel::getPresenciaPct)
				.average()
				.orElse(0);

		double participacionAnaquelPct = auditorias.stream()
				.mapToInt(AuditoriaMarcaModel::getAnaquelPct)
				.average()
				.orElse(0);

		long oportunidadesDetectadas = hallazgoMercadoRepository.findByTipo(TipoHallazgo.OBSERVACION_MERCADO).size();
		long solicitudesGeneradas = solicitudRepository.count();

		List<CoberturaRegionDto> coberturaPorRegion = planes.stream()
				.filter(p -> p.getRegion() != null)
				.collect(Collectors.groupingBy(PlanVisitaModel::getRegion))
				.entrySet().stream()
				.map(entry -> construirCoberturaRegion(entry.getKey(), entry.getValue()))
				.toList();

		List<CumplimientoMercaderistaDto> cumplimientoPorMercaderista = planes.stream()
				.filter(p -> p.getUsuario() != null)
				.collect(Collectors.groupingBy(p -> p.getUsuario().getId()))
				.values().stream()
				.map(this::construirCumplimientoMercaderista)
				.toList();

		return KpiDashboardResponseDto.builder()
				.fecha(LocalDate.now())
				.visitasPlanificadas(visitasPlanificadas)
				.visitasEjecutadas(visitasEjecutadas)
				.cumplimientoVisitasPct(redondear(cumplimientoVisitasPct))
				.clientesAtendidos((int) clientesAtendidos)
				.presenciaMarcaPct(redondear(presenciaMarcaPct))
				.participacionAnaquelPct(redondear(participacionAnaquelPct))
				.oportunidadesDetectadas((int) oportunidadesDetectadas)
				.solicitudesGeneradas((int) solicitudesGeneradas)
				.coberturaPorRegion(coberturaPorRegion)
				.cumplimientoPorMercaderista(cumplimientoPorMercaderista)
				.build();
	}

	private CoberturaRegionDto construirCoberturaRegion(String region, List<PlanVisitaModel> planesRegion) {
		int planificadas = planesRegion.size();
		int ejecutadas = contarEjecutadas(planesRegion);
		return CoberturaRegionDto.builder()
				.region(region)
				.visitasPlanificadas(planificadas)
				.visitasEjecutadas(ejecutadas)
				.cumplimientoPct(redondear(calcularCumplimiento(planificadas, ejecutadas)))
				.build();
	}

	private CumplimientoMercaderistaDto construirCumplimientoMercaderista(List<PlanVisitaModel> planesUsuario) {
		UsuarioModel usuario = planesUsuario.get(0).getUsuario();
		int planificadas = planesUsuario.size();
		int ejecutadas = contarEjecutadas(planesUsuario);
		return CumplimientoMercaderistaDto.builder()
				.usuarioId(usuario.getId())
				.usuarioNombre(usuario.getNombre())
				.visitasPlanificadas(planificadas)
				.visitasEjecutadas(ejecutadas)
				.cumplimientoPct(redondear(calcularCumplimiento(planificadas, ejecutadas)))
				.build();
	}

	private int contarEjecutadas(List<PlanVisitaModel> planes) {
		return (int) planes.stream().filter(p -> p.getEstado() == EstadoPlanVisita.EJECUTADA).count();
	}

	private double calcularCumplimiento(int planificadas, int ejecutadas) {
		return planificadas == 0 ? 0 : (ejecutadas * 100.0) / planificadas;
	}

	private double redondear(double valor) {
		return Math.round(valor * 100.0) / 100.0;
	}
}
