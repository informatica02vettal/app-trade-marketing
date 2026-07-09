package ve.com.vettal.trademarketing.features.bancoimagenes.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.PlanogramaRequestDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.PlanogramaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.mapper.PlanogramaMapper;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.PlanogramaModel;
import ve.com.vettal.trademarketing.features.bancoimagenes.repository.PlanogramaRepository;

@Service
@RequiredArgsConstructor
public class PlanogramaService {

	private final PlanogramaRepository planogramaRepository;
	private final PlanogramaMapper planogramaMapper;

	@Transactional(readOnly = true)
	public List<PlanogramaResponseDto> listar(String tipoExhibidor) {
		if (tipoExhibidor != null && !tipoExhibidor.isBlank()) {
			return planogramaMapper.toDtoList(planogramaRepository.findByTipoExhibidor(tipoExhibidor));
		}
		return planogramaMapper.toDtoList(planogramaRepository.findAll());
	}

	@Transactional
	public PlanogramaResponseDto crear(PlanogramaRequestDto request) {
		PlanogramaModel planograma = PlanogramaModel.builder()
				.tipoExhibidor(request.getTipoExhibidor())
				.nombre(request.getNombre())
				.imagenUrl(request.getImagenUrl())
				.build();

		return planogramaMapper.toDto(planogramaRepository.save(planograma));
	}

	@Transactional
	public void eliminar(Long id) {
		PlanogramaModel planograma = planogramaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Planograma no encontrado con id " + id));

		planogramaRepository.delete(planograma);
	}
}
