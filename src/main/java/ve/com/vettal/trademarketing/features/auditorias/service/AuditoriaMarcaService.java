package ve.com.vettal.trademarketing.features.auditorias.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.auditorias.dto.AuditoriaMarcaRequestDto;
import ve.com.vettal.trademarketing.features.auditorias.dto.AuditoriaMarcaResponseDto;
import ve.com.vettal.trademarketing.features.auditorias.mapper.AuditoriaMarcaMapper;
import ve.com.vettal.trademarketing.features.auditorias.model.AuditoriaMarcaModel;
import ve.com.vettal.trademarketing.features.auditorias.model.EstadoPop;
import ve.com.vettal.trademarketing.features.auditorias.repository.AuditoriaMarcaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class AuditoriaMarcaService {

	private final AuditoriaMarcaRepository auditoriaMarcaRepository;
	private final VisitaRepository visitaRepository;
	private final AuditoriaMarcaMapper auditoriaMarcaMapper;

	@Transactional(readOnly = true)
	public List<AuditoriaMarcaResponseDto> listarPorVisita(Long visitaId) {
		return auditoriaMarcaMapper.toDtoList(auditoriaMarcaRepository.findByVisitaId(visitaId));
	}

	@Transactional
	public AuditoriaMarcaResponseDto crear(AuditoriaMarcaRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));

		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se pueden auditar marcas en visitas en curso");
		}

		// Regla 1: sin exhibidor de marca no puede haber producto en exhibidor.
		boolean productoExhibidor = request.isExhibidorMarca() && request.isProductoExhibidor();

		// Regla 2: sin ningún elemento de comunicación visible, el POP se considera faltante.
		boolean sinMaterialPop = !request.isAvisoPared() && !request.isBanderines()
				&& !request.isRotulado() && !request.isEmpleadosUniforme();
		EstadoPop estadoPop = sinMaterialPop ? EstadoPop.FALTANTE : request.getEstadoPop();

		// Regla 3: el porcentaje de anaquel siempre se recalcula en el servidor.
		int frentesVettal = request.getFrentesVettal() != null ? request.getFrentesVettal() : 0;
		int frentesTotales = request.getFrentesTotales() != null ? request.getFrentesTotales() : 0;
		int anaquelPct = frentesTotales > 0 ? (int) Math.round((frentesVettal * 100.0) / frentesTotales) : 0;

		AuditoriaMarcaModel auditoria = AuditoriaMarcaModel.builder()
				.visita(visita)
				.marca(request.getMarca())
				.presenciaPct(request.getPresenciaPct())
				.anaquelPct(anaquelPct)
				.frentesVettal(frentesVettal)
				.frentesTotales(frentesTotales)
				.exhibidorMarca(request.isExhibidorMarca())
				.productoExhibidor(productoExhibidor)
				.productoAnaquel(request.isProductoAnaquel())
				.avisoFachada(request.isAvisoFachada())
				.avisoPared(request.isAvisoPared())
				.banderines(request.isBanderines())
				.rotulado(request.isRotulado())
				.empleadosUniforme(request.isEmpleadosUniforme())
				.estadoExhibidores(request.getEstadoExhibidores())
				.estadoPop(estadoPop)
				.competenciaDetectada(request.getCompetenciaDetectada())
				.oportunidad(request.getOportunidad())
				.build();

		return auditoriaMarcaMapper.toDto(auditoriaMarcaRepository.save(auditoria));
	}
}
