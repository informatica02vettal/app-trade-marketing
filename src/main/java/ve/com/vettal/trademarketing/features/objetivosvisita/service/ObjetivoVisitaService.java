package ve.com.vettal.trademarketing.features.objetivosvisita.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaSubtipoRequestDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaSubtipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaTipoRequestDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.dto.ObjetivoVisitaTipoResponseDto;
import ve.com.vettal.trademarketing.features.objetivosvisita.mapper.ObjetivoVisitaMapper;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaSubtipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaTipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.repository.ObjetivoVisitaSubtipoRepository;
import ve.com.vettal.trademarketing.features.objetivosvisita.repository.ObjetivoVisitaTipoRepository;

@Service
@RequiredArgsConstructor
public class ObjetivoVisitaService {

	private final ObjetivoVisitaTipoRepository tipoRepository;
	private final ObjetivoVisitaSubtipoRepository subtipoRepository;
	private final ObjetivoVisitaMapper mapper;

	@Transactional(readOnly = true)
	public List<ObjetivoVisitaTipoResponseDto> listarTipos() {
		return mapper.toTipoDtoList(tipoRepository.findAll());
	}

	@Transactional
	public ObjetivoVisitaTipoResponseDto crearTipo(ObjetivoVisitaTipoRequestDto request) {
		ObjetivoVisitaTipoModel tipo = ObjetivoVisitaTipoModel.builder()
				.nombre(request.getNombre())
				.activo(request.getActivo() == null || request.getActivo())
				.build();

		return mapper.toDto(tipoRepository.save(tipo));
	}

	@Transactional
	public ObjetivoVisitaTipoResponseDto actualizarTipo(Long id, ObjetivoVisitaTipoRequestDto request) {
		ObjetivoVisitaTipoModel tipo = tipoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de objetivo no encontrado con id " + id));

		tipo.setNombre(request.getNombre());
		if (request.getActivo() != null) {
			tipo.setActivo(request.getActivo());
		}

		return mapper.toDto(tipoRepository.save(tipo));
	}

	@Transactional(readOnly = true)
	public List<ObjetivoVisitaSubtipoResponseDto> listarSubtipos(Long tipoId) {
		List<ObjetivoVisitaSubtipoModel> subtipos =
				tipoId != null ? subtipoRepository.findByTipoId(tipoId) : subtipoRepository.findAll();
		return mapper.toSubtipoDtoList(subtipos);
	}

	@Transactional
	public ObjetivoVisitaSubtipoResponseDto crearSubtipo(ObjetivoVisitaSubtipoRequestDto request) {
		ObjetivoVisitaTipoModel tipo = tipoRepository.findById(request.getTipoId())
				.orElseThrow(() -> new ResourceNotFoundException("Tipo de objetivo no encontrado con id " + request.getTipoId()));

		ObjetivoVisitaSubtipoModel subtipo = ObjetivoVisitaSubtipoModel.builder()
				.tipo(tipo)
				.nombre(request.getNombre())
				.activo(request.getActivo() == null || request.getActivo())
				.build();

		return mapper.toDto(subtipoRepository.save(subtipo));
	}

	@Transactional
	public ObjetivoVisitaSubtipoResponseDto actualizarSubtipo(Long id, ObjetivoVisitaSubtipoRequestDto request) {
		ObjetivoVisitaSubtipoModel subtipo = subtipoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subtipo de objetivo no encontrado con id " + id));

		if (!subtipo.getTipo().getId().equals(request.getTipoId())) {
			ObjetivoVisitaTipoModel tipo = tipoRepository.findById(request.getTipoId())
					.orElseThrow(() -> new ResourceNotFoundException("Tipo de objetivo no encontrado con id " + request.getTipoId()));
			subtipo.setTipo(tipo);
		}
		subtipo.setNombre(request.getNombre());
		if (request.getActivo() != null) {
			subtipo.setActivo(request.getActivo());
		}

		return mapper.toDto(subtipoRepository.save(subtipo));
	}
}
