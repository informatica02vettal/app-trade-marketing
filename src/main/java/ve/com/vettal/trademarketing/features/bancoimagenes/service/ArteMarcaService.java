package ve.com.vettal.trademarketing.features.bancoimagenes.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.ArteMarcaRequestDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.dto.ArteMarcaResponseDto;
import ve.com.vettal.trademarketing.features.bancoimagenes.mapper.ArteMarcaMapper;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.ArteMarcaModel;
import ve.com.vettal.trademarketing.features.bancoimagenes.repository.ArteMarcaRepository;

@Service
@RequiredArgsConstructor
public class ArteMarcaService {

	private final ArteMarcaRepository arteMarcaRepository;
	private final ArteMarcaMapper arteMarcaMapper;

	@Transactional(readOnly = true)
	public List<ArteMarcaResponseDto> listar(String marca) {
		if (marca != null && !marca.isBlank()) {
			return arteMarcaMapper.toDtoList(arteMarcaRepository.findByMarca(marca));
		}
		return arteMarcaMapper.toDtoList(arteMarcaRepository.findAll());
	}

	@Transactional
	public ArteMarcaResponseDto crear(ArteMarcaRequestDto request) {
		ArteMarcaModel arteMarca = ArteMarcaModel.builder()
				.marca(request.getMarca())
				.nombre(request.getNombre())
				.archivoUrl(request.getArchivoUrl())
				.tipoArchivo(request.getTipoArchivo())
				.build();

		return arteMarcaMapper.toDto(arteMarcaRepository.save(arteMarca));
	}

	@Transactional
	public void eliminar(Long id) {
		ArteMarcaModel arteMarca = arteMarcaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Arte de marca no encontrado con id " + id));

		arteMarcaRepository.delete(arteMarca);
	}
}
