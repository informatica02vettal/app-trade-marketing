package ve.com.vettal.trademarketing.features.competidores.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.competidores.dto.CompetidorRequestDto;
import ve.com.vettal.trademarketing.features.competidores.dto.CompetidorResponseDto;
import ve.com.vettal.trademarketing.features.competidores.mapper.CompetidorMapper;
import ve.com.vettal.trademarketing.features.competidores.model.CategoriaFotoCompetidor;
import ve.com.vettal.trademarketing.features.competidores.model.CompetidorFotoModel;
import ve.com.vettal.trademarketing.features.competidores.model.CompetidorModel;
import ve.com.vettal.trademarketing.features.competidores.repository.CompetidorRepository;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class CompetidorService {

	private final CompetidorRepository competidorRepository;
	private final VisitaRepository visitaRepository;
	private final CompetidorMapper competidorMapper;

	@Transactional(readOnly = true)
	public List<CompetidorResponseDto> listarPorVisita(Long visitaId) {
		return competidorRepository.findByVisitaId(visitaId).stream().map(this::toDtoConFotos).toList();
	}

	@Transactional
	public CompetidorResponseDto crear(CompetidorRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));

		CompetidorModel competidor = CompetidorModel.builder()
				.visita(visita)
				.nombreEmpresa(request.getNombreEmpresa())
				.marcasRepresentadas(request.getMarcasRepresentadas())
				.tipoProductosExhibidos(request.getTipoProductosExhibidos())
				.tamanoStand(request.getTamanoStand())
				.cantidadPromotores(request.getCantidadPromotores())
				.cantidadPersonalTecnico(request.getCantidadPersonalTecnico())
				.poseeInflables(Boolean.TRUE.equals(request.getPoseeInflables()))
				.poseeToldos(Boolean.TRUE.equals(request.getPoseeToldos()))
				.poseePantallaLed(Boolean.TRUE.equals(request.getPoseePantallaLed()))
				.poseeExperienciasInteractivas(Boolean.TRUE.equals(request.getPoseeExperienciasInteractivas()))
				.realizaDemostraciones(Boolean.TRUE.equals(request.getRealizaDemostraciones()))
				.entregaMaterialPop(Boolean.TRUE.equals(request.getEntregaMaterialPop()))
				.entregaMuestras(Boolean.TRUE.equals(request.getEntregaMuestras()))
				.realizaRifasConcursos(Boolean.TRUE.equals(request.getRealizaRifasConcursos()))
				.realizaPromocionesEspeciales(Boolean.TRUE.equals(request.getRealizaPromocionesEspeciales()))
				.cuentaActivaciones(Boolean.TRUE.equals(request.getCuentaActivaciones()))
				.poseeExhibidoresDiferenciadores(Boolean.TRUE.equals(request.getPoseeExhibidoresDiferenciadores()))
				.utilizaMascotasPublicitarias(Boolean.TRUE.equals(request.getUtilizaMascotasPublicitarias()))
				.observaciones(request.getObservaciones())
				.build();

		if (request.getFotosStand() != null) {
			request.getFotosStand().forEach(url -> competidor.addFoto(
					CompetidorFotoModel.builder().categoria(CategoriaFotoCompetidor.STAND).url(url).build()));
		}
		if (request.getFotosMaterialPublicitario() != null) {
			request.getFotosMaterialPublicitario().forEach(url -> competidor.addFoto(
					CompetidorFotoModel.builder().categoria(CategoriaFotoCompetidor.MATERIAL_PUBLICITARIO).url(url).build()));
		}

		return toDtoConFotos(competidorRepository.save(competidor));
	}

	private CompetidorResponseDto toDtoConFotos(CompetidorModel model) {
		CompetidorResponseDto dto = competidorMapper.toDto(model);
		dto.setFotosStand(model.getFotos().stream()
				.filter(f -> f.getCategoria() == CategoriaFotoCompetidor.STAND)
				.map(CompetidorFotoModel::getUrl)
				.toList());
		dto.setFotosMaterialPublicitario(model.getFotos().stream()
				.filter(f -> f.getCategoria() == CategoriaFotoCompetidor.MATERIAL_PUBLICITARIO)
				.map(CompetidorFotoModel::getUrl)
				.toList());
		return dto;
	}
}
