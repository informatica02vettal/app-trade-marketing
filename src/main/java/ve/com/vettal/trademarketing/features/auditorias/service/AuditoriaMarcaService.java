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
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.catalogos.repository.MarcaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class AuditoriaMarcaService {

	private final AuditoriaMarcaRepository auditoriaMarcaRepository;
	private final VisitaRepository visitaRepository;
	private final MarcaRepository marcaRepository;
	private final AuditoriaMarcaMapper auditoriaMarcaMapper;

	@Transactional(readOnly = true)
	public List<AuditoriaMarcaResponseDto> listarPorVisita(Long visitaId) {
		return auditoriaMarcaMapper.toDtoList(auditoriaMarcaRepository.findByVisitaId(visitaId));
	}

	/**
	 * Crea o actualiza (upsert por visita+marca) la auditoría de esta marca.
	 * Se llama repetidamente durante el flujo (cada respuesta/foto guarda de
	 * inmediato con {@code completa=false}) y una última vez con
	 * {@code completa=true} al terminar, para que el progreso quede siempre
	 * en la base de datos y se pueda retomar desde cualquier dispositivo.
	 */
	@Transactional
	public AuditoriaMarcaResponseDto crear(AuditoriaMarcaRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));

		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se pueden auditar marcas en visitas en curso");
		}

		MarcaModel marca = marcaRepository.findById(request.getMarcaId())
				.orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con id " + request.getMarcaId()));

		AuditoriaMarcaModel auditoria = auditoriaMarcaRepository
				.findByVisitaIdAndMarcaId(request.getVisitaId(), request.getMarcaId())
				.orElseGet(() -> AuditoriaMarcaModel.builder().visita(visita).marca(marca).build());

		// Regla 1: sin exhibidor de marca no puede haber producto en exhibidor.
		boolean productoExhibidor = request.isExhibidorMarca() && request.isProductoExhibidor();

		// Regla 2: sin ningún elemento de comunicación visible, el POP se considera faltante.
		boolean sinMaterialPop = !request.isAvisoPared() && !request.isBanderines()
				&& !request.isRotulado() && !request.isEmpleadosUniforme();
		EstadoPop estadoPop = request.getEstadoPop() == null
				? null
				: (sinMaterialPop ? EstadoPop.FALTANTE : request.getEstadoPop());

		// Regla 3: el porcentaje de anaquel siempre se recalcula en el servidor.
		int frentesVettal = request.getFrentesVettal() != null ? request.getFrentesVettal() : 0;
		int frentesTotales = request.getFrentesTotales() != null ? request.getFrentesTotales() : 0;
		int anaquelPct = frentesTotales > 0 ? (int) Math.round((frentesVettal * 100.0) / frentesTotales) : 0;

		auditoria.setPresenciaPct(request.getPresenciaPct() != null ? request.getPresenciaPct() : 0);
		auditoria.setAnaquelPct(anaquelPct);
		auditoria.setFrentesVettal(frentesVettal);
		auditoria.setFrentesTotales(frentesTotales);
		auditoria.setExhibidorMarca(request.isExhibidorMarca());
		auditoria.setProductoExhibidor(productoExhibidor);
		auditoria.setProductoAnaquel(request.isProductoAnaquel());
		auditoria.setAvisoFachada(request.isAvisoFachada());
		auditoria.setAvisoPared(request.isAvisoPared());
		auditoria.setBanderines(request.isBanderines());
		auditoria.setRotulado(request.isRotulado());
		auditoria.setEmpleadosUniforme(request.isEmpleadosUniforme());
		auditoria.setEstadoExhibidores(request.getEstadoExhibidores());
		auditoria.setEstadoPop(estadoPop);
		auditoria.setCompetenciaDetectada(request.getCompetenciaDetectada());
		auditoria.setOportunidad(request.getOportunidad());
		auditoria.setCompleta(request.isCompleta());

		return auditoriaMarcaMapper.toDto(auditoriaMarcaRepository.save(auditoria));
	}
}
